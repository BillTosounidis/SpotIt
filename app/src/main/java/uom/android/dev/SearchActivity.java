package uom.android.dev;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uom.android.dev.LastFmJson.JsonResponse;
import uom.android.dev.LastFmJson.LastFmClient;
import uom.android.dev.LastFmJson.Results;
import uom.android.dev.LastFmJson.Track;
import uom.android.dev.LastFmJson.TrackMatches;

public class SearchActivity extends AppCompatActivity {

    private ListView listView;
    private String searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listView = (ListView) findViewById(R.id.resultsListView);

        Intent intent = getIntent();
        String what = intent.getAction();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            searchQuery = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
            searchForQuery();
        }
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

    protected void searchForQuery(){

        final String apikey = "d22eee316a280d357babf1f7b1e56205";

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        HttpUrl httpUrl = original.url();

                        HttpUrl newhttpUrl = httpUrl.newBuilder()
                                .addQueryParameter("api_key", apikey).build();

                        Request.Builder requestBuilder = original.newBuilder().url(newhttpUrl);

                        Request request = requestBuilder.build();

                        return chain.proceed(request);
                    }
                })
                .build();


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://ws.audioscrobbler.com/2.0/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        LastFmClient client = retrofit.create(LastFmClient.class);
        Call<JsonResponse> call = client.searchTrack(searchQuery);

        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(@NonNull Call<JsonResponse> call, @NonNull Response<JsonResponse> response) {

                if(response.isSuccessful()){
                    Results result = response.body().getResults();
                    assert result!=null;
                    TrackMatches trackMatches = result.getTrackmatches();
                    List<Track> tracks = trackMatches.getTrack();
                    SearchResultsAdapter searchAdapter = new SearchResultsAdapter(SearchActivity.this,  tracks);
                    listView.setAdapter(searchAdapter);
                }
                else {
                    Log.d("Response error", String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonResponse> call, @NonNull Throwable t) {
                Toast.makeText(SearchActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
