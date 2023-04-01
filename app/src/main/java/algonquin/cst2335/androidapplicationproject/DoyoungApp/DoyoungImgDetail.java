package algonquin.cst2335.androidapplicationproject.DoyoungApp;

import android.graphics.Bitmap;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class DoyoungImgDetail {

    @PrimaryKey (autoGenerate = true)
    int id;

    @Ignore
    Bitmap image;

    @ColumnInfo(name="ImagePath")
    String imgPath;

    @ColumnInfo(name="CameraName")
    String cameraName;

    @ColumnInfo(name="ImageURL")
    String imgURL;

    public DoyoungImgDetail(Bitmap img, String c, String i) {
        image = img;
        cameraName = c;
        imgURL = i;
    }

    public DoyoungImgDetail() {}
}
