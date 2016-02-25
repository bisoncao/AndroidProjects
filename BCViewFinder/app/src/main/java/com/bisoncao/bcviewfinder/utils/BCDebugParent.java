package com.bisoncao.bcviewfinder.utils;

import android.util.Log;

import java.util.List;

/**
 * Parent class of debug class for log output
 *
 * @author Bison Cao
 * @created 1:55 02/26/2016
 */
public class BCDebugParent {
    public static final String BISON = "bison";

    private boolean mDebug;

    public BCDebugParent(boolean isDebug) {
        mDebug = isDebug;
    }

    /**
     * Output DEBUG level log when {@link #mDebug} is true
     *
     * @param tag
     * @param msg
     */
    public void d2(String tag, String msg) {
        if (mDebug) {
            Log.d(tag, msg + "");
        }
    }

    /**
     * Output INFO level log when {@link #mDebug} is true
     *
     * @param tag
     * @param msg
     */
    public void i2(String tag, String msg) {
        if (mDebug) {
            Log.i(tag, msg + "");
        }
    }

    /**
     * Output ERROR level log when {@link #mDebug} is true
     *
     * @param tag
     * @param msg
     */
    public void e2(String tag, String msg) {
        if (mDebug) {
            Log.e(tag, msg + "");
        }
    }

    /**
     * Output WARN level log when {@link #mDebug} is true
     */
    public void w2(String tag, String msg) {
        if (mDebug) {
            Log.w(tag, msg + "");
        }
    }

    /**
     * Print stack trace of exception when {@link #mDebug} is true
     */
    public void printStackTrace2(Exception e) {
        if (mDebug) {
            e.printStackTrace();
        }
    }

    /**
     * Print stack trace of exception with tag & msg when {@link #mDebug} is true
     */
    public void printStackTrace2(String tag, String msg, Exception e) {
        if (mDebug) {
            Log.e(tag, msg, e);
        }
    }

    /**
     * Print stack trace of error when {@link #mDebug} is true
     */
    public void printStackTraceErr2(Error e) {
        if (mDebug) {
            e.printStackTrace();
        }
    }

    /**
     * Print stack trace of error with tag & msg when {@link #mDebug} is true
     */
    public void printStackTraceErr2(String tag, String msg, Error e) {
        if (mDebug) {
            Log.e(tag, msg, e);
        }
    }

    /**
     * Output DEBUG level log which consist of String array when {@link #mDebug} is true
     */
    public void dArr2(String tag, String msg, String[] arr) {
        if (mDebug) {
            Log.d(tag, msg);
            String res = "";
            if (arr != null) {
                for (String string : arr) {
                    res += string + ";";
                }
                res = res.substring(0, res.length() - 1);
            }
            Log.d(tag, res);

        }
    }

    /**
     * Output DEBUG level log which consist of String list when {@link #mDebug} is true
     */
    public void dList2(String tag, String msg, List<String> list) {
        if (mDebug) {
            String res = msg;
            if (list != null && list.size() != 0) {
                for (String string : list) {
                    res += string + ";";
                }
                res = res.substring(0, res.length() - 1);
            }
            Log.d(tag, res);

        }
    }

}
