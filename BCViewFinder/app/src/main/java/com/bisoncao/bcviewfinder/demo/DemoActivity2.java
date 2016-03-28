package com.bisoncao.bcviewfinder.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bisoncao.bcviewfinder.R;
import com.bisoncao.bcviewfinder.ViewFinder;
import com.bisoncao.bcviewfinder.ViewFinderBind;

/**
 * Demo activity for using ViewFinder with group field
 *
 * @created 2:42 PM 02/26/2016
 * @author Bison Cao
 */
public class DemoActivity2 extends AppCompatActivity {

    private static final String GLABEL_HEADER_VIEW = "header_view";

    private TextView[] tvs;
    @ViewFinderBind(value = R.id.tv_1, group = GLABEL_HEADER_VIEW)
    private TextView tv1;
    @ViewFinderBind(value = R.id.tv_2, group = GLABEL_HEADER_VIEW)
    private TextView tv2;
    @ViewFinderBind(value = R.id.tv_3, group = GLABEL_HEADER_VIEW)
    private TextView tv3;
    @ViewFinderBind(value = R.id.tv_4, group = GLABEL_HEADER_VIEW)
    private TextView tv4;
    @ViewFinderBind(value = R.id.tv_5, group = GLABEL_HEADER_VIEW)
    private TextView tv5;
    @ViewFinderBind(value = R.id.tv_6, group = GLABEL_HEADER_VIEW)
    private TextView tv6;
    @ViewFinderBind(value = R.id.tv_7, group = GLABEL_HEADER_VIEW)
    private TextView tv7;
    @ViewFinderBind(value = R.id.tv_8, group = GLABEL_HEADER_VIEW)
    private TextView tv8;
    @ViewFinderBind(value = R.id.tv_9, group = GLABEL_HEADER_VIEW)
    private TextView tv9;

    @ViewFinderBind(R.id.tv_selected_indicator)
    private TextView tvSelectedIndicator;
    @ViewFinderBind(R.id.btn_select_next)
    private Button btnSelectNext;
    @ViewFinderBind(R.id.toolbar)
    private Toolbar toolbar;
    @ViewFinderBind(R.id.list_view)
    private ListView listView;
    @ViewFinderBind(R.id.btn_back_to_demo_1)
    private Button btnBack;
    private int curSelected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_2);

        /**
         * ****** ATTENTION ******
         * bind the member variables annotated with ViewFinderBind without group field
         */
        ViewFinder.bind(DemoActivity2.this);

        View headerView = getLayoutInflater().inflate(R.layout.activity_demo_2_header_view, null);

        /**
         * ****** ATTENTION ******
         * bind the member variables annotated with ViewFinderBind with group field
         */
        ViewFinder.bind(DemoActivity2.this, GLABEL_HEADER_VIEW, headerView);

        tvs = new TextView[]{tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9};

        listView.addHeaderView(headerView);
        listView.setAdapter(null);
        tvSelectedIndicator.setText(getString(R.string.selected_indicator_formatter, "(none)"));

        setSupportActionBar(toolbar);

        btnSelectNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectNext();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private synchronized void selectNext() {
        if (curSelected >= 0) {
            tvs[curSelected].setTextColor(Color.parseColor("#000000"));
        }
        curSelected = curSelected + 1 > tvs.length - 1 ? 0 : curSelected + 1;
        tvSelectedIndicator.setText(getString(R.string.selected_indicator_formatter, tvs[curSelected].getText()));
        tvs[curSelected].setTextColor(Color.parseColor("#ff0000"));
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
