package jkor.first.roboproperties;

/**
 * Provides a typed interface for loading properties from a property source.
 * @author Jeremy Koritzinsky
 *
 */
public abstract class AbstractPropertyLoader {
    
	/**
	 * Retrieves the property value for the given path as a string.
	 * @param path The path to search for.  None of the elements of the path are null.
	 * @return The value in the cache for the given path as a string.
	 */
	protected abstract String getProperty(String... path);
	
    /**
     * Fetches the property as a string.
     * @param path The path to search for.  None of the elements of the path can be null.
     * @return The value in the cache for the given path as a string.
     */
    public String getString(String... path){
        Assertions.NoneAreNull(path, "path");
        return getProperty(path);
    }
    
    /**
     * Gets the property value as an integer.
     * @param path The path to retrieve the value for.  None of the elements of the path can be null.
     * @return the value of the property as an Integer.
     * @throws NumberFormatException if the value cannot be parsed as an integer.
     */
    public Integer getInt(String... path){
        Assertions.NoneAreNull(path, "path");
        String value = getProperty(path);
        if(value == null) return null;
        return Integer.valueOf(value);
    }
    
    /**
     * Gets the property value as a floating point double.
     * @param path The path to retrieve the value for.  None of the elements of the path can be null.
     * @return the value of the property as a Double.
     * @throws NumberFormatException if the value cannot be parsed as an integer.
     */
    public Double getDouble(String... path){
        Assertions.NoneAreNull(path, "path");
        String value = getProperty(path);
        if(value == null) return null;
        return Double.valueOf(value);
    }
    
    /**
     * Gets the property value as the given enum type.
     * @param <T> Type of the Enum.
     * @param enumType The class instance for the enum type. Cannot be null.
     * @param path The property path to search for.  None of the elements of the path can be null.
     * @return The value of the property as the given enum type.
     */
    public <T extends Enum<T>> T getEnum(Class<T> enumType, String... path){
        Assertions.IsNotNull(enumType, "enumType");
        Assertions.NoneAreNull(path, "path");
        String value = getProperty(path);
        if(value == null) return null;
        return Enum.valueOf(enumType, value);
    }
}
