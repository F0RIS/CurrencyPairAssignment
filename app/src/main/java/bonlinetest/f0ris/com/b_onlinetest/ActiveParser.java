package bonlinetest.f0ris.com.b_onlinetest;

import java.text.ParseException;
import java.util.Calendar;

import bonlinetest.f0ris.com.b_onlinetest.Models.Active;


public class ActiveParser {

    private static Calendar calendar = Calendar.getInstance();

    public static Active.Position parsePosition(String response) throws NumberFormatException, ParseException {
        String[] arr = response.split(",");
        ActiveParser.calendar.setTimeInMillis(Long.parseLong(arr[1]));
        return new Active.Position(ActiveParser.calendar.getTime(), Float.parseFloat(arr[2] + arr[3])); // glue values
    }
}
