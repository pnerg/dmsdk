package org.dmonix.util.comparators;

import org.dmonix.AbstractTestCase;

/**
 * Test the class <code>FileDateComparato</code>.
 * 
 * @author Peter Nerg
 */
public class TestIgnoreCaseComparator extends AbstractTestCase {

    public void testCompareIgnoreCase() {
        IgnoreCaseComparator<String> comparator = new IgnoreCaseComparator<String>();
        assertFalse("Expected the comparator to be case-insensitive", comparator.isCaseSensitive());
        assertTrue(comparator.compare("a", "b") < 0);
        assertTrue(comparator.compare("a", "B") < 0);
        assertTrue(comparator.compare("A", "b") < 0);
        assertTrue(comparator.compare("a", "a") == 0);
        assertTrue(comparator.compare("a", "A") == 0);
    }

    public void testCompareWithCase() {
        IgnoreCaseComparator<String> comparator = new IgnoreCaseComparator<String>(false);
        assertTrue("Expected the comparator to be case-sensitive", comparator.isCaseSensitive());
        assertTrue(comparator.compare("a", "b") < 0);
        assertTrue(comparator.compare("a", "B") > 0);
        assertTrue(comparator.compare("A", "b") < 0);
        assertTrue(comparator.compare("a", "a") == 0);
        assertTrue(comparator.compare("a", "A") != 0);
    }

    public void testsetCaseSensitive() {
        IgnoreCaseComparator<String> comparator = new IgnoreCaseComparator<String>(true);
        assertFalse("Expected the comparator to be case-insensitive", comparator.isCaseSensitive());
        assertTrue(comparator.compare("a", "b") < 0);
        assertTrue(comparator.compare("a", "B") < 0);
        assertTrue(comparator.compare("A", "b") < 0);
        assertTrue(comparator.compare("a", "a") == 0);
        assertTrue(comparator.compare("a", "A") == 0);

        comparator.setCaseSensitive(true);
        assertTrue("Expected the comparator to be case-sensitive", comparator.isCaseSensitive());
        assertTrue(comparator.compare("a", "b") < 0);
        assertTrue(comparator.compare("a", "B") > 0);
        assertTrue(comparator.compare("A", "b") < 0);
        assertTrue(comparator.compare("a", "a") == 0);
        assertTrue(comparator.compare("a", "A") != 0);
    }

}
