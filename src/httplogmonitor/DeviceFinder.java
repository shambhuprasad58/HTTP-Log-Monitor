/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httplogmonitor;

import java.util.ArrayList;
import java.util.List;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;

/**
 *
 * @author root
 */
public class DeviceFinder {
    
    public List<PcapIf> findAllDevices()
    {
        List<PcapIf> alldevs = new ArrayList<PcapIf>(); // Will be filled with NICs
        StringBuilder errbuf = new StringBuilder(); // For any error msgs

        /***************************************************************************
         * First get a list of devices on this system
         **************************************************************************/
        int r = Pcap.findAllDevs(alldevs, errbuf);
        if (r == Pcap.NOT_OK || alldevs.isEmpty()) {
                System.err.printf("Can't read list of devices, error is %s", errbuf
                    .toString());
                return alldevs;
        }

        System.out.println("Network devices found:");

        int i = 0;
        for (PcapIf device : alldevs) {
                String description =
                    (device.getDescription() != null) ? device.getDescription()
                        : "No description available";
                System.out.printf("#%d: %s [%s]\n", i++, device.getName(), description);
        }

        return alldevs;
    }
}
