/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httplogmonitor;

import httplogmonitorutil.Alert;
import httplogmonitorutil.HttpObject;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class AlertTracker implements Runnable
{
    LinkedBlockingQueue<HttpObject> alertURLQueue;
    LinkedBlockingQueue<Alert> alertQueue;
    int threshold;
    int alertGapInMillisecond;

    public AlertTracker(LinkedBlockingQueue<HttpObject> alertURLQueue, int threshold, LinkedBlockingQueue<Alert> alertQueue, int alertGapInMillisecond) 
    {
        this.alertURLQueue = alertURLQueue;
        this.threshold = threshold;
        this.alertQueue = alertQueue;
        this.alertGapInMillisecond = alertGapInMillisecond;
    }
    
    @Override
    public void run() 
    {
        while(true)
        {
            while(alertURLQueue.isEmpty())
            {
                try {
                    Thread.sleep(5*1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(AlertTracker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(alertURLQueue.size() <= threshold)
            {
                try {
                    alertQueue.put(new Alert(new Date(), alertURLQueue.size(), false));
                } catch (InterruptedException ex) {
                    Logger.getLogger(AlertTracker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            HttpObject lastURL;
            while(!alertURLQueue.isEmpty())
            {
                try 
                {
                    lastURL = alertURLQueue.peek();
                    long diff = new Date().getTime() - lastURL.getHittingTime().getTime();
                    if(diff < (alertGapInMillisecond-100))
                    {
                        Thread.sleep(alertGapInMillisecond - diff -100);
                        alertURLQueue.remove();
                        break;
                    }
                    alertURLQueue.remove();
                } catch (InterruptedException ex) {
                    Logger.getLogger(AlertTracker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
