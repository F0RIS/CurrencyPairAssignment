package bonlinetest.f0ris.com.b_onlinetest.model;

import java.util.ArrayList;
import java.util.Date;

import bonlinetest.f0ris.com.b_onlinetest.ActiveParser;
import bonlinetest.f0ris.com.b_onlinetest.network.RequestDealer;

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
            System.out.println(response);
            this.positions.add(ActiveParser.parsePosition(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
