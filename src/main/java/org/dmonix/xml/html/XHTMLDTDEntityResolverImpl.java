package org.dmonix.xml.html;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * This entity resolver is specialized for resolving XHTML DTD's. In order to validate an XHTML document the XML parser will need access to the appropriate XML
 * DTD's.<br>
 * Instead of fetching the XML DTD's from their original location at <a href="http://www.w3.org">http://www.w3.org</a>, this resolver will use the same XML
 * DTD's pre-bundled with the DMSDK.<br>
 * The class can resolve the following public/system id's:
 * 
 * <pre>
 * <code>
 * &lt;!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd"&gt;
 * &lt;!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"&gt;
 * &lt;!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"&gt;
 * </code>
 * </pre>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @since 2.0
 */
public class XHTMLDTDEntityResolverImpl implements EntityResolver {

    /** The logger instance for this class */
    private static final Logger logger = Logger.getLogger(XHTMLDTDEntityResolverImpl.class.getName());

    private static final String PATH = "/org/dmonix/xml/html/dtd";

    /**
     * Returns a stream to a locally cached version of the resource. Instead of fetching the resource from a remote site the method will find and return same
     * file that is locally stored in the distribution of the DMSDK.
     * 
     * @return The stream to the local resource
     */
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        String dtdFile = systemId.substring(systemId.lastIndexOf("/"));
        if (logger.isLoggable(Level.FINER))
            logger.log(Level.FINER, "Resolving entity : " + PATH + dtdFile);

        return new InputSource(getClass().getResourceAsStream(PATH + dtdFile));
    }

}
