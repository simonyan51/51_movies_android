package com.simonyan51.a51movies.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.simonyan51.a51movies.R;

public class MessageActivity extends BaseActivity implements View.OnClickListener {

    // ===========================================================
    // Constants
    // ===========================================================

    private static final String LOG_TAG = MessageActivity.class.getSimpleName();

    // ===========================================================
    // Fields
    // ===========================================================

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
        return R.layout.activity_message;
    }


    // ===========================================================
    // Listeners, methods for/from Interfaces
    // ===========================================================

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // ===========================================================
    // Methods
    // ===========================================================

    private void init() {
        setActionBarTitle(getString(R.string.menu_message));
    }

    private void findViews() {

    }

    private void setListeners() {

    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
