package org.dmonix.xml;

import java.io.IOException;
import java.io.StringReader;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * A dummy EntityResolver implementation. The class is intended to be used when the XML document uses either a DTD or schema that is not available.<br>
 * This class will igonere any request to a DTD or a schema.
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
public class DefaultEntityResolver implements EntityResolver {

    /**
     * The method will always return an empty StringReader.
     */
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        return new InputSource(new StringReader(""));
    }

}
