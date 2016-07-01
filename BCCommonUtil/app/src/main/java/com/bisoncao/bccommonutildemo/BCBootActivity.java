package com.bisoncao.bccommonutildemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class BCBootActivity extends AppCompatActivity {

    private static final String TAG = "BCBootActivity";
    private static final int DURATION = 2000;
    // indicates whether it is exit by user before finish
    private boolean earlyExit = false;

    private static final int GOTO_MAIN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!earlyExit) {
                    startActivity(new Intent(BCBootActivity.this, MainActivity.class));
                    finish();
                } else {
                    Log.d(TAG, "early exit by user!");
                }
            }
        }, DURATION);
    }

    @Override
    protected void onDestroy() {
        earlyExit = true;
        super.onDestroy();
    }

    private Handler handler = new Handler() {
    };

}
