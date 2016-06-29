package bonlinetest.f0ris.com.b_onlinetest;

import java.util.Calendar;

import bonlinetest.f0ris.com.b_onlinetest.Models.Active;


public class JsonParser {

    private static Calendar calendar = Calendar.getInstance();

    public static Active parseActive(String response) {

        String[] arr = response.split(",");

        JsonParser.calendar.setTimeInMillis(Long.parseLong(arr[1]));

        // glue values
        float[] prices = {Float.parseFloat(arr[2] + arr[3]), Float.parseFloat(arr[4] + arr[5])};

        return new Active(arr[0], JsonParser.calendar.getTime(), prices);
    }
}
