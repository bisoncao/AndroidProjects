package nom.bisoncao.bcviewfinder.activity;

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

import java.nio.charset.Charset;

import nom.bisoncao.bcviewfinder.R;
import nom.bisoncao.bcviewfinder.utils.ViewFinder;
import nom.bisoncao.bcviewfinder.utils.ViewFinderBind;

public class MainActivity extends AppCompatActivity {

    @ViewFinderBind(R.id.tv_first)
    public TextView tvFirst;
    @ViewFinderBind(R.id.tv_second)
    public TextView tvSecond;
    @ViewFinderBind(R.id.tv_third)
    public TextView tvThird;
    @ViewFinderBind(R.id.btn_swap)
    public Button btnSwap;
    @ViewFinderBind(R.id.fab)
    public FloatingActionButton fab;
    private CharSequence[] texts = new CharSequence[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewFinder.bind(this, this.getWindow().getDecorView());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
