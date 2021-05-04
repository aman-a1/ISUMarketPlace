package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.Moderator.navMod.Settings;
import com.example.myapplication.nav.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;

/**
 * @author amana1
 */
public class AfterLogin extends AppCompatActivity implements Serializable {
    String name;
    String Pass;
    userDetails d;
    Bundle b = new Bundle();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        Intent i = getIntent();
        d = (userDetails)i.getSerializableExtra("user");
        BottomNavigationView btnView = findViewById(R.id.bottom_navigation);
        btnView.setOnNavigationItemReselectedListener(navListener);
        b.putSerializable("user",d);
        Fragment selectedFrag  = new HomeFragment();

        selectedFrag.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFrag).commit();
    }

    /**
     * controls the fragment view on events of click
     */
    private BottomNavigationView.OnNavigationItemReselectedListener navListener = new BottomNavigationView.OnNavigationItemReselectedListener() {
        @Override
        public void onNavigationItemReselected(@NonNull MenuItem item) {
            Fragment selectedFragment;

            switch (item.getItemId()){
                case R.id.nav_home:
                    selectedFragment = new HomeFragment();
                    break;

                case R.id.nav_notifications:
                    selectedFragment = new NotificationFragment();
                    break;
                case R.id.nav_profile:
                    selectedFragment = new ProfileFragment();
                    break;
                case R.id.nav_chats:
                    selectedFragment = new ChatsFragment();
                    break;
                case R.id.nav_settings:
                    selectedFragment = new SettingsFragment();
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + item.getItemId());
            }
            selectedFragment.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment)
                    .commit();

        }

    };
}