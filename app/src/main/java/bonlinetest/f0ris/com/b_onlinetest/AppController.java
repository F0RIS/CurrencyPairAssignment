package bonlinetest.f0ris.com.b_onlinetest;

import android.app.Application;
import android.content.Context;

/**
 * Created by F0RIS on 05.03.2016.
 */
public class AppController extends Application {

    private static Context context;
    private static AppController instance;

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
