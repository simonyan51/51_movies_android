package com.simonyan51.a51movies.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.simonyan51.a51movies.R;
import com.simonyan51.a51movies.ui.fragment.main.FavoriteFragment;
import com.simonyan51.a51movies.ui.fragment.main.HomeFragment;
import com.simonyan51.a51movies.ui.fragment.main.MovieFragment;
import com.simonyan51.a51movies.ui.fragment.main.ProfileFragment;
import com.simonyan51.a51movies.ui.fragment.main.SearchFragment;
import com.simonyan51.a51movies.util.FragmentTransactionManager;
import com.simonyan51.a51movies.util.PreferenceHelper;

public class MainActivity extends BaseActivity implements View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {

    // ===========================================================
    // Constants
    // ===========================================================

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    // ===========================================================
    // Fields
    // ===========================================================

    private BottomNavigationView mBnNavigation;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass
    // ===========================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        findViews();
        setListeners();
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_message, menu);
        return super.onCreateOptionsMenu(menu);
    }



    // ===========================================================
    // Listeners, methods for/from Interfaces
    // ===========================================================

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_home:
                openScreen(HomeFragment.newInstance(), R.id.main_container, false);
                break;
            case R.id.action_search:
                openScreen(SearchFragment.newInstance(), R.id.main_container, true);
                break;
            case R.id.action_movie:
                openScreen(MovieFragment.newInstance(), R.id.main_container, true);
                break;
            case R.id.action_fav:
                openScreen(FavoriteFragment.newInstance(), R.id.main_container, true);
                break;
            case R.id.action_profile:
                openScreen(ProfileFragment.newInstance(), R.id.main_container, true);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_message:
                startMessageActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // ===========================================================
    // Methods
    // ===========================================================

    private void init() {
        setActionBarTitle(getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        openScreen(HomeFragment.newInstance(), R.id.main_container, false);
    }

    private void findViews() {
        mBnNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
    }

    private void setListeners() {
        mBnNavigation.setOnNavigationItemSelectedListener(this);
    }

    private void openScreen(Fragment fragment, int view, Boolean mustAddToBackStack) {
        FragmentTransactionManager.displayFragment(
                getSupportFragmentManager(),
                fragment,
                view,
                mustAddToBackStack
        );
    }

    private void startMessageActivity() {
        Intent intent = new Intent(this, MessageActivity.class);
        startActivity(intent);
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
