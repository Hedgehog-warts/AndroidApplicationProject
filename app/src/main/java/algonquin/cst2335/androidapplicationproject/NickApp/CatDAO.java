package algonquin.cst2335.androidapplicationproject.NickApp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * Interface for the cats table in the cat_database. Defines methods to
 * insert, query and delete cat data
 */
@Dao
public interface CatDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert( cats catID);

    @Query("Select * from cats_t")
    public List<cats> getAllCats();

    @Delete
    void delete(cats catID);

}
