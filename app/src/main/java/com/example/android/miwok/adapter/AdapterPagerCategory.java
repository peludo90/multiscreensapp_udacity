package com.example.android.miwok.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.miwok.R;
import com.example.android.miwok.fragments.FragmentColors;
import com.example.android.miwok.fragments.FragmentFamily;
import com.example.android.miwok.fragments.FragmentNumbers;
import com.example.android.miwok.fragments.FragmentPhrases;

/**
 * Created by obed.gonzalez on 17/02/2017.
 */
public class AdapterPagerCategory extends FragmentPagerAdapter {

    private String[] titles;

    public AdapterPagerCategory(FragmentManager fm, Context context) {
        super(fm);
        titles = new String[]{
                context.getString(R.string.category_numbers),
                context.getString(R.string.category_family),
                context.getString(R.string.category_colors),
                context.getString(R.string.category_phrases)};

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentNumbers();
            case 1:
                return new FragmentFamily();
            case 2:
                return new FragmentColors();
            case 3:
                return new FragmentPhrases();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return titles[position];
    }
}
