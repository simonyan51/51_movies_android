package com.simonyan51.a51movies.ui.fragment.main;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.simonyan51.a51movies.R;
import com.simonyan51.a51movies.ui.activity.AuthActivity;
import com.simonyan51.a51movies.ui.fragment.BaseFragment;
import com.simonyan51.a51movies.util.PreferenceHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    // ===========================================================
    // Constants
    // ===========================================================

    private static final String LOG_TAG = ProfileFragment.class.getSimpleName();

    // ===========================================================
    // Fields
    // ===========================================================

    private ImageView mIvProfileImage;
    private Button mLogoutButton;

    // ===========================================================
    // Constructors
    // ===========================================================

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    public static ProfileFragment newInstance(Bundle args) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        findViews(view);
        init();
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
        int id = v.getId();
        switch (id) {
            case R.id.logout:
                logout();
                break;
        }
    }

    // ===========================================================
    // Other Listeners, methods for/from Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    private void init() {
        setActionBarTitle(getString(R.string.menu_profile));

        Glide.with(mIvProfileImage.getContext())
                .load("http://www.lilavatihospital.com/Admin/Doctors/doctor.jpg")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mIvProfileImage);
    }

    private void setListeners() {
        mLogoutButton.setOnClickListener(this);
    }

    private void findViews(View view) {
        mLogoutButton = view.findViewById(R.id.logout);
        mIvProfileImage = view.findViewById(R.id.iv_profile_image);
    }

    private void logout() {
        PreferenceHelper.getInstance(getActivity().getBaseContext()).deleteUserData();;
        Intent intent = new Intent(getActivity(), AuthActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}

