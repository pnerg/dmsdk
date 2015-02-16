package org.dmonix.xml.html;

import javax.xml.transform.OutputKeys;

/**
 * Utility class for creating XHTML-formatted documents. All tags and attributes will be output using lower case as defined by W3C.<br>
 * All documents will have the following DOCTYPE:<br>
 * <code>&lt;!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"&gt;</code>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @since 1.1
 */
public class XHTMLDocument extends HTMLDocument {

    public XHTMLDocument() {
        super(false);
        super.setTransformerProperty(OutputKeys.DOCTYPE_PUBLIC, "-//W3C//DTD XHTML 1.0 Transitional//EN");
        super.setTransformerProperty(OutputKeys.DOCTYPE_SYSTEM, "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd");
        super.setTransformerProperty(OutputKeys.METHOD, "xml");
        super.setTransformerProperty(OutputKeys.INDENT, "yes");
        super.setTransformerProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        super.setEntityResolver(new XHTMLDTDEntityResolverImpl());
    }

    /**
     * This method is not supported since W3C states that XHTML documents are always in lower case. The method will always throw
     * <code>UnsupportedOperationException</code>.
     */
    public void setUseUpperCase(boolean upperCase) {
        throw new UnsupportedOperationException("XHTML documents are always in lower case.");
    }
}
