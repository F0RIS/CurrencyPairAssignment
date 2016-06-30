package bonlinetest.f0ris.com.b_onlinetest;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;

import bonlinetest.f0ris.com.b_onlinetest.Models.Active;

/**
 * Created by F0RIS on 05.03.2016.
 */
public class AppController extends Application {

    private static Context context;
    private static AppController instance;
    public static ArrayList<Active> data = new ArrayList<>();
    public static Active EUR_USD_Active;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        AppController.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return AppController.context;
    }

    public static AppController getInstance() {
        return AppController.instance;
    }

}
