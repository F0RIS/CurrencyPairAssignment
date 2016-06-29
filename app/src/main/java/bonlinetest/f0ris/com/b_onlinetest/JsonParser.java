package bonlinetest.f0ris.com.b_onlinetest;

import java.util.ArrayList;
import java.util.Calendar;

import bonlinetest.f0ris.com.b_onlinetest.Models.Active;


public class JsonParser {

    private static Calendar calendar = Calendar.getInstance();

    public static Active parseActive(String response) {

        String[] arr = response.split(",");

        JsonParser.calendar.setTimeInMillis(Long.parseLong(arr[1]));

        ArrayList<Float> arrayList = new ArrayList<>();

        for (int i = 2; i < arr.length; i++) { // 1st val - EUR/USD, 2 val - time
            if (i <= 4) { // glue 2 and 3 price values
                arrayList.add(Float.parseFloat(arr[i] + arr[i + 1]));
                i++;
            } else {
                arrayList.add(Float.parseFloat(arr[i]));
            }
        }

        return new Active(arr[0], JsonParser.calendar.getTime(), arrayList.toArray(new Float[arrayList.size()]));
    }
}
