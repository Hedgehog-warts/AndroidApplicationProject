package algonquin.cst2335.androidapplicationproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity

public class RongCityInfo {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") // database will automatically increment this;
    public long id;
    @ColumnInfo(name = "city")
    String city;
    @ColumnInfo(name = "temperature")
    String temperature;
    @ColumnInfo(name = "description")
    String description;
    @ColumnInfo(name = "timeSent")
    String timeSent;

    /**
     * ChatMessage(): default constructor
     */
    public RongCityInfo() {
    }

    /**
     * Creates an instance of ChatMessage with specified values.
     *
     * @param m    The city name.
     * @param temp The timestamp when the message was sent.
     * @param desp A String value of weather description.
     * @param ts   A String value of timeSent.
     */
    public RongCityInfo(String m, String temp, String desp, String ts) {
        city = m;
        temperature = temp;
        description = desp;
        timeSent = ts;
    }

    public String getCity() {
        return city;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }

    public String getTimeSent() {
        return timeSent;
    }
}
