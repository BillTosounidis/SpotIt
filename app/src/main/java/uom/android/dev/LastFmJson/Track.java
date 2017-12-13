package uom.android.dev.LastFmJson;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Track implements Parcelable{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("listeners")
    @Expose
    private String listeners;

    @SerializedName("image")
    @Expose
    private List<Image> image = new ArrayList<>();
    @SerializedName("mbid")
    @Expose
    private String mbid;
    @Expose
    private String mediumImage;

    public Track(){
    }

    public Track(String name, String url, String listeners,
                 List<Image> image, String mbid){

        this.name = name;
        this.url = url;
        this.listeners = listeners;
        this.image = image;
        this.mbid = mbid;

        this.setMediumImage(image);
    }

    public Track (Parcel par){
        name = par.readString();
        url = par.readString();
        listeners = par.readString();
        par.readTypedList(image, Image.CREATOR);
        mbid = par.readString();
        mediumImage = par.readString();
    }

    // This is a static field that is used to regenerate object individually or as arrays.
    public static final Creator<Track> CREATOR = new Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getListeners() {
        return listeners;
    }

    public void setListeners(String listeners) {
        this.listeners = listeners;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public String getMediumImage(){ return this.mediumImage; }

    // This method is used to set the image for the track.
    private void setMediumImage(List<Image> image){
        for(Image img: image){
           if(img.getSize().equals("medium")
                   && !img.getText().equals("")){
               this.mediumImage = img.getText();
           }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeString(this.listeners);
        dest.writeTypedList(this.image);
        dest.writeString(this.mbid);
        dest.writeString(this.mediumImage);
    }
}