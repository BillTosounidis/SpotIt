package uom.android.dev;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import uom.android.dev.Fragments.SearchBarFragment;
import uom.android.dev.Fragments.TopArtistsSliderFragment;
import uom.android.dev.Fragments.TopTracksFragment;

public class MainActivity extends AppCompatActivity{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);

        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nv);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(nvDrawer);

        TopArtistsSliderFragment topArtistsSliderFragment = new TopArtistsSliderFragment();
        TopTracksFragment topTracksFragment = new TopTracksFragment();
        SearchBarFragment searchBarFragment = new SearchBarFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.sliderContainer, topArtistsSliderFragment).commit();
        fragmentManager.beginTransaction()
                .replace(R.id.topTracks, topTracksFragment).commit();
        fragmentManager.beginTransaction()
                .replace(R.id.searchBarContainer, searchBarFragment).commit();

    }
    /*public void selectItemDrawer(MenuItem menuItem){
        Fragment myFragment = null;
        Class fragmentClass = null;
        switch (menuItem.getItemId()){
            case R.id.search:
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(i);
                break;
        }
       *//* try{
            myFragment = (Fragment) fragmentClass.newInstance();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.drawer, myFragment).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawerLayout.closeDrawers();*//*
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (id){
                    case R.id.search:
                        Intent i = new Intent(MainActivity.this, SearchActivity.class);
                        startActivity(i);
                        break;
                }

                return false;
            }
        });
    }
}
