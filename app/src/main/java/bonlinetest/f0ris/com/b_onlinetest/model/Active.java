package bonlinetest.f0ris.com.b_onlinetest.model;

import java.io.Serializable;
import java.util.ArrayList;

import bonlinetest.f0ris.com.b_onlinetest.Parser;
import bonlinetest.f0ris.com.b_onlinetest.network.RequestDealer;

public class Active implements Serializable {

    public String name;
    public ArrayList<Position> positions = new ArrayList<>();

    public Active(String activeName) {
        this.name = activeName;
    }

    public void getPositionsUpdates(){
        try {
            String response = RequestDealer.requestActiveUpdate(this.name);
            System.out.println(response);
            this.positions.add(Parser.parsePosition(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
