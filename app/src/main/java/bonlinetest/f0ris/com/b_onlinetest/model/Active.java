package bonlinetest.f0ris.com.b_onlinetest.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import bonlinetest.f0ris.com.b_onlinetest.BuildConfig;
import bonlinetest.f0ris.com.b_onlinetest.Parser;
import bonlinetest.f0ris.com.b_onlinetest.network.RequestDealer;

public class Active implements Parcelable {

    public String name;
    public ArrayList<Position> positions = new ArrayList<>();

    public Active(String activeName) {
        this.name = activeName;
    }

    public void getPositionsUpdates() {
        try {
            String response = RequestDealer.requestActiveUpdate(this.name);
            if (BuildConfig.DEBUG){
                System.out.println(response);
            }
            this.positions.add(Parser.parsePosition(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Parcelable implementation*/
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(positions);
    }

    public static final Parcelable.Creator<Active> CREATOR
            = new Parcelable.Creator<Active>() {
        public Active createFromParcel(Parcel in) {
            return new Active(in);
        }

        public Active[] newArray(int size) {
            return new Active[size];
        }
    };

    private Active(Parcel in) {
        name = in.readString();
        in.readTypedList(positions, Position.CREATOR);
    }
}
