package uom.android.dev.LastFmJson;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by vasil on 22-Nov-17.
 */

public interface LastFmClient {

    @GET("?method=track.search&format=json")
    Call<JsonResponse> searchTrack(@Query("track") String songTitle);

    @GET("?method=track.getsimilar&format=json")
    Call<JsonResponse> similarTracksMbid(@Query("mbid") String trackMbid);

    @GET("?method=trac.getsimilar&format=json")
    Call<JsonResponse> similarTracks(@Query("artist") String artistName,
                                     @Query("track") String songTitle);
}
