package com.example.myapplication.Moderator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication.Moderator.navMod.Accounts;
import com.example.myapplication.Moderator.navMod.Items;
import com.example.myapplication.Moderator.navMod.Settings;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class home extends AppCompatActivity {

    /**
     * Creates and generates a default page when moderator user logs in
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modhome);
        BottomNavigationView btnView = findViewById(R.id.mod_nav);
        Fragment f = new Accounts();
        btnView.setOnNavigationItemReselectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, f).commit();
        Toast.makeText(this,"Alert: If you not from the administration, please exit!",Toast.LENGTH_SHORT);
    }

    /**
     * Shifts between fragments when clicked
     */
    private BottomNavigationView.OnNavigationItemReselectedListener navListener = new BottomNavigationView.OnNavigationItemReselectedListener() {
        @Override
        public void onNavigationItemReselected(@NonNull MenuItem item) {
            Fragment selectedFragment;
            Bundle b = new Bundle();
            switch (item.getItemId()){
                case R.id.nav_accounts:
                    selectedFragment = new Accounts();
                    break;
                case R.id.nav_Items:
                    selectedFragment = new Items();
                    break;
                case R.id.nav_Setting:
                    selectedFragment = new Settings();
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + item.getItemId());
            }
            selectedFragment.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,selectedFragment)
                    .commit();

        }

    };
}