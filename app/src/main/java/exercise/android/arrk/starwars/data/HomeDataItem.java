package exercise.android.arrk.starwars.data;

import android.os.Parcel;
import android.os.Parcelable;

public class HomeDataItem implements Parcelable {
    private String name;
    private String height;
    private String mass;
    private String date;
    private String time;

    public String getName() {
        return name;
    }

    public void setName(String stringId) {
        this.name = stringId;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public HomeDataItem() {
    }

    protected HomeDataItem(Parcel in) {
        name = in.readString();
        height = in.readString();
        mass = in.readString();
        date = in.readString();
        time = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(height);
        dest.writeString(mass);
        dest.writeString(date);
        dest.writeString(time);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<HomeDataItem> CREATOR = new Parcelable.Creator<HomeDataItem>() {
        @Override
        public HomeDataItem createFromParcel(Parcel in) {
            return new HomeDataItem(in);
        }

        @Override
        public HomeDataItem[] newArray(int size) {
            return new HomeDataItem[size];
        }
    };
}