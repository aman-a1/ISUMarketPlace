package com.example.myapplication.Moderator.navMod;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.EditPro;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.nav.NotificationFragment;
import com.example.myapplication.nav.ProfileFragment;

public class Settings extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modsettings, container, false);
        return v;
    }

    /**
     * Exits the app on click
     * @param v
     */
    public void onClick(View v) {
        Intent myInt = new Intent(this.getContext(), MainActivity.class);
        startActivity(myInt);
    }
}
