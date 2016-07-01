package bonlinetest.f0ris.com.b_onlinetest.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Storing active's positions
 */
public class Position implements Parcelable {
    public Date date;
    public float value;

    public Position(Date date, float value) {
        this.date = date;
        this.value = value;
    }


    /* Parcelable implementation*/
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(date.getTime());
        dest.writeFloat(value);
    }
    public static final Parcelable.Creator<Position> CREATOR
            = new Parcelable.Creator<Position>() {
        public Position createFromParcel(Parcel in) {
            return new Position(in);
        }

        public Position[] newArray(int size) {
            return new Position[size];
        }
    };

    private Position(Parcel in) {
        date = new Date(in.readLong());
        value = in.readFloat();
    }
}