package com.example.myapplication.nav;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.EditPro;
import com.example.myapplication.R;
import com.example.myapplication.home.AddNewItem;
import com.example.myapplication.home.All;
import com.example.myapplication.home.Clothing;
import com.example.myapplication.home.Electronics;
import com.example.myapplication.home.Furniture;
import com.example.myapplication.home.SectionPageAdapter;
import com.example.myapplication.userDetails;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;


public class HomeFragment extends Fragment {

    userDetails user;
    FloatingActionButton AddNew;
    TabLayout tabLayout;

    /**
     * Dislpays all the products posted in the app
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home, container, false);
        user = (userDetails) getArguments().getSerializable("user");
        if (savedInstanceState == null) {
            tabLayout = v.findViewById(R.id.tabs);
            AddNew = v.findViewById(R.id.AddNewItem);
            AddNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddNewItemPage();
                }
            });

            TabItem clothingTab = (TabItem) v.findViewById(R.id.ClothingTab);
            if (clothingTab == null) {
                System.out.println("IT IS NULL!!");
            }

            if (tabLayout == null) {
                System.out.println("TAB LAYOUT IS NULL");
            }

            setupTabItem();
            Fragment f = new All();
            Bundle b = new Bundle();
            b.putSerializable("user",user);
            f.setArguments(b);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout, f);
            transaction.addToBackStack(null);

            transaction.commit();

        }

        return v;
    }

    private void setupTabItem() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentManager childFragMan = getChildFragmentManager();
                Bundle b = new Bundle();
                b.putSerializable("user",user);
                Fragment f = null;
                FragmentTransaction childFragTrans = childFragMan.beginTransaction();
                switch (tab.getPosition()) {
                    case 0:
                        System.out.println("here");
                        f = new All();
                        break;
                    case 1:
                        f = new Clothing();
                        break;
                    case 2:
                        f = new Electronics();
                        break;
                    case 3:
                        f = new Furniture();
                        break;
                    default:
                        f = new All();
                }
                f.setArguments(b);
                childFragTrans.replace(R.id.frameLayout, f);
                childFragTrans.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    /**
     * directs to the add new items page
     */
    private void AddNewItemPage()
    {
        Intent myInt = new Intent(getActivity() , AddNewItem.class);
        myInt.putExtra("user",(Serializable)user);
        startActivity(myInt);
    }

}
