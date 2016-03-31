package com.bisoncao.bccommonutil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bisoncao.bcviewfinder.demo.DemoActivity;

public class BCBootActivity extends AppCompatActivity {

    private static final String TAG = "BCBootActivity";
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
                    startActivity(new Intent(BCBootActivity.this, DemoActivity.class));
                    finish();
                } else {
                    Log.d(TAG, "early exit by user!");
                }
            }
        }, 1500);
    }

    @Override
    protected void onDestroy() {
        earlyExit = true;
        super.onDestroy();
    }

    private Handler handler = new Handler() {
    };

}
