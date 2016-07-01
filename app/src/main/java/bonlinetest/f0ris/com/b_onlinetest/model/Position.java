package bonlinetest.f0ris.com.b_onlinetest.model;

import java.util.Date;

/**
 * Storing active's positions
 */
public class Position {
    public Date date;
    public float value;

    public Position(Date date, float value) {
        this.date = date;
        this.value = value;
    }
}