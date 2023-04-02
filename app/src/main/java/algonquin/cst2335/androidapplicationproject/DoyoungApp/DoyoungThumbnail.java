package algonquin.cst2335.androidapplicationproject.DoyoungApp;

import android.graphics.Bitmap;

/** This is a thumbnail data class
 * @author Doyoung Kim
 * @version 1.0
 */
public class DoyoungThumbnail {

    /** a image number for a thumbnail */
    String imgNumber;

    /** a rover name for a thumbnail */
    String roverName;

    /** a bitmap data for a thumbnail */
    Bitmap thumbnail;

    /** Constructor for DoyoungThumbnail class
     * @param i a number of a image
     * @param r a rover name of a image
     * @param t a bitmap data of a image
     */
    public DoyoungThumbnail(String i, String r, Bitmap t) {
        imgNumber = i;
        roverName = r;
        thumbnail = t;
    }
}
