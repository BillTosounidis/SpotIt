package uom.android.dev.LastFmJson;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by v4570 on 13/12/17.
 */

public class TrackSearch extends Track{

    @SerializedName("artist")
    @Expose
    private String mArtist;
    @SerializedName("streamable")
    @Expose
    private String streamable;

    public TrackSearch(){
        super();
    }

    public TrackSearch(String name, String url,
                       String streamable, String listeners,
                       List<Image> image, String mbid,
                       String mArtist){
        super(name, url, listeners, image, mbid);
        this.mArtist = mArtist;
        this.streamable = streamable;
    }

    private TrackSearch(Parcel in) {
        super(in);
        this.mArtist = in.readString();
        this.streamable = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.mArtist);
        dest.writeString(this.streamable);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TrackSearch> CREATOR = new Creator<TrackSearch>() {
        @Override
        public TrackSearch createFromParcel(Parcel in) {
            return new TrackSearch(in);
        }

        @Override
        public TrackSearch[] newArray(int size) {
            return new TrackSearch[size];
        }
    };

    public String getmArtist(){
        return mArtist;
    }

    public void setmArtist(String artist){
        this.mArtist = artist;
    }

    public String getStreamable(){
        return streamable;
    }

    public void setStreamable(String streamable){
        this.mArtist = streamable;
    }

}
