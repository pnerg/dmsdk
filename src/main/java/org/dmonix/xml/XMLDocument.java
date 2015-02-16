package org.dmonix.xml;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

/**
 * This class is wrapped around a <code>org.w3c.dom.Document</code> with added features.
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @since 1.1
 */
public class XMLDocument {

    /** The logger instance for this class and all sub classes. */
    protected final Logger log = Logger.getLogger(getClass().getName());

    /** The XML transformer. */
    protected Transformer transformer = null;

    /** The XML document builder. */
    protected DocumentBuilder docBuilder = null;

    /** The XML document that this class is wrapped around. */
    protected Document document;

    private boolean useUpperCase = false;
    private boolean ignoreCase = true;

    private ErrorHandler errorHandler;
    private EntityResolver entityResolver;

    /**
     * Creates an empty XML document.
     * 
     * @throws XMLConfigurationException
     *             In case of an error during configuration
     */
    public XMLDocument() throws XMLConfigurationException {
        configure();
        document = docBuilder.newDocument();
    }

    /**
     * Creates an XML document using an existing XML document from the provided document.
     * 
     * @param document
     *            The XML document
     * @throws XMLConfigurationException
     *             In case of an error during configuration
     */
    public XMLDocument(Document doc) throws XMLConfigurationException {
        configure();
        this.document = doc;
    }

    /**
     * Creates an XML document using an input stream.
     * 
     * @param istream
     *            The input stream
     * @throws IOException
     * @throws SAXException
     * @throws XMLConfigurationException
     *             In case of an error during configuration
     */
    public XMLDocument(InputStream istream) throws IOException, SAXException, XMLConfigurationException {
        configure();
        this.document = docBuilder.parse(istream);
    }

    /**
     * Creates an XML document using an input stream.
     * 
     * @param istream
     *            The input stream
     * @param resolver
     *            The entity resolver for the parser
     * @param handler
     *            The error handler for the parser
     * @throws IOException
     * @throws SAXException
     * @throws XMLConfigurationException
     *             In case of an error during configuration
     */
    public XMLDocument(InputStream istream, EntityResolver resolver, ErrorHandler handler) throws IOException, SAXException, XMLConfigurationException {
        configure();

        if (resolver != null)
            this.setEntityResolver(resolver);

        if (handler != null)
            this.setErrorHandler(handler);

        this.document = docBuilder.parse(istream);
    }

    /**
     * Creates an XML document using an existing XML document from file.
     * 
     * @param file
     *            The XML document
     * @throws IOException
     * @throws SAXException
     * @throws XMLConfigurationException
     *             In case of an error during configuration
     */
    public XMLDocument(File file) throws IOException, SAXException, XMLConfigurationException {
        configure();
        this.document = docBuilder.parse(file);
    }

    /**
     * Creates an XML document using an existing XML document from file.
     * 
     * @param file
     *            The XML document
     * @param resolver
     *            The entity resolver for the parser
     * @param handler
     *            The error handler for the parser
     * @throws IOException
     * @throws SAXException
     * @throws XMLConfigurationException
     *             In case of an error during configuration
     */
    public XMLDocument(File file, EntityResolver resolver, ErrorHandler handler) throws IOException, SAXException, XMLConfigurationException {
        configure();

        if (resolver != null)
            this.setEntityResolver(resolver);

        if (handler != null)
            this.setErrorHandler(handler);

        this.document = docBuilder.parse(file);
    }

    /**
     * Creates an XML document using an existing XML document from a URL.
     * 
     * @param url
     *            The XML document
     * @throws IOException
     * @throws SAXException
     * @throws XMLConfigurationException
     *             In case of an error during configuration
     */
    public XMLDocument(URL url) throws IOException, SAXException, XMLConfigurationException {
        configure();
        this.document = docBuilder.parse(url.openConnection().getInputStream());
    }

    /**
     * Creates an XML document using an existing XML document from a URL.
     * 
     * @param url
     *            The XML document
     * @param resolver
     *            The entity resolver for the parser
     * @param handler
     *            The error handler for the parser
     * @throws IOException
     * @throws SAXException
     * @throws XMLConfigurationException
     *             In case of an error during configuration
     */
    public XMLDocument(URL url, EntityResolver resolver, ErrorHandler handler) throws IOException, SAXException, XMLConfigurationException {
        configure();

        if (resolver != null)
            this.setEntityResolver(resolver);

        if (handler != null)
            this.setErrorHandler(handler);

        this.document = docBuilder.parse(url.openConnection().getInputStream());
    }

    /**
     * Compare this XMLDocument to another XMLDocument.
     * 
     * @param xmlDocument
     * @return
     */
    public boolean compare(XMLDocument xmlDocument) {
        if (xmlDocument == null)
            return false;

        return XMLUtil.compare(this.document, xmlDocument.document);
    }

    /**
     * Create a new element with the requested name
     * 
     * @param name
     *            The name of the element
     * @return The created element
     */
    public XMLElement createElement(String name) {
        return createElement(document.createElement(name));
    }

    /**
     * Converts the input document into a Map. The name of each found element will be the key in the Map and the value of the element will be the value in the
     * Map.<br>
     * I case the same element occurrs several time, the latest value will take precedence.
     * 
     * @return The Map, empty if no elements were found
     * @see XMLUtil#documentToMap(Document)
     */
    public Map<String, String> documentToMap() {
        return XMLUtil.documentToMap(this.document);
    }

    /**
     * Returns if all the HTML tags are to be in upper case or not.
     * 
     * @return true if all tags are written in uppercase, false otherwise
     */
    public boolean getUseUpperCase() {
        return this.useUpperCase;
    }

    /**
     * Sets if all the HTML tags are to be in upper case or not. <br>
     * This determines if the tags written to the document will be in upper or lower case. <br>
     * No browser will care if the tag is called &lt;table&gt; or &lt;TABLE&gt;. <br>
     * But an XML parser is case sensitive so if you plan to use this class to read a file it is advisable to stick to either upper or lower case. <br>
     * Default is false
     * 
     * @param upperCase
     *            true = upper case, false = lower case
     */
    public void setUseUpperCase(boolean upperCase) {
        this.useUpperCase = upperCase;
        this.ignoreCase = false;
    }

    /**
     * Get the root element of this document object.
     * 
     * @return The root element
     * @see org.w3c.dom.Document#getDocumentElement
     */
    public XMLElement getDocumentElement() {
        return createElement(document.getDocumentElement());
    }

    /**
     * Returns the first found element with the supplied name.
     * 
     * @param name
     *            The name of the element
     * @return The first found element, null if not found
     */
    public XMLElement getElement(String name) {
        NodeList list = document.getElementsByTagName(name);
        if (list.getLength() > 0)
            return createElement((Element) list.item(0));

        return null;
    }

    /**
     * Returns the first element if it exist and <code>null</code> if it does not exist.
     * 
     * @param list
     *            the list
     * @return the first element in the list
     */
    public XMLElement getElement(NodeList list) {
        if (list.getLength() > 0)
            return createElement((Element) list.item(0));

        return null;
    }

    /**
     * Returns the Element whose ID is provided by elementId.
     * 
     * @param elementId
     *            The id
     * @return The element, null if not found
     * @see org.w3c.dom.Document#getElementById
     */
    public XMLElement getElementById(String elementId) {
        return createElement(document.getElementById(elementId));
    }

    /**
     * Returns a XMLElementList of all the Elements with a given tag name in the order in which they are encountered in a preorder traversal of the Document
     * tree.
     * 
     * @param tagname
     *            The name of the tag
     * @return The list
     * @see org.w3c.dom.Document#getElementsByTagName
     * @deprecated use getElementsByTagName(String, List)
     */
    public XMLElementList getElementsByTagName(String tagname) {
        XMLElementList list = new XMLElementList();
        NodeList nodeList = document.getElementsByTagName(tagname);
        for (int i = 0; i < nodeList.getLength(); i++) {
            list.add(createElement((Element) nodeList.item(i)));
        }

        return list;
    }

    /**
     * Find and store all the Elements with a given tag name in the order in which they are encountered in a preorder traversal of the Document tree.
     * 
     * @param tagname
     *            The name of the tag
     * @param list
     *            The list in which to store the XMLElements
     * @return The numberof items added to the list
     * @see org.w3c.dom.Document#getElementsByTagName
     */
    public int getElementsByTagName(String tagname, List<XMLElement> list) {
        NodeList nodeList = document.getElementsByTagName(tagname);
        for (int i = 0; i < nodeList.getLength(); i++) {
            list.add(createElement((Element) nodeList.item(i)));
        }
        return list.size();
    }

    /**
     * Get the node value from the first element in the node list. <br>
     * The method expects that the nodes in the list are elements and that they have a a child node of the type Text that may be extracted.
     * 
     * @param list
     *            The node list
     * @return The node value of the node, empty String if not found or the method fails
     */
    public String getElementValue(NodeList list) {
        return this.getElementValue(list, "");
    }

    /**
     * Get the node value from the first element in the node list. <br>
     * The method expects that the nodes in the list are elements and that they have a a child node of the type Text that may be extracted.
     * 
     * @param list
     *            The node list
     * @param defaultValue
     *            The value to return in case no value was found
     * @return The node value of the node, empty String if not found or the method fails
     */
    public String getElementValue(NodeList list, String defaultValue) {
        if (list.getLength() == 0)
            return defaultValue;

        return createElement((Element) list.item(0)).getElementValue();
    }

    /**
     * Returns the value of first found element value with the supplied name.
     * 
     * @param name
     *            The name of the element
     * @return The first found element value, empty string if not found
     */
    public String getElementValue(String name) {
        return this.getElementValue(document.getElementsByTagName(name));
    }

    /**
     * Returns the first found element value with the supplied name.
     * 
     * @param name
     *            The name of the element
     * @param defaultValue
     *            The value to return in case no value was found
     * @return The first found element value
     */
    public String getElementValue(String name, String defaultValue) {
        return this.getElementValue(document.getElementsByTagName(name), defaultValue);
    }

    /**
     * Inserts an element before an existing elemet
     * 
     * @param newChild
     *            The element to insert
     * @param reference
     *            The element where to insert before
     */
    public void insertBefore(XMLElement newChild, XMLElement reference) {
        document.insertBefore(newChild.getElement(), reference.getElement());
    }

    /**
     * Recursively removes all empty child text nodes. <br>
     * The method will also trim the non empty text nodes, i.e. any white spaces in the beginning and the end of the text node will be removed.
     */
    public void normalizeDocument() {
        Element root = document.getDocumentElement();
        if (root == null || !root.hasChildNodes())
            return;

        XMLUtil.normalizeDocument(root);
    }

    /**
     * Remove the element whose ID is provided by elementId.
     * 
     * @param elementId
     *            The id
     * @return The removed element, null if not found
     */
    public XMLElement removeElementById(String elementId) {
        return new XMLElement(XMLUtil.removeElementById(this.document, elementId));
    }

    /**
     * Remove all the elements with the supplied name.
     * 
     * @param name
     *            The element name
     * @return The number of removed elements
     */
    public int removeElementByTagName(String name) {
        return XMLUtil.removeElementByTagName(this.document, name);
    }

    /**
     * Creates a new root element for the document. Any existing root element will be overwritten, thus descarding any previous data in the document.
     * 
     * @param elementName
     *            the name of the root element
     * @return the created root element
     */
    public XMLElement setDocumentElement(String elementName) {
        return createElement(XMLUtil.setRootElement(document, elementName));
    }

    /**
     * Set the XML document from a file. Any existing document will be discarded.
     * 
     * @param file
     * @throws IOException
     * @throws SAXException
     */
    public void setDocument(File file) throws IOException, SAXException {
        document = docBuilder.parse(file);
    }

    /**
     * Set the XML document from a URL. Any existing document will be discarded.
     * 
     * @param file
     * @throws IOException
     * @throws SAXException
     */
    public void setDocument(URL url) throws IOException, SAXException {
        document = docBuilder.parse(url.openStream());
    }

    /**
     * Specify the {@link EntityResolver} to be used to resolve entities present in the XML document to be parsed. Setting this to <code>null</code> will result
     * in the underlying implementation using it's own default implementation and behavior.
     * 
     * @param resolver
     *            The <code>EntityResolver</code> to be used to resolve entities present in the XML document to be parsed.
     */
    public void setEntityResolver(EntityResolver resolver) {
        this.docBuilder.setEntityResolver(resolver);
    }

    /**
     * Get the {@link EntityResolver} used by this object.
     * 
     * @return
     */
    public EntityResolver getEntityResolver() {
        return this.entityResolver;
    }

    /**
     * Specify the {@link ErrorHandler} to be used by the parser. Setting this to <code>null</code> will result in the underlying implementation using it's own
     * default implementation and behavior.
     * 
     * @param handler
     *            The <code>ErrorHandler</code> to be used by the parser.
     */
    public void setErrorHandler(ErrorHandler handler) {
        this.docBuilder.setErrorHandler(handler);
    }

    /**
     * Get the {@link ErrorHandler} used by this object.
     * 
     * @return
     */
    public ErrorHandler getErrorHandler() {
        return this.errorHandler;
    }

    /**
     * Set a property for the transformer.
     * 
     * @param property
     *            The name of the property (javax.xml.transform.OutputKeys)
     * @param value
     *            The value of the property
     * @see javax.xml.transform.OutputKeys
     */
    public void setTransformerProperty(String property, String value) {
        this.transformer.setOutputProperty(property, value);
    }

    /**
     * Write the XML document to a file. <br>
     * Any existing file will be overwritten. If the path/file does not exist the entire path including the file will be created.
     * 
     * @param file
     *            The output file
     * @return The result of the operation, true if successful false otherwise
     */
    public boolean toFile(File file) {
        if (file.exists()) {
            file.delete();
        } else {
            File parent = file.getParentFile();
            if (parent != null)
                parent.mkdirs();
        }

        try {
            if (log.isLoggable(Level.FINER)) {
                log.log(Level.FINER, "XML output:\n" + this.toString());
            }

            FileOutputStream fostream;
            try {
                fostream = new FileOutputStream(file);
            } catch (FileNotFoundException ex) {
                file.getParentFile().mkdirs();
                fostream = new FileOutputStream(file);
            }
            toStream(fostream);
            fostream.close();
            fostream = null;
        } catch (Exception ex) {
            log.log(Level.WARNING, ex.getMessage(), ex);
            return false;
        }

        return true;
    }

    /**
     * Writes the document to a supplied outputstream.
     * 
     * @param ostream
     *            The target stream
     * @throws IOException
     * @throws TransformerException
     */
    public void toStream(OutputStream ostream) throws IOException, TransformerException {
        toStream(ostream, false);
    }

    /**
     * Writes the document to a supplied outputstream.
     * 
     * @param ostream
     *            The target stream
     * @param closeStream
     *            If the outputstream is to be closed after write
     * @throws IOException
     * @throws TransformerException
     */
    public void toStream(OutputStream ostream, boolean closeStream) throws IOException, TransformerException {
        transformer.transform(new DOMSource(document), new StreamResult(new BufferedOutputStream(ostream)));
        ostream.flush();

        if (closeStream)
            ostream.close();
    }

    /**
     * Writes the document to a supplied writer.
     * 
     * @param writer
     *            The target writer
     * @throws IOException
     * @throws TransformerException
     */
    public void toWriter(Writer writer) throws IOException, TransformerException {
        transformer.transform(new DOMSource(document), new StreamResult(new BufferedWriter(writer)));
        writer.flush();
    }

    /**
     * Write the XML document to a string.
     * 
     * @return The String, null if failed
     */
    public String toString() {
        try {
            StringWriter sw = new StringWriter();
            try {
                toWriter(sw);
            } catch (Exception ex) {
                return null;
            }
            return sw.toString().trim();
        } catch (Exception ex) {
            log.log(Level.WARNING, "Failed to write XML to string", ex);
            return null;
        }
    }

    /**
     * Get the correct case for a element or attribute
     * 
     * @param name
     * @return
     */
    protected String getCorrectCase(String name) {
        if (ignoreCase)
            return name;

        if (useUpperCase)
            return name.toUpperCase();

        return name.toLowerCase();
    }

    /**
     * Configure the transformer and the builder for this class.
     * 
     * @throws XMLConfigurationException
     *             In case of an error during configuration
     */
    private void configure() throws XMLConfigurationException {
        /*
         * Create the transformer
         */
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            setTransformerProperty(OutputKeys.METHOD, "xml");
            setTransformerProperty(OutputKeys.INDENT, "yes");
            setTransformerProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        } catch (TransformerConfigurationException ex) {
            throw new XMLConfigurationException("Failed to configure the XML transformer.", ex);
        }

        this.entityResolver = new DefaultEntityResolver();
        this.errorHandler = new DefaultErrorHandler();

        /*
         * Create the document builder
         */
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setValidating(true);
            factory.setCoalescing(true);
            factory.setIgnoringElementContentWhitespace(true);

            docBuilder = factory.newDocumentBuilder();
            this.setEntityResolver(this.entityResolver);
            this.setErrorHandler(this.errorHandler);
        } catch (ParserConfigurationException ex) {
            throw new XMLConfigurationException("Failed to configure the XML parser.", ex);
        }
    }

    private XMLElement createElement(Element e) {
        if (e == null)
            return null;

        XMLElement xmle = new XMLElement(e);
        if (!ignoreCase)
            xmle.setUseUpperCase(useUpperCase);

        return xmle;
    }

}