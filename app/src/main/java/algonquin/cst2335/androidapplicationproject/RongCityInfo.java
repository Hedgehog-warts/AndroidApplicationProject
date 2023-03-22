package algonquin.cst2335.androidapplicationproject;

public class RongCityInfo {
    private String message;
    private String timeSent;
    private boolean isSentButton;

    public RongCityInfo(String m, String t, boolean sent) {
        message = m;
        timeSent = t;
        isSentButton = sent;
    }

    public String getMessage() {
        return message;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public boolean isSentButton() {
        return isSentButton;
    }
}
