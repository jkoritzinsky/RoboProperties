/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jkor.first.roboproperties;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.*;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;

/**
 *
 * @author jkoritzinsky
 */
public class PropertyLoaderTest {
    
    public PropertyLoaderTest() {
    }
    
    @Rule
    public ExpectedException expected = ExpectedException.none();
    
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    
    @Test
    public void constructionWithNullDefaultNoProfileThrowsNullPointerException()
            throws IOException {
        expected.expect(NullPointerException.class);
        PropertyLoader loader = new PropertyLoader(null);
    }
    
    @Test
    public void constructionWithNullDefaultFakeProfileThrowsNullPointerException()
            throws IOException {
        InputStream profile = new FileInputStream(folder.newFile());
        expected.expect(NullPointerException.class);
        expected.expectMessage(containsString("defaultPropertyFile"));
        PropertyLoader loader = new PropertyLoader(null, profile);
    }
    
    @Test
    public void constructionWithFakeDefaultNullProfileThrowsNullPointerException()
            throws IOException {
        InputStream defaultFile = new FileInputStream(folder.newFile());
        expected.expect(NullPointerException.class);
        expected.expectMessage(containsString("profilePropertyFile"));
        PropertyLoader loader = new PropertyLoader(defaultFile, null);
    }
    
}
