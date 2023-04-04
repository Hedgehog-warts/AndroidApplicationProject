package algonquin.cst2335.androidapplicationproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {XingyunArticle.class}, version=1)
public abstract class XingyunDatabase  extends RoomDatabase {
    public abstract XingyunArticleDAO xaDAO();
}
