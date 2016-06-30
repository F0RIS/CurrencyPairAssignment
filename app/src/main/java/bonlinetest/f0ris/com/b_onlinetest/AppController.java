package bonlinetest.f0ris.com.b_onlinetest;

import android.app.Application;
import android.content.Context;

public class AppController extends Application {

    private static Context context;
    private static AppController instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }

    public static AppController getInstance() {
        return instance;
    }

}
