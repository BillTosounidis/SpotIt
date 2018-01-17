package uom.android.dev.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "favorite_tracks",
        indices = @Index(value = {"track_name", "track_artist"}, unique = true))
public class FavTrack implements Parcelable {

    @ColumnInfo(name = "track_name")
    private String name;

    @ColumnInfo(name = "track_artist")
    private String artist;

    @ColumnInfo(name = "track_image")
    private String image;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "track_id")
    private int trackId;

    @ColumnInfo(name = "track_mbid")
    private String mbid;

    @ColumnInfo(name = "track_uri")
    private String uri;


    public FavTrack(String name, String artist, String image,
                    String mbid, String uri){
        this.name = name;
        this.artist = artist;
        this.image = image;
        this.mbid = mbid;
        this.uri = uri;
    }

    protected FavTrack(Parcel in) {
        name = in.readString();
        artist = in.readString();
        image = in.readString();
        trackId = in.readInt();
        mbid = in.readString();
        uri = in.readString();
    }

    public static final Creator<FavTrack> CREATOR = new Creator<FavTrack>() {
        @Override
        public FavTrack createFromParcel(Parcel in) {
            return new FavTrack(in);
        }

        @Override
        public FavTrack[] newArray(int size) {
            return new FavTrack[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.artist);
        dest.writeString(this.image);
        dest.writeInt(this.trackId);
        dest.writeString(this.mbid);
        dest.writeString(this.uri);
    }
}
