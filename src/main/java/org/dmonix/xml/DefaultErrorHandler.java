package org.dmonix.xml;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * This default implemententation of the ErrorHandler does nothing. All errors and warnings will simply be ignored by this class.
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
public class DefaultErrorHandler implements ErrorHandler {

    public void warning(SAXParseException exception) throws SAXException {
    }

    public void error(SAXParseException exception) throws SAXException {
    }

    public void fatalError(SAXParseException exception) throws SAXException {
    }

}
