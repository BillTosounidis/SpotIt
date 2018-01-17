package uom.android.dev.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import uom.android.dev.LastFmJson.Image;
import uom.android.dev.LastFmJson.TrackSearch;
import uom.android.dev.R;


public class TrackResultsAdapter extends RecyclerView.Adapter<TrackResultsAdapter.TrackViewHolder> {

    private final Context context;
    private List<TrackSearch> values;
    private final OnItemClickListener listener;
    private final OnLongClickListener longClickListener;

    public class TrackViewHolder extends RecyclerView.ViewHolder {
        final ImageView track_image;
        final TextView track_title;
        final TextView total_listeners;
        final TextView track_artist;
        final CardView element;

        public TrackViewHolder(View itemView){
            super(itemView);
            track_image = (ImageView) itemView.findViewById(R.id.track_image_imageview);
            track_title = (TextView) itemView.findViewById(R.id.track_title_textview);
            total_listeners = (TextView) itemView.findViewById(R.id.track_listeners_textview);
            track_artist = (TextView) itemView.findViewById(R.id.track_artist_textview);
            element = (CardView) itemView;
        }
    }

    public TrackResultsAdapter(Context context, List<TrackSearch> values, OnItemClickListener listener, OnLongClickListener longClickListener){

        this.context = context;
        this.values = values;
        this.listener = listener;
        this.longClickListener = longClickListener;
    }

    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.track_list_item, parent, false);

        return new TrackViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TrackViewHolder holder, int position){
        final TrackSearch track = values.get(position);
        Uri image_uri = null;

        List<Image> images = track.getImage();
        if (images.size() != 0 && !images.get(3).getText().equals("")){
            image_uri = Uri.parse(images.get(3).getText());
        }

        Picasso.with(context).cancelRequest(holder.track_image);
        if (image_uri != null){
            Picasso
                    .with(context)
                    .load(image_uri)
                    .into(holder.track_image);
        } else {
            holder.track_image.setImageResource(R.drawable.ic_info_white_24px);
        }

        holder.track_title.setText(track.getName());
        holder.track_artist.setText(track.getmArtist());
        holder.track_image.setContentDescription(track.getName());

        holder.total_listeners.setText(String.format(context.getString(R.string.listeners_info),
                track.getListeners()));


        holder.element.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                listener.onItemClick(track);
            }
        });

        holder.element.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickListener.onLongItemClick(track);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        if(values != null){
            return values.size();
        }
        return 0;
    }
    public void setTrackList(List<TrackSearch> tracks){
        this.values = tracks;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(TrackSearch track);
    }

    public interface OnLongClickListener {
        void onLongItemClick(TrackSearch track);
    }
}
