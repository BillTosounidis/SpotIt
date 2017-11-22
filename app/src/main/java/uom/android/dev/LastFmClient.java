package uom.android.dev;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by vasil on 22-Nov-17.
 */

public interface LastFmClient {

    @GET("http://ws.audioscrobbler.com/2.0/?method=track.search&track={songTitle}&api_key=d22eee316a280d357babf1f7b1e56205&format=json")
    Call<List<MatchSongSearch>> findSong(@Path("songTitle") String songTitle);
}
