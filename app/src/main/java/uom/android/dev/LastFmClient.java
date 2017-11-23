package uom.android.dev;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by vasil on 22-Nov-17.
 */

public interface LastFmClient {

    @GET("?method=track.search&format=json")
    Call<JsonResponse> getResults(@Query("track") String songTitle);
}
