package com.example.myapplication.home;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionPageAdapter extends FragmentPagerAdapter {
    private final Context mContext;

    public SectionPageAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    //creates tab fragments for main categories
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new All();
                break;
            case 1:
                fragment = new Clothing();
                break;
            case 2:
                fragment = new Electronics();
                break;
            case 3:
                fragment = new Furniture();
                break;
        }
        return fragment;
    }


    //names tabs
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "All";
            case 1:
                return "Clothing";
            case 2:
                return "Electronics";
            case 3:
                return "Furniture";
        }
        return null;
    }

    //tab count
    @Override
    public int getCount() {
        return 4;
    }
}
