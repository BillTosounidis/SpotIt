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
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import uom.android.dev.Adapters.TopTracksAdapter;
import uom.android.dev.Database.LocalDatabaseManager;
import uom.android.dev.LastFMSearchService;
import uom.android.dev.LastFmJson.TopTrack;
import uom.android.dev.R;


public class TopTracksFragment extends Fragment {

    private List<TopTrack> topTracks;
    private CompositeDisposable mCompositeSubscription;
    private LastFMSearchService searchService;
    private static final long TIMEOUT_SECONDS = 30;
    private RecyclerView resultsView;
    private TopTracksAdapter topTracksAdapter;

    public TopTracksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_top_tracks, container, false);
        mCompositeSubscription = new CompositeDisposable();

        LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        layout.setOrientation(LinearLayoutManager.HORIZONTAL);
        resultsView = rootView.findViewById(R.id.topTracksRV);
        resultsView.setLayoutManager(layout);
        resultsView.setVisibility(View.GONE);
        topTracksAdapter = new TopTracksAdapter(getActivity(), topTracks, new TopTracksAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Uri track_uri) {
                if (track_uri != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, track_uri);
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        Log.d(TopTracksFragment.class.getSimpleName(), "Couldn't call " + track_uri.toString()
                                + ", no receiving apps installed!");
                    }
                }
            }
        }, new TopTracksAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(TopTrack track) {
                LocalDatabaseManager.getInstance(getActivity()).addFavTrackTop(track);
            }
        });

        resultsView.setAdapter(topTracksAdapter);
        loadTopTracks();
        return rootView;
    }

    @Override
    public void onDestroy(){
        mCompositeSubscription.dispose();
        super.onDestroy();
    }

    private void loadTopTracks() {

        searchService = new LastFMSearchService();
        Flowable<List<TopTrack>> fetchDataObservable = searchService.getTopTracks();

        mCompositeSubscription.add(fetchDataObservable
                .timeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<List<TopTrack>>() {

                    @Override
                    public void onNext(List<TopTrack> mTracks) {

                        topTracks = mTracks;
                        topTracksAdapter.setTrackList(topTracks);
                    }

                    @Override
                    public void onError(Throwable t) {

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
