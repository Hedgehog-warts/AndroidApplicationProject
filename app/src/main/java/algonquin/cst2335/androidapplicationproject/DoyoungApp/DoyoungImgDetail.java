package algonquin.cst2335.androidapplicationproject.DoyoungApp;

import android.graphics.Bitmap;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/** This is used to define the columns DoyoungImgDetail table
 * @author Doyoung Kim
 * @version 1.0
 */
@Entity
public class DoyoungImgDetail {

    /** ID(primary key) for the DoyoungImgDetail table */
    @PrimaryKey (autoGenerate = true)
    int id;

    /** Not used in the table, used to store the bitmap data from the server */
    @Ignore
    Bitmap image;

    /** ImagePath column for the DoyoungImgDetail table */
    @ColumnInfo(name="ImagePath")
    String imgPath;

    /** CameraName column for the DoyoungImgDetail table */
    @ColumnInfo(name="CameraName")
    String cameraName;

    /** ImageURL column for the DoyoungImgDetail table */
    @ColumnInfo(name="ImageURL")
    String imgURL;

    /** Date column for the DoyoungImgDetail table */
    @ColumnInfo(name="Date")
    int photoDate;

    /** PhotoID column for the DoyoungImgDetail table */
    @ColumnInfo(name="PhotoID")
    int photoID;

    /** Constructor to store image details
     * @param img a bitmap data of a image
     * @param c a camera name of a image
     * @param i an url of a image
     * @param pd a photoDate
     * @param pi a photoID
     */
    public DoyoungImgDetail(Bitmap img, String c, String i, int pd, int pi) {
        image = img;
        cameraName = c;
        imgURL = i;
        photoDate = pd;
        photoID = pi;
    }

    /** No-arg constructor */
    public DoyoungImgDetail() {}
}
