
package jkor.first.roboproperties;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.containsString;

/**
 *
 * @author jkoritzinsky
 */
public class PropertyLoaderTest {
    
    public PropertyLoaderTest() {
    }
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    
    @Test
    @SuppressWarnings("unused")
    public void constructionWithNullDefaultNoProfileThrowsNullPointerException()
            throws IOException {
        expectedException.expect(NullPointerException.class);
        PropertyLoader loader = new PropertyLoader((InputStream)null);
    }
    
    @Test
    @SuppressWarnings("unused")
    public void constructionWithNullDefaultFakeProfileThrowsNullPointerException()
            throws IOException {
        InputStream profile = new FileInputStream(folder.newFile());
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(containsString("defaultPropertyFile"));
        PropertyLoader loader = new PropertyLoader(null, profile);
    }
    
    @Test
    @SuppressWarnings("unused")
    public void constructionWithFakeDefaultNullProfileThrowsNullPointerException()
            throws IOException {
        InputStream defaultFile = new FileInputStream(folder.newFile());
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(containsString("profilePropertyFile"));
        PropertyLoader loader = new PropertyLoader(defaultFile, null);
    }
    
    @Test
    @SuppressWarnings("unused")
    public void constructionWithNullPropertiesCacheThrowsNullPointerException() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(containsString("propertyCache"));
        PropertyLoader loader = new PropertyLoader((Properties)null);
    }
    
    @Test
    public void getStringWithExistingPropertyReturnsString(){
        Properties prop = new Properties();
        String expectedValue = "I exist";
        prop.setProperty("test", expectedValue);
        PropertyLoader target = new PropertyLoader(prop);
        assertEquals(expectedValue, target.getString("test"));
    }
    
    @Test
    public void getStringWithNonExistantPropertyReturnsNull(){
        PropertyLoader target = new PropertyLoader(new Properties());
        assertNull(target.getString("I don't exist"));
    }
    
    @Test
    public void getStringWithNullPropertyKeyThrowsNullPointerException(){
        PropertyLoader target = new PropertyLoader(new Properties());
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(containsString("key"));
        target.getString(null);
    }
    
    @Test
    public void getIntWithNullPropertyKeyThrowsNullPointerException(){
        PropertyLoader target = new PropertyLoader(new Properties());
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(containsString("key"));
        target.getInt(null);
    }
    
    @Test
    public void getIntWithNonExistantPropertyKeyReturnsNull(){
        PropertyLoader target = new PropertyLoader(new Properties());
        assertNull(target.getInt("I don't exist"));
    }
    
    
    @Test
    public void getIntWithExistantKeyForIntPropertyReturnsInt(){
        Properties prop = new Properties();
        String expectedValue = "1";
        prop.setProperty("test", expectedValue);
        PropertyLoader target = new PropertyLoader(prop);
        assertEquals(1, target.getInt("test").intValue());
    }
    
    @Test
    public void getIntWithExistantKeyForNonIntPropertyThrowsNumberFormatException(){
        Properties prop = new Properties();
        String expectedValue = "I'm not a number";
        prop.setProperty("test", expectedValue);
        PropertyLoader target = new PropertyLoader(prop);
        expectedException.expect(NumberFormatException.class);
        target.getInt("test");
    }
    
    @Test
    public void getDoubleWithNullPropertyKeyThrowsNullPointerException(){
        PropertyLoader target = new PropertyLoader(new Properties());
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(containsString("key"));
        target.getDouble(null);
    }
    
    @Test
    public void getDoubleWithNonExistantPropertyKeyReturnsNull(){
        PropertyLoader target = new PropertyLoader(new Properties());
        assertNull(target.getDouble("I don't exist"));
    }
    
    
    @Test
    public void getDoubleWithExistantKeyForDoublePropertyReturnsDouble(){
        Properties prop = new Properties();
        String expectedValue = "1.0";
        prop.setProperty("test", expectedValue);
        PropertyLoader target = new PropertyLoader(prop);
        assertEquals(1.0, target.getDouble("test").doubleValue(), 0.0);
    }
    
    @Test
    public void getDoubleWithExistantKeyForNonDoublePropertyThrowsNumberFormatException(){
        Properties prop = new Properties();
        String expectedValue = "I'm not a number";
        prop.setProperty("test", expectedValue);
        PropertyLoader target = new PropertyLoader(prop);
        expectedException.expect(NumberFormatException.class);
        target.getDouble("test");
    }
    
    private enum TestEnum {
        ValueInEnum
    }
    
    @Test
    public void getEnumWithNullEnumTypeThrowsNullReferenceException(){
        PropertyLoader target = new PropertyLoader(new Properties());
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(containsString("enumType"));
        target.getEnum(null, "");
    }
    
    @Test
    public void getEnumWithNullKeyThrowsNullReferenceException(){
        PropertyLoader target = new PropertyLoader(new Properties());
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(containsString("key"));
        target.getEnum(TestEnum.class, null);
    }
    
    @Test
    public void getEnumWithNonExistantKeyReturnsNull(){
        PropertyLoader target = new PropertyLoader(new Properties());
        assertNull(target.getEnum(TestEnum.class, "I don't exist"));
    }
    
    @Test
    public void getEnumWithValueNotInEnumThrowsIllegalArgumentException(){
        Properties properties = new Properties();
        properties.setProperty("testProperty", "NotInEnum");
        PropertyLoader target = new PropertyLoader(properties);
        expectedException.expect(IllegalArgumentException.class);
        target.getEnum(TestEnum.class, "testProperty");
    }
    
    @Test
    public void getEnumWithValueInEnumReturnsEnumValue(){
        Properties properties = new Properties();
        properties.setProperty("testProperty", "ValueInEnum");
        PropertyLoader target = new PropertyLoader(properties);
        assertEquals(TestEnum.ValueInEnum, target.getEnum(TestEnum.class, "testProperty"));
    }
}
