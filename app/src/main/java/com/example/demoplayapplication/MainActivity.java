package com.example.demoplayapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends YouTubeBaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener, YouTubePlayer.OnInitializedListener {

    private static final String TAG = "MainActivity";
    private YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    private Button button;
    private Toolbar toolbar;
    private FrameLayout fragment_container;
    private static final int RECOVERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=findViewById(R.id.toolbar);
        youTubePlayerView = findViewById(R.id.view);
        button =findViewById(R.id.button);
        fragment_container=findViewById(R.id.fragment_container);
        loadFragment(new HomeFragment());
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(MainActivity.this);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youTubePlayerView.initialize(YouTubeConfig.getKey(),onInitializedListener);
            }
        });


    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private FragmentManager getSupportFragmentManager() {
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(YouTubeConfig.getKey(), MainActivity.this);
        }    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubePlayerView;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }else if (id == R.id.notification){
            return true;
        }else if (id == R.id.search_icon){
            return true;
        }else if (id == R.id.action_user){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.navigation_home:
                toolbar.setTitle("Home");
                fragment = new HomeFragment();
                break;

            case R.id.navigation_dashboard:
                toolbar.setTitle("Explore");
                fragment = new ExploreFragment();
                break;

            case R.id.navigation_notifications:
                toolbar.setTitle("Subscriptions");
                fragment = new SubsciptionFragment();
                break;

            case R.id.navigation_profile:
                toolbar.setTitle("Library");
                fragment = new LibraryFragment();
                break;
        }

        return loadFragment(fragment);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        List<String> playList = new ArrayList<>();
        playList.add("PLCH0RJhrZ8JJa2ik8D_JdLhIj9dwppH2g");
        playList.add("8Yt6EibGAa8");
        playList.add("PLsyeobzWxl7oA8QOlMtQsRT_I7Rx2hoX4");
        youTubePlayer.loadVideo(String.valueOf(playList));
//                youTubePlayer.loadVideo("PLsyeobzWxl7oA8QOlMtQsRT_I7Rx2hoX4");
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(MainActivity.this, "Failed to Initialize Youtube Player", Toast.LENGTH_SHORT).show();

    }
}