package org.dmonix.xml.html;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.xml.transform.OutputKeys;

import org.xml.sax.SAXException;

import org.dmonix.xml.XMLConfigurationException;
import org.dmonix.xml.XMLDocument;
import org.dmonix.xml.XMLElement;

/**
 * Utility class for creating HTML-formatted documents. The output documents are based on standard XML, thus all elements will be correctly formatted. <br>
 * All XML tags and attributes will be defined in either upper or lower case depending on what has been defined by the developer (
 * <code>setUseUpperCase(boolean)</code>)
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
public class HTMLDocument extends XMLDocument {

    /** The &lt;HTML&gt; element. */
    protected XMLElement htmlNode = null;

    /** The &lt;HEAD&gt; element. */
    protected XMLElement headNode = null;

    /** The &lt;BODY&gt; element. */
    protected XMLElement bodyNode = null;

    /**
     * Creates a new HTML document. The document will contain a <code>HTML</code> element with a <code>HEAD</code> and <code>BODY</code> element.
     * 
     * @throws XMLConfigurationException
     *             In case of an error during configuration
     */
    public HTMLDocument() throws XMLConfigurationException {
        this(false);
    }

    /**
     * Creates a new HTML document The document will contain a <code>HTML</code> element with a <code>HEAD</code> and <code>BODY</code> element.
     * 
     * @param upperCase
     *            true = upper case, false = lower case
     * @since 1.1
     * @throws XMLConfigurationException
     *             In case of an error during configuration
     */
    public HTMLDocument(boolean upperCase) throws XMLConfigurationException {
        super();

        // method must be specified as XML otherwise the output cannot be parsed
        // using a XML-parser
        super.setTransformerProperty(OutputKeys.METHOD, "xml");
        super.setTransformerProperty(OutputKeys.INDENT, "yes");
        super.setTransformerProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

        super.setUseUpperCase(upperCase);

        htmlNode = super.setDocumentElement(getCorrectCase("HTML"));
        htmlNode.setUseUpperCase(upperCase);

        headNode = htmlNode.appendChildElement("HEAD");
        bodyNode = htmlNode.appendChildElement("BODY");
    }

    /**
     * Creates a HTML document using an existing HTML document.
     * 
     * @param file
     *            The HTML document
     * @throws IOException
     * @throws SAXException
     * @throws XMLConfigurationException
     *             In case of an error during configuration
     */
    public HTMLDocument(File file) throws IOException, SAXException, XMLConfigurationException {
        this();
        setDocument(file);
    }

    /**
     * Creates a HTML document using an existing HTML document.
     * 
     * @param url
     *            The HTML document
     * @throws IOException
     * @throws SAXException
     * @throws XMLConfigurationException
     *             In case of an error during configuration
     */
    public HTMLDocument(URL url) throws IOException, SAXException, XMLConfigurationException {
        this();
        setDocument(url);
    }

    public void setDocument(File file) throws IOException, SAXException {
        super.setDocument(file);
        headNode = super.getElement(document.getElementsByTagName(getCorrectCase("HEAD")));
        bodyNode = super.getElement(document.getElementsByTagName(getCorrectCase("BODY")));
    }

    public void setDocument(URL url) throws IOException, SAXException {
        super.setDocument(url);
        headNode = super.getElement(document.getElementsByTagName(getCorrectCase("HEAD")));
        bodyNode = super.getElement(document.getElementsByTagName(getCorrectCase("BODY")));
    }

    /**
     * Adds a hyper link. <br>
     * <code>&lt;A HREF="[href]"&gt;[text]&lt;/A&gt;</code>
     * 
     * @param parent
     *            The parent element
     * @param hrefAttribute
     *            The HREF attribute class
     * @param text
     *            The text to display
     * @return The new element
     */
    public XMLElement addAnchor(XMLElement parent, HTMLAttribute.HrefAttribute hrefAttribute, String text) {
        XMLElement a = parent.appendChildElement("A");
        a.setElementValue(text);
        a.setAttribute(hrefAttribute.getAttributeName(), hrefAttribute.getAttributeValue());
        return a;
    }

    /**
     * Adds a hyper link. <br>
     * <code>&lt;A HREF="[href]"&gt;[text]&lt;/A&gt;</code>
     * 
     * @param parent
     *            The parent element
     * @param href
     *            The url
     * @param text
     *            The text to display
     * @return The new element
     */
    public XMLElement addAnchor(XMLElement parent, String href, String text) {
        XMLElement a = parent.appendChildElement("A");
        a.setElementValue(text);
        a.setAttribute("HREF", href);
        return a;
    }

    /**
     * Adds a <code>&lt;BR/&gt;</code> to the document body.
     * 
     * @return The new element
     */
    public XMLElement addBR() {
        return bodyNode.appendChildElement("BR");
    }

    /**
     * Adds a <code>&lt;BR/&gt;</code> to the provided element.
     * 
     * @param parent
     *            The parent element
     * @return The new element
     */
    public XMLElement addBR(XMLElement parent) {
        return parent.appendChildElement("BR");
    }

    /**
     * Adds a <code>&lt;HR/&gt;</code> to the document body.
     * 
     * @return The new element
     */
    public XMLElement addHR() {
        return bodyNode.appendChildElement("HR");
    }

    /**
     * Adds a <code>&lt;HR/&gt;</code> to the provided element.
     * 
     * @param parent
     *            The parent element
     * @return The new element
     */
    public XMLElement addHR(XMLElement parent) {
        return parent.appendChildElement("HR");
    }

    /**
     * Adds a <code>&lt;IMG/&gt;</code> to the document body.
     * 
     * @param parent
     *            The parent element
     * @param src
     *            The URL to the image
     * @return The new element
     */
    public XMLElement addIMG(XMLElement parent, String src) {
        XMLElement img = parent.appendChildElement("IMG");
        img.setAttribute("SRC", src);
        return img;
    }

    /**
     * Adds a <code>&lt;IMG/&gt;</code> to the document body.
     * 
     * @param parent
     *            The parent element
     * @param src
     *            The URL to the image
     * @param border
     *            The of the image
     * @return The new element
     */
    public XMLElement addIMG(XMLElement parent, String src, String border) {
        XMLElement img = addIMG(parent, src);
        img.setAttribute("BORDER", border);
        return img;
    }

    /**
     * Adds a <code>&lt;P/&gt;</code> paragraph to the document body.
     * 
     * @return The new element
     */
    public XMLElement addP() {
        return bodyNode.appendChildElement("P");
    }

    /**
     * Adds a <code>&lt;P/&gt;</code> paragraph to the provided element.
     * 
     * @param parent
     *            The parent element
     * @return The new element
     */
    public XMLElement addP(XMLElement parent) {
        return parent.appendChildElement("P");
    }

    /**
     * Adds a <code>&lt;P&gt;[text]&lt;/P&gt;</code> paragraph to the provided element.
     * 
     * @param parent
     *            The parent element
     * @param text
     *            The text of the element
     * @return The new element
     */
    public XMLElement addP(XMLElement parent, String text) {
        XMLElement e = parent.appendChildElement("P");
        e.setElementValue(text);
        return e;
    }

    /**
     * Adds a <code>&lt;P CLASS="[classAttribute]"&gt;[text]&lt;/P&gt;</code> paragraph to the provided element.
     * 
     * @param parent
     *            The parent element
     * @param classAttribute
     *            The <code>CLASS</code> attribute
     * @param text
     *            The text of the element
     * @return The new element
     */
    public XMLElement addP(XMLElement parent, HTMLAttribute.ClassAttribute classAttribute, String text) {
        return addElement(parent, "P", classAttribute, text);
    }

    /**
     * Adds a <code>&lt;TD/&gt;</code> to the document body.
     * 
     * @return The new element
     */
    public XMLElement addTD() {
        return bodyNode.appendChildElement("TD");
    }

    /**
     * Adds a <code>&lt;TD/&gt;</code> to the provided element.
     * 
     * @param parent
     *            The parent element
     * @return The new element
     */
    public XMLElement addTD(XMLElement parent) {
        return parent.appendChildElement("TD");
    }

    /**
     * Adds a <code>&lt;TD CLASS="[classAttribute]"/&gt;</code> to the provided element.
     * 
     * @param parent
     *            The parent element
     * @param classAttribute
     *            the CLASS attribute
     * @return The new element
     */
    public XMLElement addTD(XMLElement parent, HTMLAttribute.ClassAttribute classAttribute) {
        return addElement(parent, "TD", classAttribute);
    }

    /**
     * Adds a <code>&lt;TR/&gt;</code> to the document body.
     * 
     * @return The new element
     */
    public XMLElement addTR() {
        return bodyNode.appendChildElement("TR");
    }

    /**
     * Adds a <code>&lt;TR/&gt;</code> to the provided element.
     * 
     * @param parent
     *            The parent element
     * @return The new element
     */
    public XMLElement addTR(XMLElement parent) {
        return parent.appendChildElement("TR");
    }

    /**
     * Adds a <code>&lt;META/&gt;</code> to the document head.
     * 
     * @return The new element
     */
    public XMLElement addMetaTag() {
        XMLElement meta = headNode.appendChildElement("META");
        return meta;
    }

    /**
     * Adds a <code>&lt;META/&gt;</code> to the document head. <br>
     * <code>
     * &lt;META name="<var>name</var>" content="<var>content</var>"&gt;
     * </code>
     * 
     * @param name
     *            The name
     * @param content
     *            The content
     * @return The new element
     */
    public XMLElement addMetaTag(String name, String content) {
        XMLElement meta = headNode.appendChildElement("META");
        meta.setAttribute("name", name);
        meta.setAttribute("content", content);
        return meta;
    }

    /**
     * Add the meta tags for no cache. <br>
     * These meta tags will force the browser to load the page for each access. <code>
     * <br>&lt;META http-equiv="Cache-Control" content="no-cache"/&gt;
     * <br>&lt;META http-equiv="Pragma" content="no-cache"/&gt;
     * <br>&lt;META http-equiv="Expires" content="0"/&gt;
     * <code>
     */
    public void addMetaTagForNoCache() {
        XMLElement meta1 = addMetaTag();
        meta1.setAttribute("HTTP-EQUIV", "CACHE-CONTROL");
        meta1.setAttribute("CONTENT", "NO-CACHE");

        XMLElement meta2 = addMetaTag();
        meta2.setAttribute("HTTP-EQUIV", "PRAGMA");
        meta2.setAttribute("CONTENT", "NO-CACHE");

        XMLElement meta3 = addMetaTag();
        meta3.setAttribute("HTTP-EQUIV", "EXPIRES");
        meta3.setAttribute("CONTENT", "0");
    }

    /**
     * Add a new element to the document body.
     * 
     * @param name
     *            The name of the element
     * @return The new element
     */
    public XMLElement addElementToBody(String name) {
        return bodyNode.appendChildElement(name);
    }

    /**
     * Add a new element to the document body.
     * 
     * @param name
     *            The name of the element
     * @param classAttribute
     *            The CLASS attribute
     * @return The new element
     */
    public XMLElement addElementToBody(String name, HTMLAttribute.ClassAttribute classAttribute) {
        XMLElement e = bodyNode.appendChildElement(name);
        setClass(e, classAttribute);
        return e;
    }

    /**
     * Add a new element.
     * 
     * @param parent
     *            The parent element
     * @param name
     *            The name of the element
     * @param text
     *            The text content
     * @return The new element
     */
    public XMLElement addElement(XMLElement parent, String name, String text) {
        XMLElement e = parent.appendChildElement(name);
        e.setElementValue(text);
        return e;
    }

    /**
     * Add a new element.
     * 
     * @param parent
     *            The parent element
     * @param name
     *            The name of the element
     * @param classAttribute
     *            The CLASS attribute
     * @return The new element
     */
    public XMLElement addElement(XMLElement parent, String name, HTMLAttribute.ClassAttribute classAttribute) {
        XMLElement e = parent.appendChildElement(name);
        setClass(e, classAttribute);
        return e;
    }

    /**
     * Add a new element.
     * 
     * @param parent
     *            The parent element
     * @param name
     *            The name of the element
     * @param classAttribute
     *            The CLASS attribute
     * @param text
     *            The text content
     * @return The new element
     */
    public XMLElement addElement(XMLElement parent, String name, HTMLAttribute.ClassAttribute classAttribute, String text) {
        XMLElement e = parent.appendChildElement(name);
        e.setElementValue(text);
        setClass(e, classAttribute);
        return e;
    }

    /**
     * Adds a <code>&lt;TABLE/&gt;</code> to the provided element.
     * 
     * @param parent
     *            The parent element
     * @return The new element
     */
    public XMLElement addTable(XMLElement parent) {
        return parent.appendChildElement("TABLE");
    }

    /**
     * Add a table element. <br>
     * <code>
     * &lt;TABLE CLASS="[classAttribute]"/&gt;
     * </code>
     * 
     * @param parent
     *            The parent element
     * @param classAttribute
     *            The CLASS attribute
     * @return The new element
     */
    public XMLElement addTable(XMLElement parent, HTMLAttribute.ClassAttribute classAttribute) {
        XMLElement table = parent.appendChildElement("TABLE");
        setClass(table, classAttribute);
        return table;
    }

    /**
     * Create a new element. <br>
     * <code>
     * <[name] CLASS="[classAttribute]"/>
     * </code>
     * 
     * @param name
     *            The name of the element
     * @param classAttribute
     *            The class attribute
     * @return The new element
     */
    public XMLElement createElement(String name, HTMLAttribute.ClassAttribute classAttribute) {
        XMLElement e = createElement(getCorrectCase(name));
        setClass(e, classAttribute);
        return e;
    }

    /**
     * Get the &lt;BODY&gt; element;
     * 
     * @return The element
     */
    public XMLElement getBodyElement() {
        return this.bodyNode;
    }

    /**
     * Get the &lt;HEAD&gt; element;
     * 
     * @return The element
     */
    public XMLElement getHeadElement() {
        return this.headNode;
    }

    /**
     * Set the class attribute for the element. <br>
     * <code>
     * &lt;FOO CLASS="[classAttribute]"/&gt;
     * </code>
     * 
     * @param parent
     *            The element
     * @param classAttribute
     *            The class attribute
     */
    public void setClass(XMLElement parent, HTMLAttribute.ClassAttribute classAttribute) {
        parent.setAttribute(classAttribute.getAttributeName(), classAttribute.getAttributeValue());
    }
}
