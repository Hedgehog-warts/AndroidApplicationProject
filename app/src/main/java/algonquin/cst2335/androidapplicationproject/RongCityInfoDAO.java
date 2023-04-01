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

    public long insertMessage(RongCityInfo m);

    @Query("Select * from RongCityInfo")
    public List<RongCityInfo> getAllMessages();

    @Delete
    public void deleteMessage(RongCityInfo m);

}
