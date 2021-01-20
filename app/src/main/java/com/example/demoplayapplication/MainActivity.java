package com.example.demoplayapplication;

import android.annotation.SuppressLint;
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
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    private Button button;
    private Toolbar toolbar;
    private FrameLayout fragment_container;
    Fragment fragment;
    private static final int RECOVERY_REQUEST = 1;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);

        youTubePlayerView = findViewById(R.id.view);
        button = findViewById(R.id.button);
        fragment_container = findViewById(R.id.fragment_container);
//        loadFragment(new HomeFragment());
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(MainActivity.this);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youTubePlayerView.initialize(YouTubeConfig.API_KEY, MainActivity.this
                );

            }
        });

//        openFragment(HomeFragment.newInstance("", ""));

    }

//    private void openFragment(HomeFragment fragment) {
//
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.container, fragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }


//    private boolean loadFragment(Fragment fragments) {
//        if (fragments != null) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.fragment_container, fragments)
//                    .commit();
//            return true;
//        }
//
//        return false;
//    }

//    private FragmentManager getSupportFragmentManager() {
//        return null;
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        fragment = null;
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

        return false;
//        return loadFragment(fragment);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo("fhWaJi1Hsfo");
        }
//        List<String> playList = new ArrayList<>();
//        playList.add("PLCH0RJhrZ8JJa2ik8D_JdLhIj9dwppH2g");
//        playList.add("8Yt6EibGAa8");
//        playList.add("PLsyeobzWxl7oA8QOlMtQsRT_I7Rx2hoX4");
//        player.loadVideo(String.valueOf(playList));
//                player.loadVideo("PLsyeobzWxl7oA8QOlMtQsRT_I7Rx2hoX4");

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            Toast.makeText(this, "Player Error", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(YouTubeConfig.API_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubePlayerView;
    }


}