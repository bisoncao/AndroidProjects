package com.bisoncao.bccommonutil;

import android.content.Context;
import android.widget.Toast;

/**
 * @author Bison Cao
 * @created 18:40 06/30/2016
 */
public class BCToastUtil {

    private Context mContext;

    public BCToastUtil(Context context) {
        mContext = context;
    }

    public void showShortToast(CharSequence text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(CharSequence text) {
        Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
    }
}
