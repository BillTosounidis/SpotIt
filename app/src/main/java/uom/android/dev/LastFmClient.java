package uom.android.dev;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by vasil on 22-Nov-17.
 */

public interface LastFmClient {

    @GET("?method=track.search&format=json")
    Call<Results> getResults(@Query("track") String songTitle);
}
