package uom.android.dev.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import uom.android.dev.LastFmJson.Image;
import uom.android.dev.LastFmJson.Track;
import uom.android.dev.LastFmJson.TopTrack;
import uom.android.dev.LastFmJson.TrackSimilar;
import uom.android.dev.R;

/**
 * Created by vasil on 22-Nov-17.
 */

public class SimilarTrackAdapter extends RecyclerView.Adapter<SimilarTrackAdapter.TrackViewHolder> {
    private final Context mContext;
    private List<TrackSimilar> trackList;
    private final OnItemClickListener listener;
    private final OnItemLongClickListener listenerlong;

    public class TrackViewHolder extends RecyclerView.ViewHolder {
        final ImageView track_image;
        final TextView track_name;
        final TextView track_match;
        final CardView item;

        public TrackViewHolder(View itemView) {
            super(itemView);
            track_image = (ImageView) itemView.findViewById(R.id.track_image_imageview);
            track_name = (TextView) itemView.findViewById(R.id.track_name_textview);
            track_match = (TextView) itemView.findViewById(R.id.track_match_textview);
            item = (CardView) itemView;
        }
    }

    public SimilarTrackAdapter(Context mContext, List<TrackSimilar> tracks,
                                OnItemClickListener listener, OnItemLongClickListener listenerlong) {
        this.mContext = mContext;
        this.trackList = tracks;
        this.listener = listener;
        this.listenerlong = listenerlong;
    }

    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.similar_track_list_item, parent, false);

        return new TrackViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TrackViewHolder holder, int position) {
        final TrackSimilar track = trackList.get(position);
        Uri image_uri = null;
        final Uri track_uri = (track.getUrl() != null)? Uri.parse(track.getUrl()): null ;
        
        List<Image> images = track.getImage();
        if (images.size() != 0){
            for(Image img : images){
                if(!img.getText().equals("") && img.getSize().equals("medium")){
                    image_uri = Uri.parse(img.getText());
                }
            }
        }

        Picasso.with(mContext).cancelRequest(holder.track_image);
        if (image_uri != null) {
            Picasso
                    .with(mContext)
                    .load(image_uri)
                    .into(holder.track_image);
        } else {
            holder.track_image.setImageResource(R.drawable.ic_info_white_24px);
        }
        
        holder.track_name.setText(String.format(
                mContext.getString(R.string.similar_track_info),
                track.getName(), track.getmArtist().getName()));

        holder.track_image.setContentDescription(track.getName());
        int percentage = Math.round(track.getMatch()*100);
        holder.track_match.setText(String.format(mContext.getString(R.string.similar_track_percentage), percentage));

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(track_uri);
            }
        });
        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listenerlong.onItemLongClick(track);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        if(trackList != null) {
            return trackList.size();
        }
        return 0;
    }


    public void setTrackList(List<TrackSimilar> tracks) {
        this.trackList = tracks;
        notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        void onItemClick(Uri track_uri);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Track track);
    }
}
