package org.dmonix.xml;

import org.dmonix.AbstractTestCase;

public class TestXMLElement extends AbstractTestCase {

    public void testAppendChildElement() throws Exception {
        XMLDocument doc = new XMLDocument();
        assertNotNull(doc);

        XMLElement root = doc.setDocumentElement("root");
        assertNotNull(root);

        XMLElement e1 = root.appendChildElement("e1");
        assertNotNull(e1);
        assertEquals(1, root.getChildElements().size());

        XMLElement e2 = root.appendChildElement("e2");
        assertNotNull(e2);
        assertEquals(2, root.getChildElements().size());

        assertEquals(root.getElementName(), doc.getDocumentElement().getElementName());
    }

    public void testGetChildElementByTagName() throws Exception {
        XMLDocument doc = new XMLDocument();
        assertNotNull(doc);

        XMLElement root = doc.setDocumentElement("root");
        assertNotNull(root);

        root.appendChildElement("e1");
        root.appendChildElement("e2");
        root.appendChildElement("e3");

        assertEquals("e1", root.getChildElementByTagName("e1").getElementName());
        assertEquals("e2", root.getChildElementByTagName("e2").getElementName());
        assertEquals("e3", root.getChildElementByTagName("e3").getElementName());
    }

    public void testRemoveAllChildElements() throws Exception {
        XMLDocument doc = new XMLDocument();
        assertNotNull(doc);

        XMLElement root = doc.setDocumentElement("root");
        assertNotNull(root);

        root.appendChildElement("e1");
        root.appendChildElement("e2");
        root.appendChildElement("e3");

        root.removeAllChildElements();
        assertEquals(0, root.getChildElements().size());
    }

    public void testSetAttribute() throws Exception {
        XMLDocument doc = new XMLDocument();
        assertNotNull(doc);

        XMLElement root = doc.setDocumentElement("root");
        assertNotNull(root);

        root.setAttribute("a1", "v1");
        assertEquals("v1", root.getAttribute("a1"));

        root.setAttribute("a2", "v2");
        assertEquals("v2", root.getAttribute("a2"));

        root.setAttribute("a1", "v1.1");
        assertEquals("v1.1", root.getAttribute("a1"));

    }

    public void testSetElementValue() throws Exception {
        XMLDocument doc = new XMLDocument();
        assertNotNull(doc);

        XMLElement root = doc.setDocumentElement("root");
        assertNotNull(root);

        root.setElementValue("Element value 1");
        assertEquals("Element value 1", root.getElementValue());

        root.setElementValue("Element value 2");
        assertEquals("Element value 2", root.getElementValue());
    }

    public void testGetElementValue() throws Exception {
        XMLDocument doc = new XMLDocument();
        assertNotNull(doc);

        XMLElement root = doc.setDocumentElement("root");
        assertNotNull(root);

        root.setElementValue("Element value 1");
        assertEquals("Element value 1", root.getElementValue());

        root.setElementValue("Element value 2");
        assertEquals("Element value 2", root.getElementValue());
    }

    public void testInsertBefore() {
        XMLDocument doc = new XMLDocument();
        assertNotNull(doc);

        XMLElement root = doc.setDocumentElement("root");
        assertNotNull(root);

    }
}
