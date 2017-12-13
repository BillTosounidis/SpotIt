
package uom.android.dev.LastFmJson;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Artist implements Parcelable{

    @SerializedName("mbid")
    private String mMbid;
    @SerializedName("name")
    private String mName;
    @SerializedName("url")
    private String mUrl;

    public Artist(){

    }

    public Artist(String mMbid, String mName, String mUrl){
        this.mMbid = mMbid;
        this.mName = mName;
        this.mUrl = mUrl;
    }

    public Artist(Parcel parcel){
        mMbid = parcel.readString();
        mName = parcel.readString();
        mUrl = parcel.readString();
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

    public String getMbid() {
        return mMbid;
    }

    public void setMbid(String mbid) {
        mMbid = mbid;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mMbid);
        dest.writeString(this.mName);
        dest.writeString(this.mUrl);
    }
}
