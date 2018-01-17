package uom.android.dev.Activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import uom.android.dev.Fragments.SimilarTracksFragment;
import uom.android.dev.LastFmJson.Image;
import uom.android.dev.LastFmJson.TrackSearch;
import uom.android.dev.R;

public class SimilarTracksActivity extends AppCompatActivity{
    public static final String TRACK_ID = "mbid";
    public static final String TRACK_TAG = "selected";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_tracks);

        Bundle bundle = getIntent().getExtras();
        TrackSearch selected_track = bundle.getParcelable(TRACK_ID);

        Toolbar toolbar = findViewById(R.id.similar_tracks_toolbar);
        if(selected_track != null &&
                !selected_track.getName().equals("")) toolbar.setTitle(selected_track.getName());
        setSupportActionBar(toolbar);




        ImageView trackImage = findViewById(R.id.track_image_imageview);

        if(!selected_track.getImage().isEmpty()){
            List<Image> images = selected_track.getImage();
            Image imageL = images.get(3);
            if(imageL != null && !imageL.getText().equals("")){
                Uri imageLUri = Uri.parse(imageL.getText());
                Picasso.with(this).cancelRequest(trackImage);
                if (imageLUri != null){
                    Picasso
                            .with(this)
                            .load(imageLUri)
                            .into(trackImage);
                }
                else{
                    trackImage.setImageResource(R.drawable.ic_info_white_24px);
                }
            }
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle b = new Bundle();
        b.putParcelable(TRACK_TAG, selected_track);
        SimilarTracksFragment similarTracksFragment = new SimilarTracksFragment();
        similarTracksFragment.setArguments(b);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.similar_tracks_fragment_container, similarTracksFragment)
                    .commit();
        }
    }
}
