package uom.android.dev.LastFmJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.reactivex.Flowable;

public class TopArtistsData {

@SerializedName("artists")
@Expose
private Artists artists;

    public Flowable<? extends TopArtistsData> filterErrors(){
        // This is where we check for errors in the response. (if the response has tracks)
        if (artists.getArtist() != null){
            return Flowable.just(this);
        }
        return null;
    }

public Artists getArtists() {
return artists;
}

public void setArtists(Artists artists) {
this.artists = artists;
}

}