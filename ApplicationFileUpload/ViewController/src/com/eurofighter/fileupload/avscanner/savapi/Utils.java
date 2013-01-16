package com.eurofighter.fileupload.avscanner.savapi;

/**
 * Utility class for SAVAPI scanner.
 */
class Utils {
    private Utils() {
        super();
    }

    public static boolean isValidString(String s) {
        return ((s != null) && (!("".equals(s.trim()))));
    }

    public static Integer parseInteger(String val) {
        Integer i = null;
        if (val != null) {
            try {
                i = new Integer(Integer.parseInt(val));
            } catch (Exception e) {
                i = null;
            }
        }
        return i;
    }
}
