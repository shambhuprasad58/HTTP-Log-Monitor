/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httplogmonitorutil;

import java.util.List;
import org.jnetpcap.PcapIf;

/**
 *
 * @author root
 */
public class UserPreferences {
    private int threshold;
    private PcapIf device;
    private int timeGapInMillisecond, alertGapInMillisecond;
    
    public UserPreferences(){};
    public UserPreferences(int threshold, PcapIf device,int timeGapInMillisecond, int alertGapInMillisecond)
    {
        this.threshold = threshold;
        this.device = device;
        this.timeGapInMillisecond = timeGapInMillisecond;
        this.alertGapInMillisecond = alertGapInMillisecond;
    }
    
    public int getThreshold()
    {
        return this.threshold;
    }
    public PcapIf getDevice()
    {
        return this.device;
    }
    public int getTimeGapInMillisecond()
    {
        return this.timeGapInMillisecond;
    }
    public int getAlertGapInMillisecond()
    {
        return this.alertGapInMillisecond;
    }
}
