package org.dmonix.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dmonix.AbstractTestCase;
import org.dmonix.io.NullOutputStream;
import org.dmonix.io.NullWriter;
import org.junit.Before;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;

/**
 * Test the class <code>org.dmonix.xml.XMLDocument</code>.
 * 
 * @author Peter Nerg
 * @since 2.0
 * @see XMLDocument
 */
public class TestXMLDocument extends AbstractTestCase {

    private EntityResolver entityResolver = null;
    private ErrorHandler errorHandler = null;
    private XMLDocument xmlDocument = null;

    @Before
    public void setUp() throws Exception {
        this.entityResolver = new DefaultEntityResolver();
        this.errorHandler = new DefaultErrorHandler();
        xmlDocument = this.newXMLDocument();
    }

    /**
     * Test <code>new XMLDocument()</code>.
     * 
     * @throws Exception
     */
    public void testNewXMLDocument() throws Exception {
        assertNotNull(this.xmlDocument);
    }

    // /**
    // * Test <code>new XMLDocument(File)</code>.
    // * @throws Exception
    // */
    // public void testNewXMLDocumentFromFile() throws Exception {
    // XMLDocument doc = new XMLDocument(new File("test/test.xml"));
    // assertNotNull(doc);
    // }
    //
    // /**
    // * Test <code>new XMLDocument(File, EntityResolver, ErrorHandler)</code>.
    // * @throws Exception
    // */
    // public void testNewXMLDocumentFromFile2() throws Exception {
    // XMLDocument doc = new XMLDocument(new File("test/test.xml"), entityResolver, errorHandler);
    // assertNotNull(doc);
    // }
    //
    // /**
    // * Test <code>new XMLDocument(InputStream)</code>.
    // * @throws Exception
    // */
    // public void testNewXMLDocumentFromStream() throws Exception {
    // XMLDocument doc = new XMLDocument(new FileInputStream("test/test.xml"));
    // assertNotNull(doc);
    // }
    //
    // /**
    // * Test <code>new XMLDocument(InputStream, EntityResolver, ErrorHandler)</code>.
    // * @throws Exception
    // */
    // public void testNewXMLDocumentFromStream2() throws Exception {
    // XMLDocument doc = new XMLDocument(new FileInputStream("test/test.xml"), entityResolver, errorHandler);
    // assertNotNull(doc);
    // }
    //
    // /**
    // * Test <code>new XMLDocument(InputStream)</code>.
    // * @throws Exception
    // */
    // public void testNewXMLDocumentFromURI() throws Exception {
    // XMLDocument doc = new XMLDocument(new URL("file:test/test.xml"));
    // assertNotNull(doc);
    // }
    //
    // /**
    // * Test <code>new XMLDocument(InputStream, EntityResolver, ErrorHandler)</code>.
    // * @throws Exception
    // */
    // public void testNewXMLDocumentFromURI2() throws Exception {
    // XMLDocument doc = new XMLDocument(new URL("file:test/test.xml"), entityResolver, errorHandler);
    // assertNotNull(doc);
    // }
    //
    // /**
    // * Test <code>compare(XMLDocument)</code>
    // * @throws Exception
    // */
    // public void testCompare() throws Exception {
    // XMLDocument doc1 = new XMLDocument(new URL("file:test/test.xml"), entityResolver, errorHandler);
    // XMLDocument doc2 = new XMLDocument(new URL("file:test/test.xml"), entityResolver, errorHandler);
    // assertFalse(doc1.compare(null));
    // assertTrue(doc1.compare(doc2));
    // }

    /**
     * Test <code>setDocumentElement(String)</code>
     * 
     * @throws Exception
     */
    public void testSetRootElement() throws Exception {

        XMLElement root = this.xmlDocument.setDocumentElement("root");
        assertNotNull(root);

        assertEquals(root.getElementName(), this.xmlDocument.getDocumentElement().getElementName());
        assertEquals(root.getElementName(), "root");
    }

    /**
     * Test <code>createElement(String)</code>
     * 
     * @throws Exception
     */
    public void testCreateElement() throws Exception {
        XMLDocument xmlDocument = new XMLDocument();
        XMLElement e = xmlDocument.createElement("e1");
        assertNull(xmlDocument.getDocumentElement());
        assertEquals("e1", e.getElementName());
    }

    /**
     * Test <code>documentToHashtable()</code>
     * 
     * @throws Exception
     */
    public void testDocumentToHashtable() throws Exception {
        Map<String, String> expected = new HashMap<String, String>();
        expected.put("root", "");
        expected.put("a", "");
        expected.put("a1", "a1");
        expected.put("a2", "a2");
        expected.put("b", "");
        expected.put("b1", "b1");
        expected.put("b2", "b2");
        expected.put("b3", "b3");

        assertMaps(expected, this.xmlDocument.documentToMap());
    }

    /**
     * Test <code>setUseUpperCase()</code>
     * 
     * @throws Exception
     */
    public void testSetUseUpperCase() {
        assertFalse(this.xmlDocument.getUseUpperCase());
        xmlDocument.setUseUpperCase(true);
        assertTrue(this.xmlDocument.getUseUpperCase());
    }

    /**
     * Test <code>getElement(String)</code>
     * 
     * @throws Exception
     */
    public void testGetElement() {
        assertNull(this.xmlDocument.getElement("not-exist"));
        assertElement("a1", "a1", this.xmlDocument.getElement("a1"));
        assertElement("b2", "b2", this.xmlDocument.getElement("b2"));
    }

    /**
     * Test <code>getElementsByTagName(String, List)</code>
     * 
     * @throws Exception
     */
    public void testGetElementsByTagName() {
        List<XMLElement> list = new ArrayList<XMLElement>();
        this.xmlDocument.getElementsByTagName("b3", list);
        super.assertCollectionSize(list, 3);
    }

    /**
     * Test <code>getElementsValue</code>
     * 
     * @throws Exception
     */
    public void testGetElementsValue() {
        assertEquals("Unexpected element value", "b1", this.xmlDocument.getElementValue("b1"));
        assertEquals("Unexpected element value", "default", this.xmlDocument.getElementValue("not-exist", "default"));
    }

    public void testRemoveElementByTagName() {
        assertEquals(3, this.xmlDocument.removeElementByTagName("b3"));

        List<XMLElement> list = new ArrayList<XMLElement>();
        this.xmlDocument.getElementsByTagName("b3", list);
        super.assertCollectionSize(list, 0);
    }

    /**
     * Test <code>toStream(OutputStream)</code>
     * 
     * @throws Exception
     */
    public void testToStream() throws Exception {
        XMLDocument doc = this.newXMLDocument();
        doc.toStream(new NullOutputStream());
    }

    /**
     * Test <code>toStream(OutputStream, boolean)</code>
     * 
     * @throws Exception
     */
    public void testToStream2() throws Exception {
        XMLDocument doc = this.newXMLDocument();
        doc.toStream(new NullOutputStream(), true);
    }

    /**
     * Test <code>toWriter(Writer)</code>
     * 
     * @throws Exception
     */
    public void testToWriter() throws Exception {
        XMLDocument doc = this.newXMLDocument();
        doc.toWriter(new NullWriter());
    }

    /**
     * Test <code>toWriter(Writer)</code>
     * 
     * @throws Exception
     */
    public void testToFile() throws Exception {
        File f = new File("tmp.xml");
        XMLDocument doc = this.newXMLDocument();
        doc.toFile(f);
        f.delete();
    }

    /**
     * Test <code>testToString()</code>
     * 
     * @throws Exception
     */
    public void testToString() throws Exception {
        XMLDocument doc = this.newXMLDocument();
        assertNotNull(doc.toString());
    }

    /**
     * Test <code>normalizeDocument()</code>
     * 
     * @throws Exception
     */
    public void testNormalizeDocument() throws Exception {
        XMLDocument doc = this.newXMLDocument();
        doc.normalizeDocument();
        assertNotNull(doc.toString());
    }

    /**
     * Creates a new empty XMLDocument
     * 
     * @return
     */
    private XMLDocument newXMLDocument() {
        XMLDocument doc = new XMLDocument();
        assertNotNull(doc);
        assertNull(doc.getDocumentElement());
        this.addData(doc);
        return doc;
    }

    /**
     * Adds data to an XMLDocument.
     * 
     * @return
     */
    private void addData(XMLDocument xmlDocument) {
        XMLElement xmlElement = xmlDocument.setDocumentElement("root");
        assertNotNull(xmlElement);

        XMLElement a = xmlElement.appendChildElement("a");
        assertNotNull(a);
        assertNotNull(a.appendChildElement("a1", "a1"));
        assertNotNull(a.appendChildElement("a2", "a2"));

        XMLElement b = xmlElement.appendChildElement("b");
        assertNotNull(b);
        assertNotNull(b.appendChildElement("b1", "b1"));
        assertNotNull(b.appendChildElement("b2", "b2"));
        assertNotNull(b.appendChildElement("b3", "b3"));
        assertNotNull(b.appendChildElement("b3", "b3"));
        assertNotNull(b.appendChildElement("b3", "b3"));
    }

    private void assertElement(String expectedName, String expectedValue, XMLElement xmlElement) {
        assertNotNull("The element is null", xmlElement);
        assertEquals("The names don't match", expectedName, xmlElement.getElementName());
        assertEquals("The values don't match", expectedValue, xmlElement.getElementValue());
    }

}
