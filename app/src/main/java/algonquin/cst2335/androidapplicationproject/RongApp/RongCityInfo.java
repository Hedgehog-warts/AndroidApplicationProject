package algonquin.cst2335.androidapplicationproject.RongApp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * RongCityInfo class represents a model of the city weather information, which is stored in a SQLite database.
 * The class defines the attributes and methods for getting the weather information of a city.
 *
 * @author [Rong Ran]
 * @version [v1.0]
 */
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
     * RongCityInfo(): default constructor
     */
    public RongCityInfo() {
    }

    /**
     * Parameterized constructor for RongCityInfo.
     *
     * @param m    the name of the city
     * @param temp the current temperature of the city
     * @param desp a brief description of the weather condition
     * @param ts   the time when the weather information was updated
     */
    public RongCityInfo(String m, String temp, String desp, String ts) {
        city = m;
        temperature = temp;
        description = desp;
        timeSent = ts;
    }

    /**
     * Getter method for city.
     *
     * @return the name of the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Getter method for temperature.
     *
     * @return the current temperature of the city
     */
    public String getTemperature() {
        return temperature;
    }

    /**
     * Getter method for description.
     *
     * @return a brief description of the weather condition
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter method for timeSent.
     *
     * @return the time when the weather information was updated
     */
    public String getTimeSent() {
        return timeSent;
    }
}
