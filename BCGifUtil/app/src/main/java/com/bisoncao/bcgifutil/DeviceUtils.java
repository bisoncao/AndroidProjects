package com.bisoncao.bcgifutil;

import android.os.Build;

public class DeviceUtils {
	public static final int API_LEVEL = Build.VERSION.SDK_INT;
	
	private static final int LOLLIPOP = 21;

    public static boolean hasLevel(int level) {
        return API_LEVEL >= level;
    }
	
	/**
	 * Android版本是否是 3.0（代号 HONEYCOMB ）以上
	 * @return
	 */
	public static boolean hasHoneycomb() {
		return hasLevel(Build.VERSION_CODES.HONEYCOMB);
	}
	
	/**
	 * Android版本是否是 5.0（代号 LOLLIPOP ）以上
	 * @return
	 */
	public static boolean hasLollipop() {
		return hasLevel(LOLLIPOP);
	}



}
