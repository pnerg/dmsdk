package org.dmonix.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextField;

import org.dmonix.util.RegExpUtil;

/**
 * A text field that can be validated against a regular expression. <br>
 * Useful for creating textfields that may only contain specific types of characters, e.g. alpha-numeric or only numeric characters. <br>
 * If the value of the textfield does not match the pattern a <code>NotValidException</code> is thrown.
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @since 1.0
 */
public class TextField extends JTextField {

    private static final long serialVersionUID = 5101108100494648L;

    /**
     * A predefined pattern that only accepts numbers.
     * 
     * @deprecated Use RegExpUtil.ONLY_NUMBERS_PATTERN/ public static final Pattern NUMBER_PATTERN = Pattern.compile(RegExpUtil.ONLY_NUMBERS_PATTERN);
     * 
     *             /** A predefined pattern to check that a string doesn't contain any XML invalid characters &amp; , &lt; , &gt; , &apos; or &quot;
     * @deprecated Use RegExpUtil.XML_INVALID_CHARS_PATTERN
     */
    public static final Pattern XML_INVALID_CHARS = Pattern.compile(RegExpUtil.XML_INVALID_CHARS_PATTERN);

    protected static final Color MANDATORY_COLOR = new Color(255, 255, 204);

    protected List<ErrorPattern> errorPatterns = new LinkedList<ErrorPattern>();

    protected Pattern pattern = Pattern.compile(".*");

    protected Object value;

    protected Object initialValue;

    /** specifies if this text field is mandatory */
    protected boolean mandatory = false;

    /** the width for the text field */
    protected int noOfChars = -1;

    /** max chars for text fields */
    protected int maxLength = -1;

    protected int numberRangeMin = Integer.MAX_VALUE;

    protected int numberRangeMax = Integer.MAX_VALUE;

    protected String errorMessage;

    /**
     * Constructs a default <code>TextField</code>.
     */
    public TextField() {
        this((Pattern) null, (Object) null);
    }

    /**
     * Constructs a <code>TextField</code> using the specified pattern.
     * 
     * @param pattern
     *            the pattern
     */
    public TextField(String pattern) {
        this(pattern, (Object) null);
    }

    /**
     * Constructs a <code>TextField</code> using the specified pattern.
     * 
     * @param pattern
     *            the <code>java.util.regex.Pattern</code> to use with this text field
     */
    public TextField(Pattern pattern) {
        this(pattern, (Object) null);
    }

    /**
     * Constructs a <code>TextField</code> using the specified pattern and value.
     * 
     * @param pattern
     *            the pattern to use with this text field
     * @param value
     *            the value of the text field
     */
    public TextField(String pattern, Object value) {
        this(Pattern.compile(pattern), value);
    }

    /**
     * Constructs a <code>TextField</code> using a specified pattern and value.
     * 
     * @param pattern
     *            the <code>java.util.regex.Pattern</code> to use with this text field
     * @param value
     *            the value of the text field
     */
    public TextField(Pattern pattern, Object value) {
        if (pattern == null)
            pattern = Pattern.compile(".*");
        this.pattern = pattern;
        this.value = value;

        this.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                this_keyTyped(e);
            }
        });
    }

    /**
     * Set the pattern to use with this text field.
     * 
     * @param pattern
     *            the pattern to use with this text field
     * @see <a href="http://java.sun.com/j2se/1.5.0/docs/api/java/util/regex/Pattern.html">java.util.regex.Pattern</a>
     */
    public void setPattern(String pattern) {
        this.setPattern(Pattern.compile(pattern));
    }

    /**
     * Set the pattern to use with this text field.
     * 
     * @param pattern
     *            the <code>java.util.regex.Pattern</code> to use with this text field
     * @see <a href="http://java.sun.com/j2se/1.5.0/docs/api/java/util/regex/Pattern.html">java.util.regex.Pattern</a>
     */
    public void setPattern(Pattern pattern) {
        this.setPattern(pattern, null);
    }

    /**
     * Set the pattern to use with this field and a specific error message.
     * 
     * @param pattern
     *            the patter to use with this text field
     * @param errorMessage
     *            the error message
     */
    public void setPattern(String pattern, String errorMessage) {
        this.setPattern(Pattern.compile(pattern), errorMessage);
    }

    /**
     * Set the pattern to use with this field and a specific error message.
     * 
     * @param pattern
     *            the <code>java.util.regex.Pattern</code> to use with this text field
     * @param errorMessage
     *            the error message
     */
    public void setPattern(Pattern pattern, String errorMessage) {
        this.pattern = pattern;
        this.errorMessage = errorMessage;
    }

    /**
     * Set the number range for this field.
     * 
     * @param min
     *            the minimum characters that can be used
     * @param max
     *            the maximum characters that can be used
     * @throws IllegalArgumentException
     */
    public void setNumberRange(int min, int max) throws IllegalArgumentException {
        if (max < min)
            throw new IllegalArgumentException("Min value must be larger than max");
        this.numberRangeMin = min;
        this.numberRangeMax = max;
    }

    /**
     * Validates the entire input sequence against the pattern. <br>
     * The method will not perform any validation if the texfield is not visible, enabled and editable
     * 
     * @throws NotValidException
     *             If the text doesn't match the pattern
     */
    public void validateText() throws NotValidException {
        if (!this.isVisible() || !this.isEnabled() || !this.isEditable())
            return;

        validateText(this.getText());
    }

    /**
     * Validate the input text towards the pattern for this textfield.
     * 
     * @param text
     *            The text to validate
     * @throws NotValidException
     *             If the text doesn't match the pattern
     */
    public void validateText(String text) throws NotValidException {
        Matcher matcher = pattern.matcher(text);
        boolean matches = matcher.matches();

        if (errorPatterns.size() > 0) {
            for (ErrorPattern errorPattern : errorPatterns) {
                if (!Pattern.matches(errorPattern.pattern, text)) {
                    error();
                    throw new NotValidException(errorPattern.message);
                }
            }
        }

        if (!matches && errorMessage == null) {
            error();
            throw new NotValidException("\"" + text + "\" does not match the pattern \"" + pattern.pattern() + "\"");
        } else if (!matches && errorMessage != null) {
            error();
            throw new NotValidException(errorMessage);
        }

        if (numberRangeMin != Integer.MAX_VALUE && numberRangeMax != Integer.MAX_VALUE) {
            try {
                int intValue = Integer.parseInt(text);
                if (intValue < numberRangeMin || intValue > numberRangeMax) {
                    this.error();
                    throw new NotValidException("The value [" + text + "] is not between " + numberRangeMin + " and " + numberRangeMax + ".");
                }
            } catch (NumberFormatException ex) {
                this.error();
                throw new NotValidException("The value [" + text + "] is not between " + numberRangeMin + " and " + numberRangeMax + ".");
            }
        }

    }

    protected void error() {
        this.requestFocus();
        this.setSelectionStart(0);
        this.setSelectionEnd(this.getText().length());
    }

    /**
     * Add an error pattern to use when validating this field and a message to use if it don't match.
     * 
     * @param errorPattern
     *            the error pattern
     * @param errorMessage
     *            the error message
     */
    public void addErrorPattern(Pattern errorPattern, String errorMessage) {
        this.addErrorPattern(errorPattern.pattern(), errorMessage);
    }

    /**
     * Add an error pattern to use when validating this field and a message to use if it don't match.
     * 
     * @param errorPattern
     *            the error pattern
     * @param errorMessage
     *            the error message
     */
    public void addErrorPattern(String errorPattern, String errorMessage) {
        errorPatterns.add(new ErrorPattern(errorPattern, errorMessage));
    }

    /**
     * Set to true if this field is mandatory.
     * 
     * @param mandatory
     *            true if mandatory
     */
    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
        if (mandatory) {
            setBackground(MANDATORY_COLOR);
        } else {
            setBackground(Color.WHITE);
        }
    }

    /**
     * Set the number of chars that should be visible in the text field.
     * 
     * @param noOfChars
     *            the number of chars
     */
    public void setWidth(int noOfChars) {
        this.noOfChars = noOfChars;
        /** @todo Should probably reset the size for this component */
        if (noOfChars == -1)
            return;

        FontMetrics fontMetrics = this.getFontMetrics(getFont());
        int charWidth = fontMetrics.charWidth(' ');
        for (char c = 'A'; c < 'Z'; c++) {
            charWidth = Math.max(charWidth, fontMetrics.charWidth(c));
        }

        this.setMaximumSize(new Dimension(charWidth * noOfChars, (int) this.getPreferredSize().getHeight()));
        this.setMinimumSize(new Dimension(charWidth * noOfChars, (int) this.getPreferredSize().getHeight()));
        this.setPreferredSize(new Dimension(charWidth * noOfChars, (int) this.getPreferredSize().getHeight()));
    }

    /**
     * Set the maximum numbers of characters that can be written in this field.
     * 
     * @param maxLength
     *            the maximun number of characters
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    protected void this_keyTyped(KeyEvent e) {
        if (!(e.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
            int selectedCharacters = (this.getSelectedText() != null) ? this.getSelectedText().length() : 0;
            int length = "this.getText() + e.getKeyChar()".length() - selectedCharacters;

            if (this.maxLength != -1 && length > this.maxLength)
                e.consume();
        }
    }

    /**
     * A value object for error patterns.
     */
    private static class ErrorPattern {
        String pattern = "";
        String message = "";

        private ErrorPattern(String pattern, String message) {
            this.pattern = pattern;
            this.message = message;
        }
    }
}