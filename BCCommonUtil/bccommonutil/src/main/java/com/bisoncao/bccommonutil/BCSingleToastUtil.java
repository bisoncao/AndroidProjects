package com.bisoncao.bccommonutil;

import android.content.Context;
import android.widget.Toast;

/**
 * @author Bison Cao
 * @created 18:40 06/30/2016
 */
public class BCSingleToastUtil {

    private Context mContext;
    private Toast singleToast;

    public BCSingleToastUtil(Context context) {
        mContext = context;
    }

    public void showShortToast(CharSequence text) {
        if (singleToast == null) {
            singleToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
            singleToast.show();
        } else {
            singleToast.setText(text);
            singleToast.show();
        }
    }

    public void showLongToast(CharSequence text) {
        Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
    }
}
