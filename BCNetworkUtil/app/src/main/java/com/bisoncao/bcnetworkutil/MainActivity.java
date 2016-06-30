package com.bisoncao.bcnetworkutil;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    private NetworkUtil mNetworkUtil;

    private Handler mHandler = new Handler();
    private Context mContext = this;
    private Button btnDetect;
    private Button btnAutoDetect;
    private TextView tvStatus;
    private EditText etHistory;
    private View layoutHistoryOpr;
    private Button btnSave;
    private Button btnClear;
    private Button btnCopy;
    private SimpleDateFormat mSdf = new SimpleDateFormat("HH:mm:ss ");
    private SimpleDateFormat mSdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
    private boolean isAutoDetect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNetworkUtil = new NetworkUtil();

        btnDetect = (Button) findViewById(R.id.btn_detect);
        btnAutoDetect = (Button) findViewById(R.id.btn_auto_detect);
        tvStatus = (TextView) findViewById(R.id.tv_status);
        etHistory = (EditText) findViewById(R.id.et_history);
        layoutHistoryOpr = findViewById(R.id.layout_history_opr);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnClear = (Button) findViewById(R.id.btn_clear);
        btnCopy = (Button) findViewById(R.id.btn_copy);

        btnDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDetect.setEnabled(false);
                btnAutoDetect.setEnabled(false);
                detect();
            }
        });
        btnAutoDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAutoDetect = !isAutoDetect;
                if (isAutoDetect) {
                    btnDetect.setEnabled(false);
                    btnAutoDetect.setText("Stop Auto Detect");
                    detect();
                    autoDetect();
                } else {
                    mHandler.removeCallbacks(autoDetectRunnable);
                    btnAutoDetect.setText("Auto Detect");
                    btnDetect.setEnabled(true);
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    File file = new File(Environment
                            .getExternalStorageDirectory(), "BCNetworkUtil_"
                            + mSdf2.format(System.currentTimeMillis()) + ".txt");
                    FileOutputStream fos = new FileOutputStream(file);
                    String info = etHistory.getText().toString();
                    fos.write(info.getBytes());
                    fos.close();
                    showToast("Saved successfully!");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("bison cao is me");
                    showToast("Save failed!");
                }
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etHistory.setText("");
                showToast("Cleared!");
            }
        });
        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData myClip = ClipData.newPlainText("text", etHistory
                        .getText().toString());
                myClipboard.setPrimaryClip(myClip);
                showToast("Copied successfully!");
            }
        });
        etHistory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnClear.setEnabled(s.length() != 0);
            }
        });
    }

    private Runnable autoDetectRunnable = new Runnable() {
        @Override
        public void run() {
            detect();
            autoDetect();
        }
    };

    private void autoDetect() {
        mHandler.postDelayed(autoDetectRunnable,
                NetworkUtil.DEFAULT_DETECT_TIMEOUT + 2000);
    }

    private void detect() {
        curBlinkStatus = BLINK_STATUS.ONE;
        tvStatus.setText("Detecting.");
        blink();
        mNetworkUtil.hasInternetAccess(mHandler,
                new NetworkUtil.DetectCallback() {
                    @Override
                    public void onDetectSuccess() {
                        onDetectFinished("Has internet access!");
                    }

                    @Override
                    public void onDetectFail() {
                        onDetectFinished("Has no internet access!");
                    }

                    @Override
                    public void onDetectTimeout() {
                        onDetectFinished("Has no internet access (timeout)!");
                    }
                });
    }

    private void onDetectFinished(String result) {
        if (!isAutoDetect) {
            btnDetect.setEnabled(true);
            btnAutoDetect.setEnabled(true);
        }
        mHandler.removeCallbacks(blinkRunnable);
        tvStatus.setText("");
        showToast(result);
        addHistory(result);
    }

    private void showToast(String msg) {
        if (singleToast == null) {
            singleToast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
            singleToast.show();
        } else {
            singleToast.setText(msg);
            singleToast.show();
        }
    }

    private Toast singleToast;

    private void addHistory(String msg) {
        boolean isVisible = etHistory.getVisibility() == View.VISIBLE;
        if (!isVisible) {
            etHistory.setVisibility(View.VISIBLE);
            layoutHistoryOpr.setVisibility(View.VISIBLE);
        }
        String time = mSdf.format(System.currentTimeMillis());
        String newMsg = time + msg;
        String combinedMsg = newMsg + (isVisible ? "\n" : "")
                + etHistory.getText();
        SpannableString span = new SpannableString(combinedMsg);
        span.setSpan(new ForegroundColorSpan(Color.BLACK), 0, newMsg.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        etHistory.setText(span);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNetworkUtil.unregister();
        mHandler.removeCallbacksAndMessages(null);
    }

    private Runnable blinkRunnable = new Runnable() {
        @Override
        public void run() {
            blinkHelper();
            mHandler.postDelayed(blinkRunnable, 500);
        }
    };

    private enum BLINK_STATUS {
        ONE,
        TWO,
        THREE
    }

    private BLINK_STATUS curBlinkStatus = BLINK_STATUS.THREE;

    private void blink() {
        mHandler.postDelayed(blinkRunnable, 500);
    }

    private void blinkHelper() {
        switch (curBlinkStatus) {
        case ONE:
            curBlinkStatus = BLINK_STATUS.TWO;
            tvStatus.setText(tvStatus.getText() + ".");
            break;
        case TWO:
            curBlinkStatus = BLINK_STATUS.THREE;
            tvStatus.setText(tvStatus.getText() + ".");
            break;
        case THREE:
            curBlinkStatus = BLINK_STATUS.ONE;
            tvStatus.setText(tvStatus.getText().subSequence(0,
                    tvStatus.length() - 2));
            break;
        }
    }
}
