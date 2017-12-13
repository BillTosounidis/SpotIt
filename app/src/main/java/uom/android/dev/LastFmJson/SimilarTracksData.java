package uom.android.dev.LastFmJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import io.reactivex.Flowable;

/**
 * Created by v4570 on 12/12/17.
 */

public class SimilarTracksData {

    @SerializedName("similartracks")
    @Expose
    private SimilarTracks similartracks;

    public Flowable<? extends SimilarTracksData> filterErrors(){
        // This is where we check for errors in the response. (if the response has tracks)
        if (similartracks.getTrack() != null){
            return Flowable.just(this);
        }
        return null;
    }

    public SimilarTracks getSimilarTracks(){ return similartracks; }
}
