package uom.android.dev.LastFmJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.reactivex.Flowable;

public class TopTracksData {

@SerializedName("tracks")
@Expose
private Tracks tracks;

    public Flowable<? extends TopTracksData> filterErrors(){
        // This is where we check for errors in the response. (if the response has tracks)
        if (tracks.getTrack() != null){
            return Flowable.just(this);
        }
        return null;
    }

public Tracks getTracks() {
return tracks;
}

public void setTracks(Tracks tracks) {
this.tracks = tracks;
}

}