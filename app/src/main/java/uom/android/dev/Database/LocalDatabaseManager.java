package uom.android.dev.Database;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.widget.Toast;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import uom.android.dev.Fragments.FavTracksFragment;
import uom.android.dev.LastFmJson.TopTrack;
import uom.android.dev.LastFmJson.TrackSearch;
import uom.android.dev.LastFmJson.TrackSimilar;


public class LocalDatabaseManager {

    private static volatile Database db;
    private static final String DB_NAME = "SpotIt-db";
    private static LocalDatabaseManager INSTANCE;
    private Context context;

    public static LocalDatabaseManager getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = new LocalDatabaseManager(context);
        }
        return INSTANCE;
    }

    public LocalDatabaseManager(Context context){
        this.context = context;
        synchronized (Database.class) {
            db = Room.databaseBuilder(context, Database.class, DB_NAME).build();
        }
    }

    public void addFavTrackSimilar(final TrackSimilar track){
        final FavTrack favTrack = new FavTrack(
                track.getName(),
                track.getmArtist().getName(),
                track.getDesiredImage("large"),
                track.getMbid(),
                track.getUrl()
        );
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                db.favTrackDao().addFavoriteTrack(favTrack);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Toast.makeText(context, "Track saved as favorite.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                getFavTrackToDelete(favTrack.getName(), favTrack.getArtist());
            }
        });
    }

    public void addFavTrack(final TrackSearch track){
        final FavTrack favTrack = new FavTrack(
                track.getName(),
                track.getmArtist(),
                track.getDesiredImage("large"),
                track.getMbid(),
                track.getUrl()
        );
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                db.favTrackDao().addFavoriteTrack(favTrack);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Toast.makeText(context, "Track saved as favorite.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                getFavTrackToDelete(favTrack.getName(), favTrack.getArtist());
            }
        });
    }

    public void addFavTrackTop(final TopTrack track){
        final FavTrack favTrack = new FavTrack(
                track.getName(),
                track.getmArtist().getName(),
                track.getDesiredImage("large"),
                track.getMbid(),
                track.getUrl()
        );
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                db.favTrackDao().addFavoriteTrack(favTrack);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Toast.makeText(context, "Track saved as favorite.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                getFavTrackToDelete(favTrack.getName(), favTrack.getArtist());
            }
        });
    }

    public void deleteFavTrack(final FavTrack favTrack){

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                db.favTrackDao().deleteFavoriteTrack(favTrack);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Toast.makeText(context, "Track removed from favorites.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context, "Error removing track from favorites.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getFavTrackToDelete(final String trackName, final String trackArtist){
        db.favTrackDao().getFavoriteTrack(trackName, trackArtist).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<FavTrack>() {
                    @Override
                    public void accept(FavTrack favTrack) throws Exception {
                        deleteFavTrack(favTrack);
                    }
                });
    }

    public void getFavTracks(){
        db.favTrackDao().getFavoriteTracks().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<FavTrack>>() {
                    @Override
                    public void accept(List<FavTrack> favTracks) throws Exception {

                    }
                });
    }

    public Database getDb(){
        return db;
    }
}
