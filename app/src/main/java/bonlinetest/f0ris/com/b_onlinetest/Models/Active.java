package bonlinetest.f0ris.com.b_onlinetest.Models;

import java.util.Date;

public class Active {
    public String active;
    public Date date;
    public Float[] prices;

    public Active(String active, Date date, Float[] price) {
        this.active = active;
        this.date = date;
        this.prices = price;
    }

}
