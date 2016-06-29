package bonlinetest.f0ris.com.b_onlinetest.Models;

import java.util.Date;

public class Active {
    public String name;
    public Date date;
    public float[] prices; //bid, offer

    public Active(String active, Date date, float[] price) {
        this.name = active;
        this.date = date;
        this.prices = price;
    }

}
