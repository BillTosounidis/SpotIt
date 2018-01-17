package uom.android.dev.Database;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@android.arch.persistence.room.Database(entities = {FavTrack.class}, version = 1)
public abstract class Database extends RoomDatabase{

    public abstract FavTrackDao favTrackDao();
}
