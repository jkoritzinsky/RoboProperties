
package jkor.first.roboproperties;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

/**
 * This class loads properties using {@link java.util.Properties} and allows fetching them with a typed interface.
 *
 * @author Jeremy Koritzinsky
 */
public class PropertyLoader {
    private final Properties cache;
    /**
     * Constructs a PropertyLoader with a single property file.
     * @param defaultPropertyFile The file to load the properties from. Cannot be null.
     * @throws IOException if the property file cannot be loaded.
     */
    public PropertyLoader(InputStream defaultPropertyFile)
            throws IOException {
        Assertions.IsNotNull(defaultPropertyFile, "defaultPropertyFile");
        cache = new Properties();
        cache.load(defaultPropertyFile);
    }
    
    /**
     * Constructs a PropertyLoader with a default property file and a profile override.
     * @param defaultPropertyFile The file to load the default properties from. Cannot be null.
     * @param profilePropertyFile The file to load the profile override properties from. Cannot be null.
     * @throws IOException if either property file cannot be loaded. 
     */
    public PropertyLoader(InputStream defaultPropertyFile, InputStream profilePropertyFile)
            throws IOException {
        Assertions.IsNotNull(defaultPropertyFile, "defaultPropertyFile");
        Assertions.IsNotNull(profilePropertyFile, "profilePropertyFile");
        Properties defaultProperties = new Properties();
        defaultProperties.load(defaultPropertyFile);
        cache = new Properties(defaultProperties);
        cache.load(profilePropertyFile);
    }
    
    /**
     * Constructs a PropertyLoader with propertyCache as it's cache.
     * Note: Makes a shallow copy of the passed in object.  Later changes to propertyCache will not affect the PropertyLoader.
     * @param propertyCache The {@link java.util.Properties} object to use as a cache. Cannot be null.
     */
    public PropertyLoader(Properties propertyCache) {
        Assertions.IsNotNull(propertyCache, "propertyCache");
        cache = propertyCache;
    }
    
    /**
     * Fetches the property as a string from the cache.
     * @param key The key to search for in the cache.  Cannot be null.
     * @return The value in the cache for the given key as a string.
     */
    public String getString(String key){
        Assertions.IsNotNull(key, "key");
        return cache.getProperty(key);
    }
    
    /**
     * Gets the property value as an integer.
     * @param key The key to retrieve the value for.
     * @return the value of the property as an Integer.
     * @throws NumberFormatException if the value cannot be parsed as an integer.
     */
    public Integer getInt(String key){
        Assertions.IsNotNull(key, "key");
        String value = cache.getProperty(key);
        if(value == null) return null;
        return Integer.valueOf(value);
    }
}
