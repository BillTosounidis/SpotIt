package uom.android.dev;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import uom.android.dev.LastFmJson.Artist;
import uom.android.dev.LastFmJson.Image;
import uom.android.dev.LastFmJson.LastFmClient;
import uom.android.dev.LastFmJson.SimilarTracksData;
import uom.android.dev.LastFmJson.TopArtist;
import uom.android.dev.LastFmJson.TopArtistsData;
import uom.android.dev.LastFmJson.TopTrack;
import uom.android.dev.LastFmJson.TopTracksData;
import uom.android.dev.LastFmJson.TrackSimilar;

/**
 * Created by v4570 on 11/12/17.
 *
 * This class is the service that we use to make calls to the api to retrieve the information we
 * want.
 * The method getSimilarTracks(String mbid) given the mbid returns a list with all the similar
 * tracks for this song. Mbid is a unique track identifier that is given by lastFM.
 */

public class LastFMSearchService {

    public LastFmClient lastfmclient;

    public LastFMSearchService(){

        RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.LAST_FM_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(rxAdapter)
                .build();
        lastfmclient = retrofit.create(LastFmClient.class);
    }



    // This method makes a call to the api using the unique "mbid" identifier that was returned by
    // the selection of a track onscreen from the user. It fetches the similar tracks and returns
    // the List.
    public Flowable<List<TrackSimilar>> getSimilarTracks(final String mbid){

        // This returns an observable object that is a list with the results we wanted.
        // flatMap is a rxJava function that applies a function to the given element and returns
        // the result. It is used for async calls. The returned is also an Observable.
        // map does the same thing with flatMap but not for async. The returned is not Observable.
        return lastfmclient.similarTracksMbid(mbid)
                .flatMap(new Function<SimilarTracksData,
                        Flowable<? extends SimilarTracksData>>() {

                    @Override
                    public Flowable<? extends SimilarTracksData> apply(
                            SimilarTracksData similarTracksData) throws Exception {
                        return similarTracksData.filterErrors();
                    }
                }).map(new Function<SimilarTracksData, List<TrackSimilar>>() {

                    @Override
                    public List<TrackSimilar> apply(SimilarTracksData similarTracksData) throws Exception {
                        final ArrayList<TrackSimilar> similarTracks = new ArrayList<>();

                        for (TrackSimilar trackData : similarTracksData.getSimilarTracks().getTrack()){

                            ArrayList<Image> trackImages = new ArrayList<>();

                            for(Image imageData : trackData.getImage()){
                                Image image = new Image(
                                        imageData.getText(),
                                        imageData.getSize());
                                trackImages.add(image);
                            }
                            final TrackSimilar track = new TrackSimilar(
                                    trackData.getName(),
                                    trackData.getmArtist(),
                                    trackData.getUrl(),
                                    trackData.getListeners(),
                                    trackImages,
                                    trackData.getMbid(),
                                    trackData.getMatch(),
                                    trackData.getmPlaycount());
                            similarTracks.add(track);
                        }

                        return similarTracks;
                    }
                });
    }

    // We need this because sometimes the API returns a null "mbid" identifier value,
    // so we need to search using the artist and the track title.
    public Flowable<List<TrackSimilar>> getSimilarTracks(final String artist, final String track){

        // This returns an observable object that is a list with the results we wanted.
        // flatMap is a rxJava function that applies a function to the given element and returns
        // the result. It is used for async calls. The returned is also an Observable.
        // map does the same thing with flatMap but not for async. The returned is not Observable.
        return lastfmclient.similarTracks(artist,track)
                .flatMap(new Function<SimilarTracksData,
                        Flowable<? extends SimilarTracksData>>() {

                    @Override
                    public Flowable<? extends SimilarTracksData> apply(
                            SimilarTracksData similarTracksData) throws Exception {
                        return similarTracksData.filterErrors();
                    }
                }).map(new Function<SimilarTracksData, List<TrackSimilar>>() {

                    @Override
                    public List<TrackSimilar> apply(SimilarTracksData similarTracksData) throws Exception {
                        final ArrayList<TrackSimilar> similarTracks = new ArrayList<>();

                        for (TrackSimilar trackData : similarTracksData.getSimilarTracks().getTrack()){

                            ArrayList<Image> trackImages = new ArrayList<>();

                            for(Image imageData : trackData.getImage()){
                                Image image = new Image(
                                        imageData.getText(),
                                        imageData.getSize());
                                trackImages.add(image);
                            }
                            final TrackSimilar track = new TrackSimilar(
                                    trackData.getName(),
                                    trackData.getmArtist(),
                                    trackData.getUrl(),
                                    trackData.getListeners(),
                                    trackImages,
                                    trackData.getMbid(),
                                    trackData.getMatch(),
                                    trackData.getmPlaycount());
                            similarTracks.add(track);
                        }

                        return similarTracks;
                    }
                });
    }

    public Flowable<List<TopArtist>> getTopArtists(){

        return lastfmclient.topArtists()
                .flatMap(new Function<TopArtistsData,
                        Flowable<? extends TopArtistsData>>() {

                    @Override
                    public Flowable<? extends TopArtistsData> apply(
                            TopArtistsData topArtistsData) throws  Exception {
                        return topArtistsData.filterErrors();
                    }
                }).map(new Function<TopArtistsData, List<TopArtist>>() {

                    @Override
                    public List<TopArtist> apply(TopArtistsData topArtistsData) throws Exception {
                        final ArrayList<TopArtist> topArtists = new ArrayList<>();

                        for (TopArtist artistData : topArtistsData.getArtists().getArtist()){

                            ArrayList<Image> artistImages = new ArrayList<>();

                            for(Image imgData : artistData.getImage()){
                                Image image = new Image(
                                        imgData.getText(),
                                        imgData.getSize());
                                artistImages.add(image);
                            }
                            final TopArtist topArtist = new TopArtist(
                                    artistData.getMbid(),
                                    artistData.getName(),
                                    artistData.getUrl(),
                                    artistImages);
                            topArtists.add(topArtist);
                        }
                        return topArtists;
                    }
                });
    }

    public Flowable<List<TopTrack>> getTopTracks(){

        return lastfmclient.topTracks()
                .flatMap(new Function<TopTracksData,
                        Flowable<? extends TopTracksData>>() {

                    @Override
                    public Flowable<? extends TopTracksData> apply(
                            TopTracksData topTracksData) throws Exception {
                        return topTracksData.filterErrors();
                    }
                }).map(new Function<TopTracksData, List<TopTrack>>() {

                    @Override
                    public List<TopTrack> apply(TopTracksData topTracksData) throws Exception {
                        final ArrayList<TopTrack> topTracks = new ArrayList<>();

                        for (TopTrack trackData : topTracksData.getTracks().getTrack()){

                            ArrayList<Image> trackImages = new ArrayList<>();

                            for(Image imgData : trackData.getImage()){
                                Image image = new Image(
                                        imgData.getText(),
                                        imgData.getSize());
                                trackImages.add(image);
                            }

                            final TopTrack topTrack = new TopTrack(
                                    trackData.getName(),
                                    trackData.getmArtist(),
                                    trackData.getUrl(),
                                    trackData.getListeners(),
                                    trackImages,
                                    trackData.getMbid(),
                                    trackData.getmPlaycount());
                            topTracks.add(topTrack);
                        }
                        return topTracks;
                    }
                });
    }
}
