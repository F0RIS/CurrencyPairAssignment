package bonlinetest.f0ris.com.b_onlinetest.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Storing active's positions
 */
public class Position implements Serializable {
    public Date date;
    public float value;

    public Position(Date date, float value) {
        this.date = date;
        this.value = value;
    }
/*
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(date.getTime());
        dest.writeFloat(value);
    }*/
}