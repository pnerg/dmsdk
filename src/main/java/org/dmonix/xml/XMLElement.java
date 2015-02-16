package org.dmonix.xml;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLElement {

    private Element element;

    private Document ownerDocument;

    private boolean useUpperCase = false;

    private boolean ignoreCase = true;

    XMLElement(Element e) {
        this.element = e;
        this.ownerDocument = e.getOwnerDocument();
    }

    XMLElement(Element e, boolean userUpperCase) {
        this(e);
        this.useUpperCase = userUpperCase;
        this.ignoreCase = false;
    }

    Element getElement() {
        return this.element;
    }

    public void appendChildElement(XMLElement xmlElement) {
        element.appendChild(xmlElement.element);
    }

    /**
     * Appends a child element.
     * 
     * @param name
     *            The name of the new element
     * @return The new element
     */
    public XMLElement appendChildElement(String name) {
        Element e = ownerDocument.createElement(getCorrectCase(name));
        element.appendChild(e);
        return createElement(e);
    }

    /**
     * Appends a child element.
     * 
     * @param name
     *            The name of the new element
     * @param value
     *            The value of the new element
     * @return The new element
     */
    public XMLElement appendChildElement(String name, String value) {
        Element e = ownerDocument.createElement(getCorrectCase(name));
        e.appendChild(ownerDocument.createTextNode((value == null) ? "" : value));
        element.appendChild(e);
        return createElement(e);
    }

    /**
     * Compare this XMLElement to another XMLElement.
     * 
     * @param xmlDocument
     * @return
     */
    public boolean compare(XMLElement xmlElement) {
        return XMLUtil.compare(this.element, xmlElement.element);
    }

    /**
     * Retrieves an attribute value by name.
     * 
     * @param name
     *            The name of the attribute to retrieve.
     * @return The <code>Attr</code> value as a string, or the empty string if that attribute does not have a specified or default value.
     */
    public String getAttribute(String name) {
        return this.element.getAttribute(name);
    }

    /**
     * Returns a list of all child elements.
     * 
     * @return The list, empty if no children
     */
    public XMLElementList getChildElements() {
        XMLElementList xmllist = new XMLElementList();
        NodeList childList = this.element.getChildNodes();
        Node childNode;
        for (int i = 0; i < childList.getLength(); i++) {
            childNode = childList.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                xmllist.add(createElement((Element) childNode));
            }
        }

        return xmllist;
    }

    /**
     * Returns the first occurence of a child element with a specific name.
     * 
     * @param name
     *            the name of the child element to look for
     * @return the first occurenc of the the name
     */
    public XMLElement getChildElementByTagName(String name) {
        NodeList childList = this.element.getChildNodes();
        for (int i = 0; i < childList.getLength(); i++) {
            Node childNode = childList.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE && childNode.getNodeName().equals(name)) {
                return createElement((Element) childNode);
            }
        }

        return null;
    }

    /**
     * Returns the name of the element.
     * 
     * @return The name
     */
    public String getElementName() {
        return this.element.getTagName();
    }

    /**
     * Get the value of the element. The method expects the element to have a single <code>TEXT</code> node as child, if so the value of the text node is
     * returned otherwise an empty String is returned.
     * 
     * @return The value, an empty string if the element has no value
     */
    public String getElementValue() {
        return this.getElementValue("");
    }

    /**
     * Get the value of the element. The method expects the element to have a single <code>TEXT</code> node as child, if so the value of the text node is
     * returned otherwise the supplied default value is returned.
     * 
     * @param defaultValue
     *            The value to return in case no value was found
     * @return The value
     */
    public String getElementValue(String defaultValue) {
        if (element == null || !element.hasChildNodes())
            return defaultValue;

        StringBuilder sb = new StringBuilder();
        for (Node node : getChildrenOfType(Node.TEXT_NODE)) {
            sb.append(node.getNodeValue());
        }

        // String value = element.getFirstChild().getNodeValue();
        // if (value != null)
        // value = value.replace('"', ' ').trim();

        return sb.toString();
    }

    /**
     * Returns a XMLElementList of all the Elements with a given tag name in the order in which they are encountered in a preorder traversal of the Document
     * tree.
     * 
     * @param tagname
     *            The name of the tag
     * @return The list
     * @see org.w3c.dom.Element#getElementsByTagName
     */
    public XMLElementList getElementsByTagName(String tagname) {
        XMLElementList list = new XMLElementList();
        NodeList nodeList = element.getElementsByTagName(tagname);
        for (int i = 0; i < nodeList.getLength(); i++) {
            list.add(createElement((Element) nodeList.item(i)));
        }

        return list;
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
        element.insertBefore(newChild.getElement(), reference.getElement());
    }

    /**
     * Removes the provided child XMLElement from this XMLElement. The method expects the argument to be a direct child to this XMLElement.
     * 
     * @param xmlelement
     *            The XMLElement to remove
     */
    public void removeElement(XMLElement xmlelement) {
        if (element == null)
            throw new IllegalArgumentException("Null argument is not allowed.");

        element.removeChild(xmlelement.element);
    }

    /**
     * Removes all child elements to the provided element.
     * 
     * @param element
     *            The element
     */
    public void removeAllChildElements() {
        this.removeAllChildrenOfType(Node.ELEMENT_NODE);
    }

    /**
     * Set an attribute to the element. The attribute will automatically be defined in either upper or lower case depending on what was set with
     * <code>setUseUpperCase(boolean)</code>.
     * 
     * @param e
     *            The element
     * @param name
     *            The name of the attribute
     * @param value
     *            The value of the attribute
     */
    public void setAttribute(String name, String value) {
        element.setAttribute(getCorrectCase(name), value);
    }

    /**
     * Set the text content for a provided element. <code>&lt;FOO&gt;[text]&lt;/FOO&gt;</code><br>
     * Any existing text nodes will be removed prior to adding addin the new.
     * 
     * @param text
     *            The text to set
     */
    public void setElementValue(String text) {
        this.removeAllChildrenOfType(Node.TEXT_NODE);
        this.element.appendChild(this.ownerDocument.createTextNode(text));
    }

    /**
     * Sets if all the HTML tags are to be in upper case or not. This determines if the tags written to the document will be in upper or lower case. <br>
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
     * Returns a String representation of this object.
     */
    public String toString() {

        StringBuffer sb = new StringBuffer();
        sb.append("<");
        sb.append(this.element.getTagName());

        NamedNodeMap attributes = this.element.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {

        }

        String elementValue = this.getElementValue();
        if (elementValue.length() > 0) {
            sb.append(">");
            sb.append(elementValue);
            sb.append("</");
            sb.append(this.element.getTagName());
            sb.append(">");
        } else {
            sb.append("/>");
        }

        return sb.toString();
    }

    /**
     * Create a new XMLElement using the provided Element.
     * 
     * @param e
     * @return The XMLElement, null if the input element is null
     */
    private XMLElement createElement(Element e) {
        if (e == null)
            return null;

        XMLElement xmle = new XMLElement(e);
        if (!this.ignoreCase)
            xmle.setUseUpperCase(this.useUpperCase);

        return xmle;
    }

    /**
     * Get the correct case for a element or attribute
     * 
     * @param name
     * @return
     */
    private String getCorrectCase(String name) {
        if (ignoreCase)
            return name;

        if (useUpperCase)
            return name.toUpperCase();

        return name.toLowerCase();
    }

    /**
     * Removes all child nodes of the specific type.
     * 
     * @param type
     *            The type
     */
    private void removeAllChildrenOfType(short type) {
        NodeList list = this.element.getChildNodes();

        /*
         * Copy the elements to a temporary list. This is due to that deleting childs whilst iterating on the node list can result in nodes not beeing deleted.
         */
        List<Node> tmpList = new ArrayList<Node>(list.getLength());
        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeType() != type)
                continue;

            tmpList.add(list.item(i));
        }

        for (Node node : tmpList) {
            element.removeChild(node);
        }
        tmpList.clear();
    }

    /**
     * Get all the children of the specified type;
     * 
     * @param type
     * @return
     */
    private List<Node> getChildrenOfType(short type) {
        NodeList list = this.element.getChildNodes();

        /*
         * Copy the elements to a temporary list. This is due to that deleting childs whilst iterating on the node list can result in nodes not beeing deleted.
         */
        List<Node> tmpList = new ArrayList<Node>(list.getLength());
        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeType() != type)
                continue;

            tmpList.add(list.item(i));
        }

        return tmpList;
    }
}
