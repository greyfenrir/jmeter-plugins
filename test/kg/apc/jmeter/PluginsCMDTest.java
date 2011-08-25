// TODO: cover all parameters
package kg.apc.jmeter;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ListIterator;
import kg.apc.emulators.TestJMeterUtils;
import org.apache.jmeter.util.JMeterUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author undera
 */
public class PluginsCMDTest {

    private String basedir;

    public PluginsCMDTest() {
        String file = this.getClass().getResource("short.jtl").getPath();
        basedir = file.substring(0, file.lastIndexOf("/"));
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        TestJMeterUtils.createJmeterEnv();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        JMeterUtils.setJMeterHome(basedir);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of processParams method, of class PluginsCMD.
     */
    @Test
    public void testProcessParams() {
        System.out.println("processParams");
        String[] args = null;
        PluginsCMD instance = new PluginsCMD();
        int expResult = 0;
        int result = instance.processParams(args);
        assertEquals(expResult, result);
    }

    @Test
    // issue 39
    public void testProcessParams_aggreg() throws IOException {
        System.out.println("processParams aggregate");
        File f = File.createTempFile("test", ".csv");
        String str = "--tool Reporter --generate-csv " + f.getAbsolutePath() + " "
                + "--input-jtl " + basedir + "/few.jtl "
                + "--aggregate-rows yes --plugin-type ResponseTimesOverTime";
        String[] args = str.split(" +");
        PluginsCMD instance = new PluginsCMD();
        int expResult = 0;
        int result = instance.processParams(args);
        assertEquals(expResult, result);
        assertTrue(78 == f.length() || 81 == f.length()); // 78 at linux, 81 at windows because or \r\n
    }

    @Test
    // issue 47
    public void testProcessParams_outliers() throws IOException {
        System.out.println("processParams outliers");
        File f = File.createTempFile("test", ".png");
        String str = " --tool Reporter --width 1000 --height 300 "
                + "--prevent-outliers yes "
                + "--plugin-type ResponseTimesDistribution"
                + " --generate-png " + f.getAbsolutePath() + " "
                + "--input-jtl " + basedir + "/results_issue_47.jtl";
        String[] args = str.split(" +");
        PluginsCMD instance = new PluginsCMD();
        int expResult = 0;
        int result = instance.processParams(args);
        assertEquals(expResult, result);
        System.out.println(f.length());
        assertTrue(14000 <= f.length());
    }

    @Test
    // issue 64
    public void testProcessParams_issue64() throws IOException {
        System.out.println("processParams outliers");
        File f = File.createTempFile("test", ".png");
        String str = "--tool Reporter --width 800 --height 600 "
                + "--plugin-type HitsPerSecond  "
                + "--aggregate-rows yes "
                + "--generate-png " + f.getAbsolutePath() + " "
                + "--input-jtl " + basedir + "/results_issue_47.jtl";
        String[] args = str.split(" +");
        PluginsCMD instance = new PluginsCMD();
        try {
            instance.processParams(args);
            fail("HitsPerSec don't handle aggregates");
        } catch (UnsupportedOperationException e) {
        }
    }

    /**
     * Test of processParams method, of class PluginsCMD.
     */
    @Test
    public void testProcessParams_StringArr() {
        System.out.println("processParams");
        String[] args = null;
        PluginsCMD instance = new PluginsCMD();
        int expResult = 0;
        int result = instance.processParams(args);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of processParams method, of class PluginsCMD.
     */
    @Test
    public void testProcessParams_ListIterator() {
        System.out.println("processParams");
        ListIterator<String> args = null;
        PluginsCMD instance = new PluginsCMD();
        int expResult = 0;
        int result = instance.processParams(args);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of showHelp method, of class PluginsCMD.
     */
    @Test
    public void testShowHelp() {
        System.out.println("showHelp");
        PrintStream os = null;
        PluginsCMD instance = new PluginsCMD();
        instance.showHelp(os);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
