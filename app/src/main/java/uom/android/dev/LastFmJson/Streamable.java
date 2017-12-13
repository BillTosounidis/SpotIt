
package uom.android.dev.LastFmJson;

import com.google.gson.annotations.SerializedName;


public class Streamable {

    @SerializedName("fulltrack")
    private String mFulltrack;
    @SerializedName("#text")
    private String mText;

    public String getFulltrack() {
        return mFulltrack;
    }

    public void setFulltrack(String fulltrack) {
        mFulltrack = fulltrack;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

}
