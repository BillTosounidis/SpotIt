
package uom.android.dev.LastFmJson;

import com.google.gson.annotations.SerializedName;


public class Attr {

    @SerializedName("artist")
    private String mArtist;

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

}
