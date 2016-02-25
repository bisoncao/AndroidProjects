package com.bisoncao.bcviewfinder.utils;

import java.util.List;

/**
 * Debug class for log output
 *
 * @author Bison Cao
 * @created 1:55 02/26/2016
 */
public class BCDebug extends BCDebugParent {

    public static final boolean IS_DEBUG = BCConst.BC_DEBUG;

    private static class BCDebugFactory {
        private static BCDebug instance = new BCDebug(IS_DEBUG);
    }

    private BCDebug(boolean isDebug) {
        super(isDebug);
    }

    public static BCDebug getInstance() {
        return BCDebugFactory.instance;
    }

    public static void d(String tag, String msg) {
        getInstance().d2(tag, msg);
    }

    public static void i(String tag, String msg) {
        getInstance().i2(tag, msg);
    }

    public static void e(String tag, String msg) {
        getInstance().e2(tag, msg);
    }

    public static void w(String tag, String msg) {
        getInstance().w2(tag, msg);
    }

    public static void printStackTrace(Exception e) {
        getInstance().printStackTrace2(e);
    }

    public static void printStackTrace(String tag, String msg, Exception e) {
        getInstance().printStackTrace2(tag, msg, e);
    }

    public static void printStackTraceErr(Error e) {
        getInstance().printStackTraceErr2(e);
    }

    public static void printStackTraceErr(String tag, String msg, Error e) {
        getInstance().printStackTraceErr2(tag, msg, e);
    }

    public static void dArr(String tag, String msg, String[] arr) {
        getInstance().dArr2(tag, msg, arr);
    }

    public static void dList(String tag, String msg, List<String> list) {
        getInstance().dList2(tag, msg, list);
    }

}
