package uom.android.dev;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import uom.android.dev.Activities.AboutActivity;
import uom.android.dev.Fragments.FavTracksFragment;
import uom.android.dev.Fragments.SearchBarFragment;
import uom.android.dev.Fragments.TopArtistsSliderFragment;
import uom.android.dev.Fragments.TopTracksFragment;

public class MainActivity extends AppCompatActivity{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private ShareActionProvider shareAction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mDrawerLayout = findViewById(R.id.main_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);

        NavigationView nvDrawer = findViewById(R.id.nv);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(nvDrawer);

        TopArtistsSliderFragment topArtistsSliderFragment = new TopArtistsSliderFragment();
        TopTracksFragment topTracksFragment = new TopTracksFragment();
        SearchBarFragment searchBarFragment = new SearchBarFragment();
        FavTracksFragment favTracksFragment = new FavTracksFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.sliderContainer, topArtistsSliderFragment).commit();
        fragmentManager.beginTransaction()
                .replace(R.id.topTracks, topTracksFragment).commit();
        fragmentManager.beginTransaction()
                .replace(R.id.searchBarContainer, searchBarFragment).commit();
        fragmentManager.beginTransaction()
                .replace(R.id.favTracks, favTracksFragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_actionbar_menu, menu);
        MenuItem item = menu.findItem(R.id.share);
        shareAction = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.share:
                Log.e(this.toString(), "SHARE!");
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBodyText = "Check out SpotIt! app on GitHub\nhttps://github.com/BillTosounidis/SpotIt";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Sharing Option"));
                return true;
            case R.id.about:
                return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (id){
                    case R.id.about:
                        Intent i = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(i);
                        break;
                }

                return true;
            }
        });
    }
}
