package com.bisoncao.bcviewfinder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bisoncao.bcviewfinder.R;
import com.bisoncao.bcviewfinder.utils.ViewFinder;
import com.bisoncao.bcviewfinder.utils.ViewFinderBind;

/**
 * Demo activity for usage of {@link ViewFinder}
 *
 * @author Bison Cao
 * @created 1:54 02/26/2016
 */
public class DemoActivity extends AppCompatActivity {

    @ViewFinderBind(R.id.tv_first)
    private TextView tvFirst;
    @ViewFinderBind(R.id.tv_second)
    private TextView tvSecond;
    @ViewFinderBind(R.id.tv_third)
    private TextView tvThird;
    @ViewFinderBind(R.id.btn_swap)
    private Button btnSwap;
    @ViewFinderBind(R.id.toolbar)
    private Toolbar toolbar;
    @ViewFinderBind(R.id.fab)
    private FloatingActionButton fab;
    @ViewFinderBind(R.id.btn_goto_demo_2)
    private Button btnGotoDemo2;
    private CharSequence[] texts = new CharSequence[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        /**
         * ****** ATTENTION ******
         * Using like this (the "this" below means DemoActivity.this)
         */
        ViewFinder.bind(this, this.getWindow().getDecorView());

        setSupportActionBar(toolbar);

        texts[0] = tvFirst.getText();
        texts[1] = tvSecond.getText();
        texts[2] = tvThird.getText();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        btnSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence temp = texts[0];
                texts[0] = texts[2];
                texts[2] = texts[1];
                texts[1] = temp;

                tvFirst.setText(texts[0]);
                tvSecond.setText(texts[1]);
                tvThird.setText(texts[2]);
            }
        });

        btnGotoDemo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DemoActivity.this, DemoActivity2.class));
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
