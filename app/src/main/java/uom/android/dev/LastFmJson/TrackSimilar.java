package uom.android.dev.LastFmJson;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by v4570 on 13/12/17.
 */

public class TrackSimilar extends Track{

    @SerializedName("artist")
    @Expose
    private Artist mArtist;
    @SerializedName("match")
    @Expose
    private Float match;
    @SerializedName("streamable")
    @Expose
    private Streamable streamable;

    @SerializedName("playcount")
    @Expose
    private Integer mPlaycount;

    public TrackSimilar(){
        super();
    }

    public TrackSimilar(String name, Artist mArtist, String url,
                        Streamable streamable, String listeners,
                        List<Image> image, String mbid,
                        Float match, Integer mPlaycount){
        super(name, url, listeners, image, mbid);
        this.mArtist = mArtist;
        this.match = match;
        this.streamable = streamable;
        this.mPlaycount = mPlaycount;
    }

    private TrackSimilar(Parcel in) {
        super(in);
        this.mArtist = in.readParcelable(Artist.class.getClassLoader());
        this.match = in.readFloat();
        this.mPlaycount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.mArtist, flags);
        dest.writeFloat(this.match);
        dest.writeInt(this.mPlaycount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TrackSimilar> CREATOR = new Creator<TrackSimilar>() {
        @Override
        public TrackSimilar createFromParcel(Parcel in) {
            return new TrackSimilar(in);
        }

        @Override
        public TrackSimilar[] newArray(int size) {
            return new TrackSimilar[size];
        }
    };

    public Artist getmArtist(){
        return mArtist;
    }

    public void setmArtist(Artist artist){
        this.mArtist = artist;
    }

    public Float getMatch(){
        return match;
    }

    public void setMatch(Float match){ this.match = match; }

    public Streamable getStreamable() {
        return streamable;
    }

    public void setStreamable(Streamable streamable) {
        this.streamable = streamable;
    }

    public Integer getmPlaycount() {
        return mPlaycount;
    }

    public void setmPlaycount(Integer mPlaycount) {
        this.mPlaycount = mPlaycount;
    }
}
