package uom.android.dev.LastFmJson;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Artists {

@SerializedName("artist")
@Expose
private List<TopArtist> artist = null;

public List<TopArtist> getArtist() {
return artist;
}

public void setArtist(List<TopArtist> artist) {
this.artist = artist;
}

}