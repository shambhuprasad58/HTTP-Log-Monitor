/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httplogmonitor;

import httplogmonitorutil.HttpObject;
import httplogmonitorutil.Utility;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class MostHitsCalculator extends TimerTask
{
    LinkedBlockingQueue<HttpObject> mostHitsURLQueue;
    LinkedBlockingQueue<ArrayList<HttpObject>> mostHitsTopURL;
    
    public MostHitsCalculator(LinkedBlockingQueue<HttpObject> mostHitsURLQueue, LinkedBlockingQueue<ArrayList<HttpObject>> mostHitsTopURL)
    {
        this.mostHitsURLQueue = mostHitsURLQueue;
        this.mostHitsTopURL = mostHitsTopURL;
    }
    
    @Override
    public void run() 
    {
        if(mostHitsURLQueue.isEmpty())
            return;
        System.out.println("Most hits calculator");
        HashMap<String, HttpObject> HitCountMapping = new HashMap<>();
        while(!mostHitsURLQueue.isEmpty())
        {
            try {
                HttpObject thisHit = mostHitsURLQueue.take();
                String section = Utility.getSection(thisHit.getUrl());
                if(HitCountMapping.containsKey(section))
                {
                    HttpObject temp = HitCountMapping.get(section);
                    temp.setHitCount(temp.getHitCount()+1);
                    HitCountMapping.put(section, temp);
                }
                else
                {
                    thisHit.setHitCount(1);
                    HitCountMapping.put(section, thisHit);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(MostHitsCalculator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ArrayList<HttpObject> topFive = new ArrayList<>();
        Iterator<Map.Entry<String, HttpObject>> iterator = HitCountMapping.entrySet().iterator();
        while(iterator.hasNext())
        {
            topFive.add(iterator.next().getValue());
            removeMinimum(topFive);
        }
        try {
            Collections.sort(topFive);
            mostHitsTopURL.put(topFive);
        } catch (InterruptedException ex) {
            Logger.getLogger(MostHitsCalculator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void removeMinimum(ArrayList<HttpObject> list)
    {
        if(list.size() < 6)
            return;
        HttpObject minimum = new HttpObject("", null, Integer.MAX_VALUE);
        for(HttpObject obj : list)
        {
            if(obj.getHitCount() < minimum.getHitCount())
                minimum = obj;
        }
        list.remove(minimum);
    }
}
