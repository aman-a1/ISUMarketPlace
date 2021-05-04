package com.example.myapplication.nav;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

//import com.example.myapplication.DeleteAccount;
import com.example.myapplication.DeleteAccount;
import com.example.myapplication.Display;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Register.RegisterPage;
import com.example.myapplication.Security;
import com.example.myapplication.userDetails;

import java.io.Serializable;

/**
 * @author amana1
 */
public class SettingsFragment extends Fragment {


    ImageView display, signOut, profile, delete, security;
    userDetails d;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.settings, container, false);

        d = (userDetails) getArguments().getSerializable("user");
        display = v.findViewById(R.id.display);
        signOut = v.findViewById(R.id.signout);
        delete = v.findViewById(R.id.delete);
        security = v.findViewById(R.id.security);

        security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePassword();
            }
        });

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayPage();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteAcc();
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainPage();
            }
        });
        return v;
    }

    /**
     * directs to the login page
     */
    private void MainPage() {
        Intent intent = new Intent(getActivity(),MainActivity.class);
        startActivity(intent);
    }
    /**
     * directs to the display page
     */

    private void DisplayPage() {
        Intent intent = new Intent(getActivity(), Display.class);
       startActivity(intent);
    }
    /**
     * directs to the delete acc. page
     */
    private void DeleteAcc() {
        Intent intent = new Intent(getActivity(), DeleteAccount.class);
        intent.putExtra("user", (Serializable) d);
       startActivity(intent);
    }

    /**
     * directs to the change password page
     */
    private void ChangePassword(){
        Intent intent = new Intent(getActivity(), Security.class);
        intent.putExtra("user", (Serializable) d);
        startActivity(intent);
    }
}
