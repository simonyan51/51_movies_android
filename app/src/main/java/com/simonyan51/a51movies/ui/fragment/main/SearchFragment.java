package com.simonyan51.a51movies.ui.fragment.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simonyan51.a51movies.R;
import com.simonyan51.a51movies.ui.fragment.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends BaseFragment implements View.OnClickListener {

    // ===========================================================
    // Constants
    // ===========================================================

    private static final String LOG_TAG = SearchFragment.class.getSimpleName();

    // ===========================================================
    // Fields
    // ===========================================================

    // ===========================================================
    // Constructors
    // ===========================================================

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    public static SearchFragment newInstance(Bundle args) {
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass
    // ===========================================================

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        init();
        findViews(view);
        setListeners();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    // ===========================================================
    // Click Listeners
    // ===========================================================

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    // ===========================================================
    // Other Listeners, methods for/from Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    private void init() {
        setActionBarTitle(getString(R.string.menu_search));
    }

    private void setListeners() {

    }

    private void findViews(View view) {

    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
