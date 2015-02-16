package org.dmonix;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import junit.framework.Assert;

/**
 * Base class for all testcases. The class will also force the JVM locale to be EN-US.
 * 
 * @author Peter Nerg
 * @since 2.0
 */
public abstract class AbstractTestCase extends Assert {

    /** The logger instance for this class */
    protected final Logger logger = Logger.getLogger(getClass().getName());

    /**
     * The time stamp for when the time measurement is started for test case. The time can either be set by directly accessing the variable or by executing
     * <code>startTimeMeasurement</code> The variable is automatically set in the constructor to the current system time.
     * 
     * @see #startTimeMeasurement()
     * @see #assertExecutionTime()
     * @see #assertExecutionTime(long)
     */
    protected long testStartTime;

    /**
     * The variable defines the maximum exution time for a test case. Set in the constructor.
     * 
     * @see #assertExecutionTime()
     */
    protected long maxExecutionTime;

    /**
     * Flag that states if an exception has been caught or not.
     */
    protected boolean exceptionCaught = false;

    static {
        Locale.setDefault(Locale.US);
        System.setProperty("user.country", Locale.US.getCountry());
        System.setProperty("user.language", Locale.US.getLanguage());
        System.setProperty("user.variant", Locale.US.getVariant());
    }

    /**
     * Sets the logging level.
     * 
     * @param level
     * @see java.util.logging.Level
     */
    protected void setLogLevel(Level level) {
        Enumeration<String> names = LogManager.getLogManager().getLoggerNames();
        while (names.hasMoreElements()) {
            LogManager.getLogManager().getLogger(names.nextElement()).setLevel(level);
        }
    }

    /**
     * Set the flag that states if an exception has been caught or not.
     * 
     * @see #exceptionCaught
     * @see #assertExceptionCaught()
     */
    protected void setExceptionCaught() {
        this.exceptionCaught = true;
    }

    /**
     * Assert that the current time minus the start time is less than or equal to the maximum allowed execution time.
     * 
     * <pre>
     * I.e.<code>System.currentTimeMillis() - startTime &lt;= maxExecutionTime</code>
     * This method will use the internal variable for measuring the start time.
     * This time stamp can be set by executing<code>startTimeMeasurement</code>
     * The maxumum allowed execution time has to be set in the constructor.
     * </pre>
     * 
     * @see #startTimeMeasurement
     */
    protected void assertExecutionTime() {
        assertExecutionTime(this.maxExecutionTime, this.testStartTime);
    }

    /**
     * Assert that the current time minus the start time is less than or equal to the maximum allowed execution time.
     * 
     * <pre>
     * I.e. <code>System.currentTimeMillis() - startTime &lt;= maxExecutionTime</code>
     * This method will use the internal variable for measuring the start time.
     * This time stamp can be set by executing<code>startTimeMeasurement</code>
     * </pre>
     * 
     * @param maxExecutionTime
     *            The allowed execution time
     * @see #startTimeMeasurement
     */
    protected void assertExecutionTime(long maxExecutionTime) {
        assertExecutionTime(maxExecutionTime, this.testStartTime);
    }

    /**
     * Assert that the current time minus the start time is less than or equal to the maximum allowed execution time. I.e.
     * <code>System.currentTimeMillis() - startTime <= maxExecutionTime</code>
     * 
     * @param maxExecutionTime
     *            The allowed execution time
     * @param startTime
     *            The start time
     */
    protected void assertExecutionTime(long maxExecutionTime, long startTime) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        assertTrue("Execution time [" + elapsedTime + " millis] exceeded the maximum time [" + maxExecutionTime + " millis]", elapsedTime <= maxExecutionTime);
        logger.info("Time for executing the test : " + elapsedTime + " millis, maximum allowed time is " + maxExecutionTime + " millis");
    }

    /**
     * Assert that the <code>exceptionCaught</code> attribute has been set.
     * 
     * @see #exceptionCaught
     * @see #setExceptionCaught()
     */
    protected void assertExceptionCaught() {
        this.assertExceptionCaught("Expected an exception.");
    }

    /**
     * Assert that the <code>exceptionCaught</code> attribute has been set.
     * 
     * @param message
     *            The message to use in case the assertion fails.
     * @see #exceptionCaught
     * @see #setExceptionCaught()
     */
    protected void assertExceptionCaught(String message) {
        assertTrue(message, exceptionCaught);
    }

    /**
     * Assert that the content of the input collections are equal.
     * 
     * @param expected
     *            The expected collection
     * @param actual
     *            The actual collection
     */
    protected void assertCollections(Collection<?> expected, Collection<?> actual) {
        assertCollectionSize(actual, expected.size());

        Object o1, o2;

        Iterator<?> iterator1 = expected.iterator();
        Iterator<?> iterator2 = actual.iterator();
        while (iterator1.hasNext()) {
            o1 = iterator1.next();
            o2 = iterator2.next();
            assertEquals("The objects don't match", o1, o2);
        }
    }

    /**
     * Assert that the input collection is not null and not empty.
     * 
     * @param collection
     *            The collection to assert
     */
    protected void assertCollectionNotEmpty(Collection<?> collection) {
        assertNotNull("The collection was null", collection);
        if (collection.size() == 0) {
            fail("The collection was empty");
        }
    }

    /**
     * Assert that the input collection is not null and has the expected size.
     * 
     * @param collection
     *            The collection to assert
     * @param expectedSize
     *            The expected size of the collection
     */
    protected void assertCollectionSize(Collection<?> collection, int expectedSize) {
        assertNotNull("The collection was null", collection);
        if (collection.size() != expectedSize) {
            fail("The size of the collection [" + collection.size() + "] does not match the expected size [" + expectedSize + "]");
        }
    }

    /**
     * Assert that the input collection is not null and has the expected size.
     * 
     * @param collection
     *            The collection to assert
     * @param minSize
     *            The minumum size of the collection
     * @param maxSize
     *            The maximum size of the collection
     */
    protected void assertCollectionSizeRange(Collection<?> collection, int minSize, int maxSize) {
        assertNotNull("The collection was null", collection);
        if (collection.size() < minSize || collection.size() > maxSize) {
            fail("The size of the collection [" + collection.size() + "] does not match the expected range [" + minSize + "-" + maxSize + "]");
        }
    }

    /**
     * Assert that the input map is not null and not empty.
     * 
     * @param map
     *            The map to assert
     */
    protected void assertMapNotEmpty(Map<?, ?> map) {
        assertNotNull("The collection was null", map);
        if (map.size() == 0) {
            fail("The collection was empty");
        }
    }

    /**
     * Assert that the input map is not null and has the expected size.
     * 
     * @param map
     *            The map to assert
     * @param expectedSize
     *            The expected size of the collection
     */
    protected void assertMapSize(Map<?, ?> map, int expectedSize) {
        assertNotNull("The map was null", map);
        if (map.size() != expectedSize) {
            fail("The size of the map [" + map.size() + "] does not match the expected size [" + expectedSize + "]");
        }
    }

    /**
     * Assert that the content of the input maps are equal.
     * 
     * @param expected
     *            The expected map
     * @param actual
     *            The actual map
     */
    protected void assertMaps(Map<?, ?> expected, Map<?, ?> actual) {
        assertMapSize(actual, expected.size());

        Object o1, o2;
        for (Object key : expected.keySet()) {
            o1 = expected.get(key);
            o2 = actual.get(key);
            assertNotNull("Could not find the key [" + key + "]", o2);
            assertEquals("The objects don't match", o1, o2);
        }
    }

    /**
     * Put the current thread to sleep for the provided time.
     * 
     * @param millis
     *            The time in millis
     */
    protected void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
    }

    /**
     * This method will set the start time stamp for when the testcase measured.
     * 
     * @see #testStartTime
     */
    protected void startTimeMeasurement() {
        this.testStartTime = System.currentTimeMillis();
    }
}
