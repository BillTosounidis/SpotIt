package uom.android.dev.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import uom.android.dev.Adapters.SimilarTrackAdapter;
import uom.android.dev.LastFMSearchService;
import uom.android.dev.LastFmJson.Track;
import uom.android.dev.LastFmJson.TrackSearch;
import uom.android.dev.LastFmJson.TrackSimilar;
import uom.android.dev.R;
import uom.android.dev.SimilarTracksActivity;


public class SimilarTracksFragment extends Fragment {

    private static final long TIMEOUT_SECONDS = 30;
    private static final String LOG_TAG = SimilarTracksFragment.class.getSimpleName();
    private CompositeDisposable mCompositeSubscription;
    private LastFMSearchService searchService;
    private List<TrackSimilar> similarTracks;
    private TrackSearch selectedTrack;
    RecyclerView resultsView;
    private SimilarTrackAdapter similarTrackAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_similar_tracks, container, false);
        selectedTrack = getArguments().getParcelable(SimilarTracksActivity.TRACK_TAG);
        mCompositeSubscription = new CompositeDisposable();

        LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        resultsView = (RecyclerView) rootView.findViewById(R.id.list_similar_tracks);
        resultsView.setHasFixedSize(true);
        resultsView.setLayoutManager(layout);
        resultsView.setVisibility(View.GONE);

        similarTrackAdapter = new SimilarTrackAdapter(getActivity(), similarTracks, new SimilarTrackAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Uri track_uri) {
                if (track_uri != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, track_uri);
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        Log.d(LOG_TAG, "Couldn't call " + track_uri.toString()
                                + ", no receiving apps installed!");
                    }
                }
            }
        }, new SimilarTrackAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(Track track) {
                Toast.makeText(getActivity(), "SAVED", Toast.LENGTH_SHORT).show();
            }
        });

        resultsView.setAdapter(similarTrackAdapter);
        loadSimilarTracks();
        return rootView;
    }

    @Override
    public void onDestroy(){
        mCompositeSubscription.dispose();
        super.onDestroy();
    }
    private void loadSimilarTracks() {
        String mbid = selectedTrack.getMbid();
        String artist = selectedTrack.getmArtist();
        String track = selectedTrack.getName();

        searchService = new LastFMSearchService();

        Flowable<List<TrackSimilar>> fetchDataObservable = null;
        if(!mbid.equals("")) {
            fetchDataObservable = searchService.getSimilarTracks(mbid);
        }
        else{
            fetchDataObservable = searchService.getSimilarTracks(artist, track);
        }


        mCompositeSubscription.add(fetchDataObservable
                .timeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<List<TrackSimilar>>() {

                    @Override
                    public void onNext(List<TrackSimilar> tracks) {

                        similarTracks = tracks;
                        similarTrackAdapter.setTrackList(tracks);
                    }

                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(getActivity(), "Download Error", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        resultsView.setVisibility(View.VISIBLE);
                    }
                })
        );
    }
}
