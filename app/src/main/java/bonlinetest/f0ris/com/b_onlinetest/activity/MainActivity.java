package bonlinetest.f0ris.com.b_onlinetest.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import bonlinetest.f0ris.com.b_onlinetest.R;
import bonlinetest.f0ris.com.b_onlinetest.fragment.ActiveChartFragment;
import bonlinetest.f0ris.com.b_onlinetest.model.Active;

public class MainActivity extends AppCompatActivity {

    private Active active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new Thread(new Runnable() {
            @Override
            public void run() {
                active = new Active("EUR/USD");
                active.getPositionsUpdates();//add one record before chart drawing for fast app start
                ActiveChartFragment activeChartFragment = ActiveChartFragment.newInstance(active);
                FragmentTransaction transaction = MainActivity.this.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, activeChartFragment, ActiveChartFragment.TAG);
                transaction.commitAllowingStateLoss();
            }
        }).start();

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
