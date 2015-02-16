package org.dmonix.util;

import java.util.Properties;

/**
 * Util class for loading various resources.
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

public abstract class Resources {

    private static String buildNumber;

    static {
        try {
            Properties p = new Properties();
            p.load(Resources.class.getClassLoader().getResourceAsStream("build.number"));
            buildNumber = p.getProperty("build.number", "x");
        } catch (Exception ex) {
            buildNumber = "x";
        }
    }

    /**
     * Get the buildnumber.
     * 
     * @return
     */
    public static String getBuildNumber() {
        return buildNumber;
    }
}