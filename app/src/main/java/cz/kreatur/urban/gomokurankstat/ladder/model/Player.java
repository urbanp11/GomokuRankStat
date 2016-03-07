package cz.kreatur.urban.gomokurankstat.ladder.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Petr Urban on 07.03.16.
 */
public class Player implements Parcelable {
    private String name;
    private int elo;
    private String countryCode;

    public Player(String countryCode, int elo, String name) {
        this.countryCode = countryCode;
        this.elo = elo;
        this.name = name;
    }

    protected Player(Parcel in) {
        name = in.readString();
        elo = in.readInt();
        countryCode = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(elo);
        dest.writeString(countryCode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Player{" +
                "countryCode='" + countryCode + '\'' +
                ", name='" + name + '\'' +
                ", elo=" + elo +
                '}';
    }
}
