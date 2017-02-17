package com.example.android.miwok;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.miwok.adapter.AdapterPagerCategory;

public class ActivityPager extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private AdapterPagerCategory adapterPagerCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        tabLayout = (TabLayout) findViewById(R.id.act_tabs_lyt);
        viewPager = (ViewPager) findViewById(R.id.act_tabs_viewpager);

        adapterPagerCategory = new AdapterPagerCategory(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapterPagerCategory);
        tabLayout.setupWithViewPager(viewPager);
    }
}
