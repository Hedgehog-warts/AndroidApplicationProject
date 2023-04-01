package algonquin.cst2335.androidapplicationproject.DoyoungApp;

import android.graphics.Bitmap;

public class DoyoungThumbnail {

    String imgNumber;
    String roverName;
    Bitmap thumbnail;

    public DoyoungThumbnail(String i, String r, Bitmap t) {
        imgNumber = i;
        roverName = r;
        thumbnail = t;
    }
}
