
package jkor.first.roboproperties;

/**
 *
 * @author jkoritzinsky
 */
class Assertions {
    static void IsNotNull(Object obj, String parameterName) {
        if(obj == null) {
            throw new NullPointerException(parameterName + " cannot be null.");
        }
    }
}
