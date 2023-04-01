package algonquin.cst2335.androidapplicationproject.DoyoungApp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DoyoungImgDetail.class}, version=1)
public abstract class ImageDatabase extends RoomDatabase {
    public abstract DoyoungImgDetailDao imgDAO();
}
