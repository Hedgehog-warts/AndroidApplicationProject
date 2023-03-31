package algonquin.cst2335.androidapplicationproject;

public class RongCityInfo {
    private String city;
    private String temperature;

    private String description;
    private String timeSent;
    public RongCityInfo(String m, String temp, String desp,String ts) {
        city = m;
        temperature = temp;
        description = desp;
        timeSent = ts;
    }

    public String getCIty() {
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
