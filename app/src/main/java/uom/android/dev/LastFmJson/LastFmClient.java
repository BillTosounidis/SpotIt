package uom.android.dev.LastFmJson;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import uom.android.dev.BuildConfig;

/**
 * Created by vasil on 22-Nov-17.
 */

public interface LastFmClient {

    @GET("?method=track.search&format=json&api_key=" + BuildConfig.LAST_FM_API_KEY)
    Flowable<ResultsData> searchTrack(@Query("track") String songTitle);

    @GET("?method=track.getsimilar&format=json&api_key=" + BuildConfig.LAST_FM_API_KEY)
    Flowable<SimilarTracksData> similarTracksMbid(@Query("mbid") String trackMbid);

    @GET("?method=trac.getsimilar&format=json&api_key=" + BuildConfig.LAST_FM_API_KEY)
    Flowable<SimilarTracksData> similarTracks(@Query("artist") String artistName,
                                              @Query("track") String songTitle);
}
