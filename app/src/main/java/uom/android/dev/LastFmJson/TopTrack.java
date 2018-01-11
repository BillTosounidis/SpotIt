package uom.android.dev.LastFmJson;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by v4570 on 13/12/17.
 */

public class TopTrack extends Track{

    @SerializedName("artist")
    @Expose
    private Artist mArtist;
    @SerializedName("playcount")
    @Expose
    private String mPlaycount;

    private String mImage;

    public TopTrack(){
        super();
    }

    public TopTrack(String name, Artist mArtist, String url,
                    String listeners, List<Image> image, String mbid, String mPlaycount){
        super(name, url, listeners, image, mbid);
        this.mArtist = mArtist;
        this.mPlaycount = mPlaycount;
    }

    private TopTrack(Parcel in) {
        super(in);
        this.mArtist = in.readParcelable(Artist.class.getClassLoader());
        this.mPlaycount = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.mArtist, flags);
        dest.writeString(this.mPlaycount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TopTrack> CREATOR = new Creator<TopTrack>() {
        @Override
        public TopTrack createFromParcel(Parcel in) {
            return new TopTrack(in);
        }

        @Override
        public TopTrack[] newArray(int size) {
            return new TopTrack[size];
        }
    };

    private void setmImage(){
        for(Image img : this.getImage()){
            if(img.getSize().equals("large")
                    && !img.getText().equals("")){
                this.mImage = img.getText();
            }
        }
    }

    public Artist getmArtist(){
        return mArtist;
    }

    public void setmArtist(Artist artist){
        this.mArtist = artist;
    }

    public String getmPlaycount() {
        return mPlaycount;
    }

    public void setmPlaycount(String mPlaycount) {
        this.mPlaycount = mPlaycount;
    }
}
