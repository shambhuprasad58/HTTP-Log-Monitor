/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httplogmonitor;

import httplogmonitorui.HomeFrame;
import httplogmonitorutil.Alert;
import httplogmonitorutil.HttpObject;
import httplogmonitorutil.Statistics;
import httplogmonitorutil.UserPreferences;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class HTTPLogMonitor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        LinkedBlockingQueue<UserPreferences> preferenceQueue = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<HttpObject> mostHitsURLQueue = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<ArrayList<HttpObject>> mostHitsTopURL = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<HttpObject> alertURLQueue = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<Alert> alertQueue = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<Statistics> statsQueue = new LinkedBlockingQueue<>();
        int threshold = 10;
        Sniffer sniffer = new Sniffer(preferenceQueue, mostHitsURLQueue, alertURLQueue, alertQueue, threshold, statsQueue, mostHitsTopURL);
        Thread snifferThread = new Thread(sniffer);
        snifferThread.start();
        System.out.println("HERE");
        Thread displayUIThread = new Thread(new DisplayUI(preferenceQueue, alertQueue, threshold, statsQueue, mostHitsTopURL));
        displayUIThread.start();
        try {
            System.out.println("INIT DONE");
            Thread.sleep(1000*1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(HTTPLogMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static class DisplayUI implements Runnable
    {
        LinkedBlockingQueue<UserPreferences> preferenceQueue;
        LinkedBlockingQueue<ArrayList<HttpObject>> mostHitsTopURL;
        LinkedBlockingQueue<Alert> alertQueue;
        LinkedBlockingQueue<Statistics> statsQueue;
        int threshold;
        
        public DisplayUI(LinkedBlockingQueue<UserPreferences> preferenceQueue, LinkedBlockingQueue<Alert> alertQueue, int threshold, LinkedBlockingQueue<Statistics> statsQueue, LinkedBlockingQueue<ArrayList<HttpObject>> mostHitsTopURL)
        {
            this.preferenceQueue = preferenceQueue;
            this.mostHitsTopURL = mostHitsTopURL;
            this.alertQueue = alertQueue;
            this.threshold = threshold;
            this.statsQueue = statsQueue;
        }
        @Override
        public void run() {
                HomeFrame.Begin(preferenceQueue, alertQueue, threshold, statsQueue, mostHitsTopURL);
        }
        
    }
    
}
