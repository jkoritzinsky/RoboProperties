
package jkor.first.roboproperties;

import java.util.Arrays;

/**
 * Class for commonly used assertions in this library.
 * @author Jeremy Koritzinsky
 */
class Assertions {
    static void IsNotNull(Object obj, String parameterName) {
        if(obj == null) {
            throw new NullPointerException(parameterName + " cannot be null.");
        }
    }
    
    static void NoneAreNull(Object[] array, String parameterName) {
    	IsNotNull(array, parameterName);
    	NoneAreNull(Arrays.asList(array), parameterName);
    }
    
    static void NoneAreNull(Iterable<Object> iterable, String parameterName) {
    	IsNotNull(iterable, parameterName);
    	String elementName = "element of " + parameterName;
    	for(Object obj : iterable)
    	{
    		IsNotNull(obj, elementName);
    	}
    }
}
