package uom.android.dev.LastFmJson;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class Image implements Parcelable{

    @SerializedName("#text")
    @Expose
    private String text;
    @SerializedName("size")
    @Expose
    private String size;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {

        @Override
        public Image createFromParcel(Parcel parcel) {
            return new Image(parcel);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public Image(String text, String size, Map<String, Object> additionalProperties) {
        this.text = text;
        this.size = size;
        this.additionalProperties = additionalProperties;
    }

    private Image(Parcel parcel) {
        this.text = parcel.readString();
        this.size = parcel.readString();
        this.additionalProperties = parcel.readHashMap(ClassLoader.getSystemClassLoader());
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.text);
        parcel.writeString(this.size);
        parcel.writeMap(this.additionalProperties);
    }
}