package algonquin.cst2335.androidapplicationproject.NickApp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CatDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert( cats catID);

    @Query("SELECT * FROM cats_t")
    List<cats> getAllCats();

    @Delete
    void delete(cats catID);

}
