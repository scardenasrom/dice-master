package com.example.scardenas.dice_master;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.scardenas.dice_master.adapter.ViewPagerAdapter;
import com.example.scardenas.dice_master.fragment.FavouritesFragment;
import com.example.scardenas.dice_master.fragment.FavouritesFragment_;
import com.example.scardenas.dice_master.fragment.HistoryFragment;
import com.example.scardenas.dice_master.fragment.HistoryFragment_;
import com.example.scardenas.dice_master.fragment.NewRollFragment;
import com.example.scardenas.dice_master.fragment.NewRollFragment_;
import com.example.scardenas.dice_master.util.FadePageTransformer;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity
        extends AppCompatActivity
        implements  FavouritesFragment.FavouritesRollListener,
                    HistoryFragment.HistoryEmptyListLayoutClickListener,
                    NewRollFragment.NewRollListener {

    @ViewById(R.id.main_toolbar) Toolbar toolbar;
    @ViewById(R.id.main_tab_layout) TabLayout tabLayout;
    @ViewById(R.id.main_view_pager) ViewPager viewPager;

    private FavouritesFragment_ favouritesFragment_;
    private HistoryFragment_ historyFragment_;
    private NewRollFragment_ newRollFragment_;

    @AfterViews
    public void initialize() {
        newRollFragment_ = new NewRollFragment_();
        favouritesFragment_ = new FavouritesFragment_();
        historyFragment_ = new HistoryFragment_();
        setSupportActionBar(toolbar);
        setupViewPager();
    }

    private void setupViewPager() {
        viewPager.setOffscreenPageLimit(2);
        viewPager.setPageTransformer(false, new FadePageTransformer());
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(newRollFragment_, getString(R.string.new_roll_fragment_title));
        adapter.addFragment(favouritesFragment_, getString(R.string.favourites_fragment_title));
        adapter.addFragment(historyFragment_, getString(R.string.history_fragment_title));
        viewPager.setAdapter(adapter);
        setupTabLayoutAndTabs();
    }

    private void setupTabLayoutAndTabs() {
        tabLayout.setupWithViewPager(viewPager);

        TextView tabOne = (TextView) LayoutInflater.from(MainActivity.this).inflate(R.layout.view_custom_tab, null);
        tabOne.setText(R.string.new_roll_fragment_title);
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_d20, 0, 0);
        TabLayout.Tab tab1 = tabLayout.getTabAt(0);
        if (tab1 != null)
            tab1.setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(MainActivity.this).inflate(R.layout.view_custom_tab, null);
        tabTwo.setText(R.string.favourites_fragment_title);
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_favourite, 0, 0);
        TabLayout.Tab tab2 = tabLayout.getTabAt(1);
        if (tab2 != null)
            tab2.setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(MainActivity.this).inflate(R.layout.view_custom_tab, null);
        tabThree.setText(R.string.history_fragment_title);
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_history, 0, 0);
        TabLayout.Tab tab3 = tabLayout.getTabAt(2);
        if (tab3 != null)
            tab3.setCustomView(tabThree);

        if (tab1 != null && tab2 != null && tab3 != null) {
            tab3.select();
            tab2.select();
            tab1.select();
        }
    }

    private void notifyHistory() {
        if (historyFragment_ != null)
            historyFragment_.reloadListAndRecyclerView();
    }

    //region FavouritesFragment callbacks
    @Override
    public void onEmptyListLayoutClick() {
        TabLayout.Tab tabOne = tabLayout.getTabAt(0);
        if (tabOne != null)
            tabOne.select();
    }

    @Override
    public void onFavouriteRollMade() {
        notifyHistory();
    }
    //endregion

    //region HistoryFragment callbacks
    @Override
    public void onHistoryEmptyListLayoutClick() {
        TabLayout.Tab tabOne  = tabLayout.getTabAt(0);
        if (tabOne != null)
            tabOne.select();
    }
    //endregion

    //region NewRollFragment callbacks
    @Override
    public void onNewFavouriteRollSavedListener() {
        TabLayout.Tab tabTwo = tabLayout.getTabAt(1);
        if (tabTwo != null)
            tabTwo.select();
        if (favouritesFragment_ != null)
            favouritesFragment_.reloadListAndRecyclerView();
    }

    @Override
    public void onNewRollMade() {
        notifyHistory();
    }
    //endregion

}
