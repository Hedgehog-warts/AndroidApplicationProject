package algonquin.cst2335.androidapplicationproject.XingyunApp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * The database that saves the favorite articles
 */
@Database(entities = { XingyunArticle.class }, version = 1)
public abstract class XingyunDatabase extends RoomDatabase {
    public abstract XingyunArticleDAO xaDAO();
}
