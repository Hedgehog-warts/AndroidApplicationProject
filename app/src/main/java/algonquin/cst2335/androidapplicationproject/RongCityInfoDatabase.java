package algonquin.cst2335.androidapplicationproject;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {RongCityInfo.class}, version = 1)
public abstract class RongCityInfoDatabase extends RoomDatabase {

    public abstract RongCityInfoDAO cmDAO();
}