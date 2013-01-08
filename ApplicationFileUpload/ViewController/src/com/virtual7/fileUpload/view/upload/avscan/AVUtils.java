package com.virtual7.fileUpload.view.upload.avscan;

public final class AVUtils {

	public static boolean isValidString(String s) {
		return ((s != null) && (!("".equals(s.trim()))));
	}

	public static Integer parseInteger(String val) {
		Integer i = null;
		if (val != null) {
			try {
				i = new Integer(Integer.parseInt(val));
			} catch (Exception e) {

			}
		}
		return i;
	}
}