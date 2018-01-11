package uom.android.dev.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import uom.android.dev.Adapters.TopTracksAdapter;
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
        resultsView = (RecyclerView) rootView.findViewById(R.id.topTracksRV);
        resultsView.setLayoutManager(layout);

        return rootView;
    }

    @Override
    public void onDestroy(){
        mCompositeSubscription.dispose();
        super.onDestroy();
    }

}
