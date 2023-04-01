package algonquin.cst2335.androidapplicationproject.DoyoungApp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface DoyoungImgDetailDao {

    @Insert
    public long insertMessage(DoyoungImgDetail d);

    @Query("Select * from DoyoungImgDetail")
    List<DoyoungImgDetail> getAllMessages();

    @Delete
    public void deleteMessage(DoyoungImgDetail m);

}