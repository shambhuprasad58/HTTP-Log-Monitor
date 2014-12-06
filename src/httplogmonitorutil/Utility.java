/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httplogmonitorutil;

/**
 *
 * @author root
 */
public class Utility {
    public static String getSection(String url)
    {
        String section = "";
        if(url.contains("http://"))
        {
            section = url.replaceFirst("http://", "~___~");
            String[] parts = section.split("/");
            if(parts.length > 1)
                section = parts[0] + parts[1];
            else
                section = parts[0];
            section = section.replace("~___~", "http://");
        }
        return section;
    }
    
    public static String removeExtra(String url)
    {
        String trimmedContent = url.replaceFirst("http://", "");
        trimmedContent = url.replaceFirst("www.", "");
        return trimmedContent;
    }
    
}
