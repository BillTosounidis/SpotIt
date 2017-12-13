
package uom.android.dev.LastFmJson;

import com.google.gson.annotations.SerializedName;


public class SimilarResults {

    @SerializedName("similartracks")
    private SimilarTracks mSimilartracks;

    public SimilarTracks getSimilartracks() {
        return mSimilartracks;
    }

    public void setSimilartracks(SimilarTracks similartracks) {
        mSimilartracks = similartracks;
    }

}
