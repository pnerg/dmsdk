package org.dmonix.xml.html;

import org.dmonix.AbstractTestCase;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class TestXHTML extends AbstractTestCase {

    public void testParse() throws Exception {
        // XHTMLDocument doc = new XHTMLDocument();
        // ErrorHandlerImpl handler = new ErrorHandlerImpl();
        // doc.setErrorHandler(handler);
        // doc.setDocument(new
        // File("test/test/org/dmonix/xml/html/index.html"));
        //
        // if(handler.errors) {
        // logger.log(Level.WARNING, "\n"+handler.toString());
        // System.out.println(handler);
        // fail("The document is not valid.");
        // }
        // assertFalse("" , handler.errors);
    }

    private class ErrorHandlerImpl implements ErrorHandler {

        private boolean errors = false;

        private StringBuffer sb = new StringBuffer();

        public void warning(SAXParseException exception) throws SAXException {
            sb.append("Warning - ");
            appendError(exception);
        }

        public void error(SAXParseException exception) throws SAXException {
            sb.append("Error - ");
            appendError(exception);
            errors = true;
        }

        public void fatalError(SAXParseException exception) throws SAXException {
            sb.append("Fatal - ");
            appendError(exception);
            errors = true;
        }

        public String toString() {
            return sb.toString();
        }

        private void appendError(SAXParseException ex) {
            sb.append("line [");
            sb.append(ex.getLineNumber());
            sb.append("] column [");
            sb.append(ex.getColumnNumber());
            sb.append("] : ");
            sb.append(ex.getMessage());
            sb.append("\n");
        }

        public boolean hadErrors() {
            return this.errors;
        }
    }
}
