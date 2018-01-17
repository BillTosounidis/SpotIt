package uom.android.dev.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface FavTrackDao {

    @Query("SELECT * FROM FAVORITE_TRACKS")
    Flowable<List<FavTrack>> getFavoriteTracks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addFavoriteTrack(FavTrack track);

    @Delete
    void deleteFavoriteTrack(FavTrack favTrack);
}
