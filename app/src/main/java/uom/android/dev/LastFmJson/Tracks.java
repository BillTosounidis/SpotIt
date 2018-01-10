package uom.android.dev.LastFmJson;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tracks {

@SerializedName("track")
@Expose
private List<TopTrack> track = null;


public List<TopTrack> getTrack() {
return track;
}

public void setTrack(List<TopTrack> track) {
this.track = track;
}

}