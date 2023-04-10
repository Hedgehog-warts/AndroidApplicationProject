package algonquin.cst2335.androidapplicationproject.NickApp;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * The AppDatabase class is an abstract class that extends RoomDatabase
 * and provides access to the database using DAOs
 */
@Database(entities = {cats.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CatDAO catDAO();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "cat_database")
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE IF EXISTS cats_t");
            database.execSQL("CREATE TABLE cats_t (catURL TEXT NOT NULL PRIMARY KEY )");
        }
    };

    public CatDAO getCatDAO() {
        return catDAO();
    }

}
