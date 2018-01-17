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

import uom.android.dev.Database.FavTrack;
import uom.android.dev.R;


public class FavTracksAdapter extends RecyclerView.Adapter<FavTracksAdapter.FavTrackViewHolder>{

    private final Context mContext;
    private List<FavTrack> favTracks;

    @Override
    public FavTrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fav_layout, parent, false);

        return new FavTrackViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FavTrackViewHolder holder, int position) {
        final FavTrack track = favTracks.get(position);
        Uri image_uri = null;

        image_uri = Uri.parse(track.getImage());

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
        holder.track_artist.setText(track.getArtist());
        holder.track_artist.setText(track.getArtist());
        holder.track_image.setContentDescription(track.getName());
    }

    @Override
    public int getItemCount() {
        return favTracks.size();
    }

    public class FavTrackViewHolder extends RecyclerView.ViewHolder{
        final ImageView track_image;
        final TextView track_title;
        final TextView track_artist;

        public FavTrackViewHolder(View itemView){
            super(itemView);
            track_image = (ImageView) itemView.findViewById(R.id.fav_image_imageview);
            track_title = (TextView) itemView.findViewById(R.id.fav_track_artist_textview);
            track_artist = (TextView) itemView.findViewById(R.id.fav_track_textview);
        }
    }

    public FavTracksAdapter(Context context, List<FavTrack> favTrackList){

        this.mContext = context;
        this.favTracks = favTrackList;
    }

    public void setFavTracks(List<FavTrack> favTracks){
        this.favTracks = favTracks;
    }
}
