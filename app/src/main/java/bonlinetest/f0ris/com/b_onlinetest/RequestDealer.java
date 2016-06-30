package bonlinetest.f0ris.com.b_onlinetest;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by F0RIS on 30.06.2016.
 */
public class RequestDealer {

    private static final String REQUEST_URL = "http://webrates.truefx.com/rates/connect.html?q=ozrates&c=%s&f=csv&s=n";

    private static OkHttpClient client = new OkHttpClient();

    public static String requestActiveUpdate(String ActiveName) throws IOException {
        Request request = new Request.Builder()
                .url(String.format(REQUEST_URL, ActiveName))
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
