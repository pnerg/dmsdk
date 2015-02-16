package org.dmonix.util;

import java.io.Serializable;

import org.dmonix.AbstractTestCase;
import org.dmonix.xml.XMLElementList;

public class TestClassUtil extends AbstractTestCase {

    public void testIsInterfaceImplemention() throws Exception {
        assertIsInterfaceImplemention(Class.forName("java.lang.Comparable"), TestClass.class, true, true);
        assertIsInterfaceImplemention(Class.forName("java.lang.Comparable"), TestClass.class, false, true);

        assertIsInterfaceImplemention(Class.forName("java.lang.Comparable"), TestClass2.class, true, true);
        assertIsInterfaceImplemention(Class.forName("java.lang.Comparable"), TestClass2.class, false, false);
        assertIsInterfaceImplemention(Class.forName("java.io.Serializable"), TestClass2.class, true, true);

        assertIsInterfaceImplemention(Class.forName("java.lang.Iterable"), TestClass.class, true, false);

        assertIsInterfaceImplemention(Class.forName("java.lang.Comparable"), TestClass3.class, true, true);
        assertIsInterfaceImplemention(Class.forName("java.io.Serializable"), TestClass3.class, true, true);
        assertIsInterfaceImplemention(Class.forName("java.lang.Comparable"), TestClass3.class, false, false);
        assertIsInterfaceImplemention(Class.forName("java.io.Serializable"), TestClass3.class, false, false);

        assertIsInterfaceImplemention(Class.forName("java.lang.Iterable"), XMLElementList.class, false, true);
    }

    private void assertIsInterfaceImplemention(Class interfac, Class implementer, boolean checkParent, boolean expected) {
        if (expected)
            assertTrue("The class [" + implementer + "] does not implement the interface [" + interfac + "]",
                    ClassUtil.isInterfaceImplemention(interfac, implementer, checkParent));
        else
            assertFalse("The class [" + implementer + "] was not expected to implement the interface [" + interfac + "]",
                    ClassUtil.isInterfaceImplemention(interfac, implementer, checkParent));
    }

    public void testPadNumber() {
        assertEquals("7", ClassUtil.padNumber(7, -69));

        assertEquals("7", ClassUtil.padNumber(7, 0));
        assertEquals("7", ClassUtil.padNumber(7, 1));
        assertEquals("07", ClassUtil.padNumber(7, 2));
        assertEquals("007", ClassUtil.padNumber(7, 3));
        assertEquals("007", ClassUtil.padNumber(7, 3));
        assertEquals("007", ClassUtil.padNumber(7, 3));

        assertEquals("69", ClassUtil.padNumber(69, 0));
        assertEquals("69", ClassUtil.padNumber(69, 1));
        assertEquals("69", ClassUtil.padNumber(69, 2));
        assertEquals("069", ClassUtil.padNumber(69, 3));
        assertEquals("0069", ClassUtil.padNumber(69, 4));
    }

    public void testPadString() {
        assertEquals("7", ClassUtil.padString("7", -69));

        assertEquals("7", ClassUtil.padString("7", 0));
        assertEquals("7", ClassUtil.padString("7", 1));
        assertEquals("07", ClassUtil.padString("7", 2));
        assertEquals("007", ClassUtil.padString("7", 3));
        assertEquals("007", ClassUtil.padString("7", 3));
        assertEquals("007", ClassUtil.padString("7", 3));

        assertEquals("69", ClassUtil.padString("69", 0));
        assertEquals("69", ClassUtil.padString("69", 1));
        assertEquals("69", ClassUtil.padString("69", 2));
        assertEquals("069", ClassUtil.padString("69", 3));
        assertEquals("0069", ClassUtil.padString("69", 4));
    }

    public void testOpenBrowser() {
        try {
            ClassUtil.openBrowser("http://www.dmonix.org");
        } catch (Exception e) {
        }
    }

    private class TestClass implements Comparable<TestClass> {
        public int compareTo(TestClass o) {
            return 0;
        }
    }

    private class TestClass2 extends TestClass implements Serializable {
        private static final long serialVersionUID = -5745385792265414774L;
    }

    private class TestClass3 extends TestClass2 {
        private static final long serialVersionUID = -424324234324234L;
    }

}
