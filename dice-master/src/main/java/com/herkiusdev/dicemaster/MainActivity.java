package com.herkiusdev.dicemaster;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.herkiusdev.dicemaster.adapter.ViewPagerAdapter;
import com.herkiusdev.dicemaster.fragment.BoardFragment_;
import com.herkiusdev.dicemaster.fragment.RPGFragment_;
import com.herkiusdev.dicemaster.util.FadePageTransformer;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.main_toolbar)
    Toolbar toolbar;
    @ViewById(R.id.main_toolbar_title)
    TextView toolbarTitle;
    @ViewById(R.id.tab_layout)
    TabLayout tabLayout;
    @ViewById(R.id.view_pager)
    ViewPager viewPager;

    @AfterViews
    public void initViews() {
        setupToolbar();
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle.setText(getText(R.string.app_name).toString());
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPager.setPageTransformer(false, new FadePageTransformer());
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BoardFragment_(), getText(R.string.board_fragment_name).toString());
        adapter.addFragment(new RPGFragment_(), getText(R.string.rpg_fragment_name).toString());
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.view_custom_tab, null);
        tabOne.setText(getText(R.string.board_fragment_name).toString().toUpperCase());
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_board_gaming, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.view_custom_tab, null);
        tabTwo.setText(getText(R.string.rpg_fragment_name).toString().toUpperCase());
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_rpg_gaming, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        tabLayout.getTabAt(1).select();
        tabLayout.getTabAt(0).select();
    }

}
