package com.bisoncao.bccommonutil;

import java.util.List;
import java.util.Map;

/**
 * @created 6:14 PM 12/05/2015
 * @author Bison Cao
 */
public class BCNullUtil {
    /**
     * Judge a CharSequence if it is null or empty
     */
    public static boolean isNullString(CharSequence string) {
        return string == null || string.length() == 0;
    }

    /**
     * Judge a CharSequence if it is not null or empty
     */
    public static boolean isNotNullString(CharSequence string) {
        return !isNullString(string);
    }

    /**
     * Judge an array if it is null or empty
     */
    public static boolean isNullArr(Object[] arr) {
        if (arr == null || arr.length == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotNullArr(Object[] arr) {
        return !isNullArr(arr);

    }

    /**
     * Judge a list if it is null or empty
     */
    public static boolean isNullList(List<?> list) {
        if (list == null || list.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotNullList(List<?> list) {
        return !isNullList(list);
    }

    /**
     * Judge a map if it is null or empty
     */
    public static boolean isNullMap(Map<?, ?> map) {
        if (map == null || map.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotNullMap(Map<?, ?> map) {
        return !isNullMap(map);
    }

}
