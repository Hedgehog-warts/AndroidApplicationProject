package algonquin.cst2335.androidapplicationproject;


import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * The RongCityInfoDatabase class is an abstract class that extends the RoomDatabase class
 * and provides access to the RongCityInfoDAO data access object.
 * This class is responsible for creating and maintaining the database instance, and defining
 * the version and entities associated with the database.
 */
@Database(entities = {RongCityInfo.class}, version = 1)
public abstract class RongCityInfoDatabase extends RoomDatabase {
    /**
     * Provides access to the RongCityInfoDAO data access object.
     *
     * @return RongCityInfoDAO instance.
     */
    public abstract RongCityInfoDAO cmDAO();
}