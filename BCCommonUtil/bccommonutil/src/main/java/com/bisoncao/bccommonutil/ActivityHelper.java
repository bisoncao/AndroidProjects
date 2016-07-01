package com.bisoncao.bccommonutil;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by caohuibin on 16/3/30.
 */
public class ActivityHelper {

    private Activity mActivity;

    public ActivityHelper(Activity activity) {
        mActivity = activity;
    }

    public void startActivity(Class activityClass) {
        mActivity.startActivity(new Intent(mActivity, activityClass));
    }
}
