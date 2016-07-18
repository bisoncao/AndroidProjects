package com.bisoncao.bccommonutildemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.bisoncao.bccommonutildemo.R;
import com.bisoncao.bccommonutil.BCClipboardUtil;
import com.bisoncao.bccommonutil.BCSingleToastUtil;

public class MainActivity extends AppCompatActivity {

    private Context mContext = this;
    private BCClipboardUtil mBCClipboardUtil;
    private BCSingleToastUtil mBCSingleToastUtil;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mBCClipboardUtil = new BCClipboardUtil(mContext);
        mBCSingleToastUtil = new BCSingleToastUtil(mContext);

        final EditText et = (EditText) findViewById(R.id.et);
        Button btnCopyToClipboard = (Button) findViewById(R.id.btnCopyToClipboard);
        btnCopyToClipboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean suc = mBCClipboardUtil.copyText(mContext, et.getText());
                mBCSingleToastUtil.showShortToast(suc ? "Copied successfully!"
                        : "Failed to copy!");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
