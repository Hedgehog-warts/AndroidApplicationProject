package algonquin.cst2335.androidapplicationproject.DoyoungApp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface DoyoungImgDetailDao {

    @Insert
    public long insertImage(DoyoungImgDetail i);

    @Query("Select * from DoyoungImgDetail")
    List<DoyoungImgDetail> getAllImages();

    @Delete
    public void deleteImage(DoyoungImgDetail d);

    @Query("SELECT * FROM DoyoungImgDetail WHERE Date=:pd AND PhotoID=:pi")
    public List<DoyoungImgDetail> checkDuplication(int pd, int pi);
}