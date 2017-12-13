package uom.android.dev;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import uom.android.dev.LastFmJson.Image;
import uom.android.dev.LastFmJson.ResultsData;
import uom.android.dev.LastFmJson.TrackSearch;

public class SearchActivity extends AppCompatActivity implements SongListFragment.OnFragmentInteractionListener{

    private String searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        searchQuery = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        searchTracks(searchQuery);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    public void searchTracks(String query){
        final LastFMSearchService lastfmService = new LastFMSearchService();

        lastfmService
                .lastfmclient.searchTrack(query)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultsData>() {

                    SongListFragment songListFragment;

                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(ResultsData resultsData) {
                        ArrayList<TrackSearch> tracks = new ArrayList<>();

                        for (TrackSearch trck: resultsData.getResults().getTrackmatches().getTrack()){

                            ArrayList<Image> images = new ArrayList<>();
                            for(Image img : trck.getImage()){
                                Image image = new Image(
                                        img.getText(),
                                        img.getSize());
                                images.add(image);
                            }

                            TrackSearch track = new TrackSearch(
                                    trck.getName(),
                                    trck.getUrl(),
                                    trck.getStreamable(),
                                    trck.getListeners(),
                                    images,
                                    trck.getMbid(),
                                    trck.getmArtist());
                            tracks.add(track);
                        }

                        songListFragment = SongListFragment.newInstance(tracks);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                "API Call Error", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, songListFragment, "SFTAG")
                                .commit();
                    }
                });
    }

    @Override
    public void onItemSelected(TrackSearch track) {
        Intent intent = new Intent(this, SimilarTracksActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("mbid", track);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
