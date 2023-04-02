package algonquin.cst2335.androidapplicationproject.DoyoungApp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

/** This is used to query to the DoyoungImgDetailDao table
 * @author Doyoung Kim
 * @version 1.0
 */
@Dao
public interface DoyoungImgDetailDao {

    /** Insert DoyoungImgDetail object
     * @param i an object of DoyoungImgDetail
     * @return data inserted in the DoyoungImgDetail table
     */
    @Insert
    public long insertImage(DoyoungImgDetail i);

    /** Get all data from DoyoungImgDetail table
     * @return all data from DoyoungImgDetail table
     */
    @Query("SELECT * FROM DoyoungImgDetail")
    List<DoyoungImgDetail> getAllImages();

    /** Delete selected DoyoungImgDetail object from the DoyoungImgDetail table
     * @param d an object of DoyoungImgDetail
     */
    @Delete
    public void deleteImage(DoyoungImgDetail d);

    /** Delete selected DoyoungImgDetail object based on Date and PhotoID from the DoyoungImgDetail table
     * @param pd a photoDate to select from DoyoungImgDetail table
     * @param pi a photoID to select from DoyoungImgDetail table
     * @return some data based on photoDate and photoID from DoyoungImgDetail table
     */
    @Query("SELECT * FROM DoyoungImgDetail WHERE Date=:pd AND PhotoID=:pi")
    public List<DoyoungImgDetail> checkDuplication(int pd, int pi);
}