package nom.bisoncao.bcviewfinder.utils;

import java.util.List;
import java.util.Map;

/**
 * @author Bison Cao
 * @created 2015-12-5 18:14
 */
public class NullUtil {
    /**
     * 判断 字符串 是否为空（空对象或长度为0）
     * @param string
     * @return
     */
    public static boolean isNullString(CharSequence string) {
        return string == null || string.length() == 0;
    }

    public static boolean isNotNullString(CharSequence string) {
        return !isNullString(string);
    }

    /**
     * 判断 数组 是否为空（空对象或长度为0）
     * @param arr
     * @return
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
     * 判断list是否为空（空对象或大小为0）
     *
     * @param list
     * @return 为空对象或者大小为0时返回true，否则返回false
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
     * 判断map是否为空（空对象或大小为0）
     * @return 为空对象或者大小为0时返回true，否则返回false
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
