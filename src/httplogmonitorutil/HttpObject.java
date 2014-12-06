/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httplogmonitorutil;

import java.util.Comparator;
import java.util.Date;

/**
 *
 * @author root
 */
public class HttpObject implements Comparator<HttpObject>, Comparable<HttpObject>{
    private String url;
    private Date hittingTime;
    private int hitCount;
    
    public HttpObject(){}
    public HttpObject(String url, Date hittingTime, int hitCount)
    {
        this.url = url;
        this.hittingTime = hittingTime;
        this.hitCount = hitCount;
    }
    
    public String getUrl()
    {
        return this.url;
    }
    public Date getHittingTime()
    {
        return this.hittingTime;
    }
    public int getHitCount()
    {
        return this.hitCount;
    }
    public void setHitCount(int hitCount)
    {
        this.hitCount = hitCount;
    }

    @Override
    public int compare(HttpObject o1, HttpObject o2) {
        return o2.getHitCount() - o1.getHitCount();
    }

    @Override
    public int compareTo(HttpObject o) {
        return((Integer)o.getHitCount()).compareTo(this.hitCount);
    }
    
}
