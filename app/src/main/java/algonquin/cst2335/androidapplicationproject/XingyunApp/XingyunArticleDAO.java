package algonquin.cst2335.androidapplicationproject.XingyunApp;

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

    /**
     * The Insert or Add query
     * 
     * @param article article
     * @return data
     */
    @Insert
    public long insertArticle(XingyunArticle article);

    /**
     * The Select query
     * 
     * @return data
     */
    @Query("Select * from XingyunArticle")
    public List<XingyunArticle> getAllFavs();

    /**
     * The Delete query
     * 
     * @param article data
     */
    @Delete
    void deleteArticle(XingyunArticle article);

}
