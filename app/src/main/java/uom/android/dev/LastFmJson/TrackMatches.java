package uom.android.dev.LastFmJson;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrackMatches {

    @SerializedName("track")
    @Expose
    private List<TrackSearch> track = null;

    public List<TrackSearch> getTrack() {
        return track;
    }

    public void setTrack(List<TrackSearch> track) {
        this.track = track;
    }

}