package bonlinetest.f0ris.com.b_onlinetest.Models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import bonlinetest.f0ris.com.b_onlinetest.JsonParser;
import bonlinetest.f0ris.com.b_onlinetest.RequestDealer;

public class Active {

    public String name;
    public ArrayList<Position> positions = new ArrayList<>();

    public static class Position{
        public Date date;
        public float value;

        public Position(Date date, float value) {
            this.date = date;
            this.value = value;
        }
    }

    public Active(String activeName) {
        this.name = activeName;
    }

    public void getPositionsUpdates(){
        try {
            String response = RequestDealer.requestActiveUpdate(this.name);
            this.positions.add(JsonParser.parsePosition(response));
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
