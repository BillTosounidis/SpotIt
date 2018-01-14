package uom.android.dev.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import uom.android.dev.LastFmJson.Image;
import uom.android.dev.LastFmJson.TopTrack;
import uom.android.dev.R;

/**
 * Created by v4570 on 10/01/18.
 */

public class TopTracksAdapter extends RecyclerView.Adapter<TopTracksAdapter.TopTrackViewHolder>{
    private final Context mContext;
    private List<TopTrack> trackList;

    public class TopTrackViewHolder extends RecyclerView.ViewHolder{
        final ImageView track_image;
        final TextView track_title;
        final TextView track_artist;

        public TopTrackViewHolder(View itemView) {
            super(itemView);
            track_image = (ImageView) itemView.findViewById(R.id.top_image_imageview);
            track_title = (TextView) itemView.findViewById(R.id.top_track_textview);
            track_artist = (TextView) itemView.findViewById(R.id.top_track_artist_textview);
        }
    }

    public TopTracksAdapter(Context mContext, List<TopTrack> trackList){

        this.mContext = mContext;
        this.trackList = trackList;
    }

    @Override
    public TopTrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.top_layout, parent, false);

        return new TopTrackViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TopTrackViewHolder holder, int position) {
        final TopTrack track = trackList.get(position);
        Uri image_uri = null;
        final Uri track_uri = (track.getUrl() != null)? Uri.parse(track.getUrl()): null;

        List<Image> images = track.getImage();
        if (images.size() != 0){
            for(Image img : images){
                if(!img.getText().equals("") && img.getSize().equals("large")){
                    image_uri = Uri.parse(img.getText());
                }
            }
        }

        Picasso.with(mContext).cancelRequest(holder.track_image);
        if (image_uri != null){
            Picasso
                    .with(mContext)
                    .load(image_uri)
                    .into(holder.track_image);
        } else{
            holder.track_image.setImageResource(R.drawable.ic_info_white_24px);
        }

        holder.track_title.setText(track.getName());
        holder.track_artist.setText(track.getmArtist().getName());
        holder.track_image.setContentDescription(track.getName());
    }

    public void setTrackList(List<TopTrack> trackList){
        this.trackList = trackList;
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }
}
