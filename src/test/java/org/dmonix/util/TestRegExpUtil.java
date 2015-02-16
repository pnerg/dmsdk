package org.dmonix.util;

import org.dmonix.AbstractTestCase;

public class TestRegExpUtil extends AbstractTestCase {

    public void testValidateRegEx() {
        assertRegExp(RegExpUtil.ONLY_CHARACTERS_PATTERN, "apeshit", true);
        assertRegExp(RegExpUtil.ONLY_CHARACTERS_PATTERN, "number 666 of the beast", false);

        assertRegExp(RegExpUtil.ONLY_NUMBERS_PATTERN, "0123456789", true);
        assertRegExp(RegExpUtil.ONLY_NUMBERS_PATTERN, "daoi321312dasd", false);

        assertRegExp(RegExpUtil.VALID_IP_PATTERN, "127.0.0.1", true);
        assertRegExp(RegExpUtil.VALID_IP_PATTERN, "192.168.168.121", true);
        assertRegExp(RegExpUtil.VALID_IP_PATTERN, "192.168.168.121.1", false);
        assertRegExp(RegExpUtil.VALID_IP_PATTERN, "192.168.168", false);

        assertRegExp(RegExpUtil.XML_INVALID_CHARS_PATTERN, "apeshit", true);
        assertRegExp(RegExpUtil.XML_INVALID_CHARS_PATTERN, "ape>shit", false);
        assertRegExp(RegExpUtil.XML_INVALID_CHARS_PATTERN, "ape<shit", false);
        assertRegExp(RegExpUtil.XML_INVALID_CHARS_PATTERN, "ape&shit", false);
        assertRegExp(RegExpUtil.XML_INVALID_CHARS_PATTERN, "ape\"shit", false);
        assertRegExp(RegExpUtil.XML_INVALID_CHARS_PATTERN, "ape'shit", false);
    }

    private void assertRegExp(String pattern, String text, boolean expected) {
        assertEquals("Error in matching [" + pattern + "] to [" + text + "]", expected, RegExpUtil.validateRegEx(pattern, text));
    }
}
