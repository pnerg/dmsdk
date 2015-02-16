package org.dmonix.util.comparators;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import org.dmonix.AbstractTestCase;
import org.dmonix.io.IOUtil;
import org.junit.After;
import org.junit.Before;

/**
 * Test the class <code>FileDateComparato</code>.
 * 
 * @author Peter Nerg
 */
public class TestFileDateComparator extends AbstractTestCase {

    private File file1 = new File("sss1.test");
    private File file2 = new File("dsda2.test");
    private File file3 = new File("aaaa3.test");
    private ArrayList<File> arrayList = new ArrayList<File>();

    @Before
    public void setUp() throws Exception {
        file1.createNewFile();
        Thread.sleep(200);

        file2.createNewFile();
        Thread.sleep(200);

        file3.createNewFile();

        arrayList.add(file2);
        arrayList.add(file3);
        arrayList.add(file1);
    }

    @After
    public void tearDown() throws Exception {
        IOUtil.deleteFile(file1);
        IOUtil.deleteFile(file2);
        IOUtil.deleteFile(file3);
    }

    public void testSortAscending() {
        FileDateComparator comparator = new FileDateComparator();
        comparator.setSortAscending(true);
        Collections.sort(arrayList, comparator);

        assertEquals(file1.getName(), arrayList.get(0).getName());
        assertEquals(file2.getName(), arrayList.get(1).getName());
        assertEquals(file3.getName(), arrayList.get(2).getName());
    }

    public void testSortDescending() {
        FileDateComparator comparator = new FileDateComparator();
        comparator.setSortAscending(false);
        Collections.sort(arrayList, comparator);

        assertEquals(file3.getName(), arrayList.get(0).getName());
        assertEquals(file2.getName(), arrayList.get(1).getName());
        assertEquals(file1.getName(), arrayList.get(2).getName());
    }
}
