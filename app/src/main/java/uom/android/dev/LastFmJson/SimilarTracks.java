package uom.android.dev.LastFmJson;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SimilarTracks {

    @SerializedName("track")
    @Expose
    private List<TrackSimilar> track = null;

    public List<TrackSimilar> getTrack() {
        return track;
    }

    public void setTrack(List<TrackSimilar> track) {
        this.track = track;
    }

}