/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httplogmonitor;

import com.sun.org.glassfish.external.statistics.Statistic;
import httplogmonitorutil.Alert;
import httplogmonitorutil.HttpObject;
import httplogmonitorutil.Statistics;
import httplogmonitorutil.UserPreferences;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jnetpcap.*;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;

/**
 *
 * @author root
 */
public class Sniffer implements Runnable{
    
    LinkedBlockingQueue<UserPreferences> preferenceQueue;
    LinkedBlockingQueue<HttpObject> mostHitsURLQueue;
    LinkedBlockingQueue<ArrayList<HttpObject>> mostHitsTopURL;
    LinkedBlockingQueue<HttpObject> alertURLQueue;
    LinkedBlockingQueue<Alert> alertQueue;
    LinkedBlockingQueue<Statistics> statsQueue;
    int threshold;
    Statistics stats;
    
    public Sniffer(LinkedBlockingQueue<UserPreferences> preferenceQueue, LinkedBlockingQueue<HttpObject> mostHitsURLQueue, LinkedBlockingQueue<HttpObject> alertURLQueue, LinkedBlockingQueue<Alert> alertQueue, int threshold, LinkedBlockingQueue<Statistics> statsQueue, LinkedBlockingQueue<ArrayList<HttpObject>> mostHitsTopURL)
    {
        this.preferenceQueue = preferenceQueue;
        this.mostHitsURLQueue = mostHitsURLQueue;
        this.mostHitsTopURL = mostHitsTopURL;
        this.alertURLQueue = alertURLQueue;
        this.alertQueue = alertQueue;
        this.threshold = threshold;
        this.statsQueue = statsQueue;
        stats = new Statistics(0,0,0,0);
    }
    
    @Override
    public void run()
    {
        Thread statsThread = new Thread(new SendStats());
        statsThread.start();
        
        Thread loopThread = null;
        Thread alertThread = null;
        PcapIf device = null;
        int timeGapInMillisecond = 0;
        int alertGapInMillisecond = 0;
        TimerTask mostHitsTimerTask = null;
        Timer mostHitsTimer = null;
        while(true)
        {
            UserPreferences preference;
            try {
                preference = preferenceQueue.take();
            if(!preferenceQueue.isEmpty())
                continue;
            if(!preference.getDevice().equals(device))
            {
                device = preference.getDevice();
                if(loopThread != null)
                    loopThread.interrupt();
                loopThread = new Thread(new RunLoop(device));
                loopThread.start();
            }
            if(preference.getTimeGapInMillisecond() != timeGapInMillisecond)
            {
                timeGapInMillisecond = preference.getTimeGapInMillisecond();
                if(mostHitsTimer != null)
                    mostHitsTimer.cancel();
                mostHitsTimerTask = new MostHitsCalculator(mostHitsURLQueue, mostHitsTopURL);
                mostHitsTimer = new Timer(true);
                mostHitsTimer.scheduleAtFixedRate(mostHitsTimerTask, 0, timeGapInMillisecond);
                System.out.println("TimerTask started");
            }
            if(preference.getAlertGapInMillisecond() != alertGapInMillisecond || preference.getThreshold() != this.threshold)
            {
                alertGapInMillisecond = preference.getAlertGapInMillisecond();
                this.threshold = preference.getThreshold();
                if(alertThread != null)
                    alertThread.interrupt();
                alertThread = new Thread(new AlertTracker(alertURLQueue, threshold, alertQueue, alertGapInMillisecond));
                alertThread.start();
            }
            } catch (InterruptedException ex) {
                Logger.getLogger(Sniffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private class SendStats implements Runnable
    {

        @Override
        public void run() 
        {
            while(true)
            {
                try {
                    statsQueue.put(stats);
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Sniffer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
    
    private class RunLoop implements Runnable
    {
        PcapIf device;
        
        public RunLoop(PcapIf device)
        {
            this.device = device;
        }
        
        @Override
        public void run()
        {
            StringBuilder errbuf = new StringBuilder();
            int snaplen = 64 * 1024;           // Capture all packets, no trucation
            int flags = Pcap.MODE_NON_PROMISCUOUS; // capture packets meant for this device
            int timeout = 0;                    // no timeout
            Pcap pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);

            if (pcap == null) {
                    System.err.printf("Error while opening device for capture: "
                        + errbuf.toString());
                    return;
            }
            PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>() 
            {
                final Tcp tcp = new Tcp();
                final Http http = new Http();
                @Override
                public void nextPacket(PcapPacket packet, String user) {
                    if (packet.hasHeader(tcp) && packet.hasHeader(http)) 
                    {
                        System.out.printf("frame #%d%n", packet.getFrameNumber());
                        if(http.hasField(Http.Request.RequestUrl))
                        {
                            HttpObject newHit = new HttpObject(http.fieldValue(Http.Request.RequestUrl), new Date(), 0);
                            try {
                                mostHitsURLQueue.put(newHit);
                                alertURLQueue.put(newHit);
                                int alertURLQueueSize = alertURLQueue.size();
                                if(alertURLQueueSize > threshold)
                                    alertQueue.put(new Alert(new Date(), alertURLQueueSize, true));
                                updateStats(http);
                                System.out.println(threshold);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Sniffer.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            };
            pcap.loop(0, jpacketHandler, "jNetPcap rocks!");
        }
        
        public void updateStats(Http http)
        {
            try
            {
                stats.setHitCount(stats.getHitCount()+1);
                //System.out.println(http.fieldValue(Http.Response.Content_Length) + " " + http.fieldValue(Http.Request.Content_Length)+" "+http.fieldValue(Http.Response.ResponseCode));
                //stats.setKiloBytesDownloaded(stats.getKiloBytesDownloaded()+Long.parseLong(http.fieldValue(Http.Response.Content_Length)));
                //stats.setKiloBytesUploaded(stats.getKiloBytesUploaded()+Long.parseLong(http.fieldValue(Http.Request.Content_Length)));
                //if(Integer.parseInt(http.fieldValue(Http.Response.ResponseCode)) == 200)
                //    stats.setSuccessfulHits(stats.getSuccessfulHits()+1);
            }
            catch(Exception ex)
            {
                Logger.getLogger(Sniffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
