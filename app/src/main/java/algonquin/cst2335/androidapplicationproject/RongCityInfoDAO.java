package algonquin.cst2335.androidapplicationproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * The RongCityInfoDAO interface provides methods to interact with the
 * database and perform CRUD operations on RongCityInfo objects.
 */
@Dao
public interface RongCityInfoDAO {
    /**
     * Inserts a RongCityInfo object into the database.
     *
     * @param m the RongCityInfo object to be inserted.
     * @return the ID of the newly inserted object.
     */
    // @Entity, @ColumnInfo,and @PrimaryKey
    @Insert

    public long insertMessage(RongCityInfo m);

    /**
     * Retrieves all RongCityInfo objects from the database.
     *
     * @return a list of RongCityInfo objects stored in the database.
     */
    @Query("Select * from RongCityInfo")
    public List<RongCityInfo> getAllMessages();

    /**
     * Deletes a RongCityInfo object from the database.
     *
     * @param m the RongCityInfo object to be deleted.
     */
    @Delete
    public void deleteMessage(RongCityInfo m);

}

