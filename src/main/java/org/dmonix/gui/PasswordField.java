package org.dmonix.gui;

import java.util.regex.Pattern;

import java.awt.event.KeyEvent;

/**
 * A password field that can be validated. <br>
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
public class PasswordField extends TextField {
    private static final long serialVersionUID = 7526472295622776147L;

    private StringBuffer password = new StringBuffer();
    private StringBuffer displayText = new StringBuffer();

    /**
     * Constructs a default <code>PasswodField</code> with default pattern.
     */
    public PasswordField() {
        super((Pattern) null, (Object) null);
    }

    /**
     * Constructs a <code>PasswodField</code> using the specified pattern.
     * 
     * @param pattern
     *            the pattern to use when validating
     */
    public PasswordField(String pattern) {
        super(pattern);
    }

    /**
     * Constructs a <code>PasswodField</code> using the specified pattern.
     * 
     * @param pattern
     *            the pattern to use when validating
     */
    public PasswordField(Pattern pattern) {
        super(pattern);
    }

    /**
     * Get the password text.
     * 
     * @return The password
     */
    public String getPassword() {
        return this.password.toString();
    }

    /**
     * Validates the entire input sequence against the pattern.
     * 
     * @throws NotValidException
     *             if the field doesn't match the pattern.
     */
    public void validateText() throws NotValidException {
        if (!this.isVisible() || !this.isEnabled())
            return;

        super.validateText(this.password.toString());
    }

    protected void this_keyTyped(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ESCAPE || e.getKeyChar() == KeyEvent.VK_ENTER)
            return;

        e.consume();
        if (e.getKeyChar() != KeyEvent.VK_BACK_SPACE && e.getKeyChar() != KeyEvent.VK_DELETE) {
            int selectedCharacters = (this.getSelectedText() != null) ? this.getSelectedText().length() : 0;
            int length = this.password.length() + 1 - selectedCharacters;

            if (this.maxLength != -1 && length > this.maxLength) {
                return;
            }

            password.append(e.getKeyChar());
            displayText.append("*");
        } else if (password.length() > 0) {
            password.deleteCharAt(password.length() - 1);
            displayText.deleteCharAt(displayText.length() - 1);
        }

        super.setText(displayText.toString());
        this.repaint();
    }
}