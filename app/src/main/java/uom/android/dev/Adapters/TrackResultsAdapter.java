package uom.android.dev.Adapters;

import android.content.Context;
import android.net.Uri;
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
import uom.android.dev.LastFmJson.TrackSearch;
import uom.android.dev.R;

/**
 * Created by vasil on 22-Nov-17.
 */

public class TrackResultsAdapter extends RecyclerView.Adapter<TrackResultsAdapter.TrackViewHolder> {

    private final Context context;
    private List<TrackSearch> values;
    private final OnItemClickListener listener;

    public class TrackViewHolder extends RecyclerView.ViewHolder {
        final ImageView track_image;
        final TextView track_title;
        final TextView total_listeners;
        final LinearLayout element;

        public TrackViewHolder(View itemView){
            super(itemView);
            track_image = (ImageView) itemView.findViewById(R.id.track_image_imageview);
            track_title = (TextView) itemView.findViewById(R.id.track_title_textview);
            total_listeners = (TextView) itemView.findViewById(R.id.track_listeners_textview);
            element = (LinearLayout) itemView;
        }
    }

    public TrackResultsAdapter(Context context, List<TrackSearch> values, OnItemClickListener listener){

        this.context = context;
        this.values = values;
        this.listener = listener;
    }

    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_pagination, parent, false);

        return new TrackViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TrackViewHolder holder, int position){
        final TrackSearch track = values.get(position);
        Uri image_uri = null;

        List<Image> images = track.getImage();
        if (images.size() != 0 && !images.get(1).getText().equals("")){
            image_uri = Uri.parse(images.get(1).getText());
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
        holder.track_image.setContentDescription(track.getName());

        holder.total_listeners.setText(track.getListeners());
        holder.element.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                listener.onItemClick(track);
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
}