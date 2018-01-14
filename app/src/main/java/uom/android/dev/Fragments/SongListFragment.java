package uom.android.dev.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import uom.android.dev.Adapters.TrackResultsAdapter;
import uom.android.dev.LastFmJson.TrackSearch;
import uom.android.dev.R;

/**
 * Created by vasil on 16-Nov-17.
 */

public class SongListFragment extends Fragment {

    private TrackResultsAdapter trackAdapter;
    private static final String TRACK_KEY = "tracks";
    private List<TrackSearch> tracks;
    private OnFragmentInteractionListener listener;

    public SongListFragment(){
        // We need this empty constructor.
    }

    public static SongListFragment newInstance(List<TrackSearch> tracks){
        SongListFragment fragment = new SongListFragment();
        Bundle args = new Bundle();

        args.putParcelableArrayList(TRACK_KEY, (ArrayList<? extends Parcelable>) tracks);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            tracks = getArguments().getParcelableArrayList(TRACK_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_songlist, container, false);

        LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView resultsView = (RecyclerView) rootView.findViewById(R.id.list_search_results);
        resultsView.setHasFixedSize(true);
        resultsView.setLayoutManager(layout);

        trackAdapter = new TrackResultsAdapter(getActivity(), tracks, new TrackResultsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TrackSearch track) {
                if (track != null){
                    listener = ((OnFragmentInteractionListener) getActivity());
                    listener.onItemSelected(track);
                }
            }
        });



        resultsView.setAdapter(trackAdapter);


        return rootView;
    }

    public void showPopUp(View v){
        trackAdapter.showPopUp(v);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener) context;
        }
        else{
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        listener = null;
    }

    public interface OnFragmentInteractionListener {
        void onItemSelected(TrackSearch track);
    }
}
