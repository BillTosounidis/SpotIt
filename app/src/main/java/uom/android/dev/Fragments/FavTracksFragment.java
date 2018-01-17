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

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
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
    private CompositeDisposable mCompositeSubscription;

    public FavTracksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fav_tracks, container, false);

        mCompositeSubscription = new CompositeDisposable();

        LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        layout.setOrientation(LinearLayoutManager.HORIZONTAL);
        resultsView = rootView.findViewById(R.id.favTracksRV);
        resultsView.setLayoutManager(layout);
        resultsView.setVisibility(View.GONE);

        favTracksAdapter = new FavTracksAdapter(getActivity(), favTracks,
                new FavTracksAdapter.OnItemLongClickListener() {
                    @Override
                    public void onItemLongClick(FavTrack track) {
                        LocalDatabaseManager.getInstance(getActivity()).deleteFavTrack(track);
                    }
                }, new FavTracksAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Uri track_uri) {
                if (track_uri != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, track_uri);
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        Log.d(FavTracksFragment.class.getSimpleName(), "Couldn't call " + track_uri.toString()
                                + ", no receiving apps installed!");
                    }
                }
            }
        });
        resultsView.setAdapter(favTracksAdapter);
        return rootView;
    }

    @Override
    public void onStart(){
        super.onStart();
        mCompositeSubscription.add(LocalDatabaseManager.getInstance(getActivity()).getDb().favTrackDao()
                .getFavoriteTracks().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<FavTrack>>() {
                    @Override
                    public void accept(List<FavTrack> favTracks) throws Exception {
                        favTracksAdapter.setFavTracks(favTracks);
                        favTracksAdapter.notifyDataSetChanged();
                        resultsView.setVisibility(View.VISIBLE);
                    }
                }));
    }

    @Override
    public void onStop(){
        super.onStop();

        mCompositeSubscription.clear();
    }

    @Override
    public void onDestroy(){
        mCompositeSubscription.dispose();
        super.onDestroy();
    }

}
