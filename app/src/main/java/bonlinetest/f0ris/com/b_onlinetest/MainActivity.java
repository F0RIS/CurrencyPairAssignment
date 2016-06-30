package bonlinetest.f0ris.com.b_onlinetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import bonlinetest.f0ris.com.b_onlinetest.Models.Active;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        Active active = JsonParser.parseActive("EUR/USD,1467220815591,1.10,996,1.11,001,1.10489,1.11309,1.10662");

        new Thread(new Runnable() {
            @Override
            public void run() {
                Active EUR_USD_Active = new Active("EUR/USD");
                AppController.EUR_USD_Active = EUR_USD_Active;
                while (!Thread.interrupted()) {
                    try {
                        EUR_USD_Active.getPositionsUpdates();
                        Thread.sleep(1000);
                    }  catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
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
