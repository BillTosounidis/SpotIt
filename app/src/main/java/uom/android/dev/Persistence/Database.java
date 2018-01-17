package uom.android.dev.Persistence;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@android.arch.persistence.room.Database(entities = {FavTrack.class}, version = 1)
public abstract class Database extends RoomDatabase{

    private static volatile Database INSTANCE;

    public abstract FavTrackDao favTrackDao();


    public static Database getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        Database.class, "SpotItDb")
                        .build();
            }
        }
        return INSTANCE;
    }
}
