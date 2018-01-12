package uom.android.dev.Fragments;


import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uom.android.dev.MainActivity;
import uom.android.dev.R;
import uom.android.dev.SearchActivity;


public class SearchBarFragment extends Fragment {

    private SearchView search;
    public static final String EXTRA_MESSAGE = "uom.android.dev.MESSAGE";

    public SearchBarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_bar, container, false);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        search = rootView.findViewById(R.id.searchView);
        search.setOnClickListener(new SearchView.OnClickListener() {

            @Override
            public void onClick(View v) {
                search.setIconified(false);
            }
        });
        search.setSearchableInfo(searchManager.getSearchableInfo(
                new ComponentName(getActivity(), SearchActivity.class)));

        final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.setAction(Intent.ACTION_SEARCH);
                intent.putExtra(EXTRA_MESSAGE, query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return true;

            }
        };
        search.setOnQueryTextListener(queryTextListener);

        return rootView;
    }

}
