package uom.android.dev.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import uom.android.dev.Adapters.FavTracksAdapter;
import uom.android.dev.Database.FavTrack;
import uom.android.dev.Database.LocalDatabaseManager;
import uom.android.dev.R;


public class FavTracksFragment extends Fragment {

    private List<FavTrack> favTracks;
    private FavTracksAdapter favTracksAdapter;
    private RecyclerView resultsView;

    public FavTracksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fav_tracks, container, false);

        LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        layout.setOrientation(LinearLayoutManager.HORIZONTAL);
        resultsView = (RecyclerView) rootView.findViewById(R.id.favTracksRV);
        resultsView.setLayoutManager(layout);
        resultsView.setVisibility(View.GONE);

        favTracksAdapter = new FavTracksAdapter(getActivity(), favTracks);
        resultsView.setAdapter(favTracksAdapter);
        loadFavTracks();
        return rootView;
    }

    private void loadFavTracks(){
        LocalDatabaseManager.getInstance(getActivity()).getDb().favTrackDao()
                .getFavoriteTracks().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<FavTrack>>() {
                    @Override
                    public void accept(List<FavTrack> favTracks) throws Exception {
                        handleResponse(favTracks);
                        resultsView.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void handleResponse(List<FavTrack> favTracks){
        favTracksAdapter.setFavTracks(favTracks);
    }

}
