package com.peep.peep.Map;



import android.os.Parcel;
import android.os.Parcelable;



public class LocationMap implements Parcelable {

    private String name;
    String location;
    double lat;
    double lon;
    String id;
    Group group;


    public LocationMap() {
    }

    public LocationMap(String name, String location, double lat, double lon) {
        this.name = name;
        this.location = location;
        this.lat = lat;
        this.lon = lon;
    }

    protected LocationMap(Parcel in) {
        name = in.readString();
        lon = in.readDouble();
        lat = in.readDouble();
        location = in.readString();
        id = in.readString();
    }

    public static final Creator<LocationMap> CREATOR = new Creator<LocationMap>() {
        @Override
        public LocationMap createFromParcel(Parcel in) {
            return new LocationMap(in);
        }

        @Override
        public LocationMap[] newArray(int size) {
            return new LocationMap[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeDouble(lon);
        parcel.writeDouble(lat);
        parcel.writeString(location);
        parcel.writeString(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}