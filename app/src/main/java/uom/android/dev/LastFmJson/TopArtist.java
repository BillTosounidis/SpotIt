package uom.android.dev.LastFmJson;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by v4570 on 10/01/18.
 */

public class TopArtist extends Artist{
    @SerializedName("image")
    @Expose
    private List<Image> image = new ArrayList<>();

    private String mImage;

    public TopArtist(){ super(); }

    public TopArtist(String mMbid, String mName, String mUrl, List<Image> image){
        super(mMbid, mName, mUrl);

        this.image = image;
        this.setmImage(this.image);
    }

    private TopArtist(Parcel in){
        super(in);

        in.readTypedList(image, Image.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.image);
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    public String getmImage(){
        return mImage;
    }

    private void setmImage(List<Image> image){
        for(Image img: image){
            if(img.getSize().equals("mega")
                    && !img.getText().equals("")){
                this.mImage = img.getText();
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TopArtist> CREATOR = new Creator<TopArtist>() {
        @Override
        public TopArtist createFromParcel(Parcel in) {
            return new TopArtist(in);
        }

        @Override
        public TopArtist[] newArray(int size) {
            return new TopArtist[size];
        }
    };
}
