package algonquin.cst2335.androidapplicationproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * The Data Access Object of articles
 */
@Dao
public interface XingyunArticleDAO {

    @Insert
    public long insertArticle(XingyunArticle article);

    @Query("Select * from XingyunArticle")
    public List<XingyunArticle> getAllFavs();

    @Delete
    void deleteArticle(XingyunArticle article);

}
