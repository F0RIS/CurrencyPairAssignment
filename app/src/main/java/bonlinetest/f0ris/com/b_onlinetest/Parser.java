package bonlinetest.f0ris.com.b_onlinetest;

import java.text.ParseException;
import java.util.Calendar;

import bonlinetest.f0ris.com.b_onlinetest.model.Active;


public class Parser {

    private static Calendar calendar = Calendar.getInstance();

    public static Active.Position parsePosition(String response) throws NumberFormatException, ParseException {
        String[] arr = response.split(",");
        Parser.calendar.setTimeInMillis(Long.parseLong(arr[1]));
        return new Active.Position(Parser.calendar.getTime(), Float.parseFloat(arr[2] + arr[3])); // glue values
    }
}
