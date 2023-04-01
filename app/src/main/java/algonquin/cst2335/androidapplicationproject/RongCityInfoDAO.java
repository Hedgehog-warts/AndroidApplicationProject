package algonquin.cst2335.androidapplicationproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface RongCityInfoDAO {

    // @Entity, @ColumnInfo,and @PrimaryKey
    @Insert
    public default long insertMessage(RongCityInfo m) {
        return 0;
    }

    @Query("Select * from RongCityInfo")
    public List<RongCityInfo> getAllMessages();

    @Delete
    public void deleteMessage(RongCityInfo m);

}
