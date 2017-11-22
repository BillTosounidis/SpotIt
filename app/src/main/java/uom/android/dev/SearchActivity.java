package uom.android.dev;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listView = (ListView) findViewById(R.id.searchView);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://ws.audioscrobbler.com/2.0/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        LastFmClient client = retrofit.create(LastFmClient.class);
        Call<List<MatchSongSearch>> call = client.findSong("painkiller");

        call.enqueue(new Callback<List<MatchSongSearch>>() {
            @Override
            public void onResponse(Call<List<MatchSongSearch>> call, Response<List<MatchSongSearch>> response) {
                List<MatchSongSearch> songs = response.body();

                listView.setAdapter(new SearchResultsAdapter(SearchActivity.this,  songs));
            }

            @Override
            public void onFailure(Call<List<MatchSongSearch>> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
