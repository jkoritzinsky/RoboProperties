/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jkor.first.roboproperties;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

/**
 *
 * @author jkoritzinsky
 */
public class PropertyLoader {
    private final Properties cache;
    public PropertyLoader(InputStream defaultPropertyFile)
            throws IOException
    {
        cache = new Properties();
        cache.load(defaultPropertyFile);
    }
    
    public PropertyLoader(InputStream defaultPropertyFile, InputStream profilePropertyFile)
            throws IOException
    {
        Properties defaultProperties = new Properties();
        defaultProperties.load(defaultPropertyFile);
        cache = new Properties(defaultProperties);
        cache.load(profilePropertyFile);
    }
}
