package org.dmonix.io;

import java.io.*;

/**
 * This is a singleton class enabling the logging of stacktraces to file. <br>
 * The default file used is <code>[user.home]/dmonix/stacktrace.log</code><br>
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
public class StackTraceLogger {

    private static PrintStream printStream;

    private static StackTraceLogger stackTraceLogger = null;

    private StackTraceLogger(File file) throws IOException {
        this.setLogFile(file);
    }

    /**
     * Get an instance of this logger.
     * 
     * @return
     * @throws IOException
     */
    public static StackTraceLogger getInstance() {
        if (stackTraceLogger == null)
            throw new IllegalStateException("Must invoke newInstance(File) first.");

        return stackTraceLogger;
    }

    /**
     * Get an instance of this logger.
     * 
     * @return
     * @throws IOException
     * @throws IOException
     */
    public static StackTraceLogger newInstance(File file) throws IOException {
        stackTraceLogger = new StackTraceLogger(file);
        return stackTraceLogger;
    }

    /**
     * Get the output stream for this logger.
     * 
     * @return
     */
    public PrintStream getOutputStream() {
        if (printStream == null) {
            throw new IllegalStateException("Must set a log file [setLogFile()] before use");
        }

        return printStream;
    }

    /**
     * Destroys this logger and releases it's resources.
     */
    public void destroy() {
        try {
            printStream.close();
        } catch (Exception ex) {
        }
        stackTraceLogger = null;
    }

    /**
     * Sets the output file for this logger.
     * 
     * @param f
     */
    public void setLogFile(File f) throws IOException {
        if (!f.exists()) {
            if (f.getParentFile() != null)
                f.getParentFile().mkdirs();

            f.createNewFile();
        }

        printStream = new PrintStream(new FileOutputStream(f, true));
    }

}