package uom.android.dev;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.w3c.dom.Attr;

import java.util.HashMap;
import java.util.Map;

public class Results {

    private String opensearchTotalResults;
    private String opensearchStartIndex;
    private String opensearchItemsPerPage;
    private TrackMatches trackmatches;
    private Attr attr;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public String getOpensearchTotalResults() {
        return opensearchTotalResults;
    }


    public void setOpensearchTotalResults(String opensearchTotalResults) {
        this.opensearchTotalResults = opensearchTotalResults;
    }

    public Results withOpensearchTotalResults(String opensearchTotalResults) {
        this.opensearchTotalResults = opensearchTotalResults;
        return this;
    }


    public String getOpensearchStartIndex() {
        return opensearchStartIndex;
    }


    public void setOpensearchStartIndex(String opensearchStartIndex) {
        this.opensearchStartIndex = opensearchStartIndex;
    }

    public Results withOpensearchStartIndex(String opensearchStartIndex) {
        this.opensearchStartIndex = opensearchStartIndex;
        return this;
    }


    public String getOpensearchItemsPerPage() {
        return opensearchItemsPerPage;
    }


    public void setOpensearchItemsPerPage(String opensearchItemsPerPage) {
        this.opensearchItemsPerPage = opensearchItemsPerPage;
    }

    public Results withOpensearchItemsPerPage(String opensearchItemsPerPage) {
        this.opensearchItemsPerPage = opensearchItemsPerPage;
        return this;
    }


    public TrackMatches getArtistmatches() {
        return trackmatches;
    }


    public void setArtistmatches(TrackMatches trackmatches) {
        this.trackmatches = trackmatches;
    }

    public Results withArtistmatches(TrackMatches trackmatches) {
        this.trackmatches = trackmatches;
        return this;
    }


    public Attr getAttr() {
        return attr;
    }


    public void setAttr(Attr attr) {
        this.attr = attr;
    }

    public Results withAttr(Attr attr) {
        this.attr = attr;
        return this;
    }

    /*@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }*/

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Results withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}