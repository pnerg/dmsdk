package org.dmonix.xml;

import org.dmonix.AbstractTestCase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TestXMLUtil extends AbstractTestCase {

    private final Document doc;

    public TestXMLUtil() {
        /*
         * <root> <name> <first>Peter</first> <last>Nerg</last> </name> <salary currency="sek">1</salary> </root>
         */
        doc = XMLUtil.newDocument();
        Element root = XMLUtil.setRootElement(doc, "root");
        Element name = XMLUtil.appendChildElement(root, "name");
        XMLUtil.appendChildElement(name, "first", "Peter");
        XMLUtil.appendChildElement(name, "last", "Nerg");
        Element salary = XMLUtil.appendChildElement(root, "salary", String.valueOf(1));
        salary.setAttribute("currency", "sek");
    }

    public void testCompareIllegalArguments() {
        assertFalse(XMLUtil.compare(null, doc));
        assertFalse(XMLUtil.compare(doc, null));
        assertFalse(XMLUtil.compare((Document) null, null));
    }

    public void testCompareSameDocument() {
        assertTrue(XMLUtil.compare(doc, doc));
    }

    public void testCompare() {
        Document doc2 = XMLUtil.newDocument();
        Element root = XMLUtil.setRootElement(doc2, "root");
        Element name = XMLUtil.appendChildElement(root, "name");
        XMLUtil.appendChildElement(name, "first", "Peter");
        XMLUtil.appendChildElement(name, "last", "Nerg");
        Element salary = XMLUtil.appendChildElement(root, "salary", String.valueOf(1));
        salary.setAttribute("currency", "sek");

        assertTrue(XMLUtil.compare(doc, doc2));
    }

    public void testCompare2() {
        Document doc2 = XMLUtil.newDocument();
        Element root = XMLUtil.setRootElement(doc2, "root");
        Element salary = XMLUtil.appendChildElement(root, "salary", String.valueOf(1));
        salary.setAttribute("currency", "sek");
        Element name = XMLUtil.appendChildElement(root, "name");
        XMLUtil.appendChildElement(name, "last", "Nerg");
        XMLUtil.appendChildElement(name, "first", "Peter");

        assertTrue(XMLUtil.compare(doc, doc2));
    }
}
