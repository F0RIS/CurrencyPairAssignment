package bonlinetest.f0ris.com.b_onlinetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;

import bonlinetest.f0ris.com.b_onlinetest.Models.Active;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Active active = JsonParser.parseActive("EUR/USD,1467220815591,1.10,996,1.11,001,1.10489,1.11309,1.10662");

        new Thread(new Runnable() {
            @Override
            public void run() {
//                AppController.active = JsonParser.parseActive("EUR/USD,1467220815591,1.10,996,1.11,001,1.10489,1.11309,1.10662");
                for (;;) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String response = null;
                    try {
                        response = MainActivity.this.run("http://webrates.truefx.com/rates/connect.html?q=ozrates&c=EUR/USD&f=csv&s=n");
                        System.out.println(response);
                        AppController.active = JsonParser.parseActive(response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();;


        System.out.println();
    }

    OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
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
