package com.bisoncao.bccommonutil;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * A util for clipboard related operations.
 * @author Bison Cao
 * @created 18:16 06/30/2016
 */
public class BCClipboardUtil {

    private ClipboardManager mClipboard;

    public static final String DEFAULT_TEXT_LABEL = "text";

    public BCClipboardUtil(Context context) {
        mClipboard = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
    }

    public boolean copyText(Context context, CharSequence text) {
        return copyText(context, DEFAULT_TEXT_LABEL, text);
    }

    public boolean copyText(Context context, CharSequence label, CharSequence text) {
        try {
            mClipboard.setPrimaryClip(ClipData.newPlainText(label, text));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
