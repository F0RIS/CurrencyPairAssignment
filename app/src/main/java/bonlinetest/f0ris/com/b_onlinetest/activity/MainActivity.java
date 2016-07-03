package bonlinetest.f0ris.com.b_onlinetest.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import bonlinetest.f0ris.com.b_onlinetest.R;
import bonlinetest.f0ris.com.b_onlinetest.fragment.ChartFragment;
import bonlinetest.f0ris.com.b_onlinetest.model.Active;

public class MainActivity extends AppCompatActivity {

    private Active active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (savedInstanceState == null)
            active = new Active("EUR/USD");
        else
            active = savedInstanceState.getParcelable("active");

//        long time = System.currentTimeMillis();
//        for (int i = 0; i < 1; i++) {
        //add one record before drawing for fast start
//            active.positions.add(new Position(new Date(1467405900624L), (float) (1+Math.random()*0.2f)));
//        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                active.getPositionsUpdates();//add one record before chart drawing for fast app start
                ChartFragment chartFragment = ChartFragment.newInstance(active);
                FragmentTransaction transaction = MainActivity.this.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, chartFragment, ChartFragment.TAG);
                transaction.commitAllowingStateLoss();
            }
        }).start();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("active", active);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
