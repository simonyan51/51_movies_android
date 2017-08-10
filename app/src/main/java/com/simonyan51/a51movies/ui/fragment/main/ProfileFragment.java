package com.simonyan51.a51movies.ui.fragment.main;


import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.simonyan51.a51movies.R;
import com.simonyan51.a51movies.db.DatabaseHelper;
import com.simonyan51.a51movies.db.query.DataQueryHandler;
import com.simonyan51.a51movies.db.query.UserDataQueryHandler;
import com.simonyan51.a51movies.db.table.UserDatabase;
import com.simonyan51.a51movies.pojo.User;
import com.simonyan51.a51movies.ui.activity.AuthActivity;
import com.simonyan51.a51movies.ui.activity.MainActivity;
import com.simonyan51.a51movies.ui.fragment.BaseFragment;
import com.simonyan51.a51movies.util.CheckMessageHelper;
import com.simonyan51.a51movies.util.PreferenceHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment implements View.OnClickListener,
        UserDataQueryHandler.DataQueryListener {

    // ===========================================================
    // Constants
    // ===========================================================

    private static final String LOG_TAG = ProfileFragment.class.getSimpleName();

    // ===========================================================
    // Fields
    // ===========================================================

    private ImageView mIvProfileImage;
    private TextView mTvUsername;
    private Button mLogoutButton;

    private User mUser;
    private DataQueryHandler mUserQueryHandler;
    private SQLiteOpenHelper mUserDatabase;
    private Handler mThreadHandler;

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

    @Override
    public void onQueryComplete(int token, Object o) {
        switch (token) {
            case UserDataQueryHandler.UserToken.GET:
                mUser = (User) o;
                mThreadHandler.sendMessage(CheckMessageHelper.executeMessage(mThreadHandler, CheckMessageHelper.SUCCESS));
                break;

            case UserDataQueryHandler.UserToken.ERROR:
                mThreadHandler.sendMessage(CheckMessageHelper.executeMessage(mThreadHandler, CheckMessageHelper.FAILED));
                break;
        }
    }

    @Override
    public void onInsertComplete(int token, Object o) {

    }

    @Override
    public void onUpdateComplete(int token, Object o) {

    }

    @Override
    public void onDeleteComplete(int token, Object o) {

    }

    // ===========================================================
    // Methods
    // ===========================================================

    private void init() {
        setActionBarTitle(getString(R.string.menu_profile));

        mUserDatabase = new DatabaseHelper(getContext(), UserDatabase.TABLE_NAME, UserDatabase.CREATE_TABLE);
        mUserQueryHandler = new UserDataQueryHandler(mUserDatabase, this);

        executeProfile();
        handleMessage();

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
        mTvUsername = view.findViewById(R.id.tv_profile_username);
    }

    private void logout() {
        PreferenceHelper.getInstance(getActivity().getBaseContext()).deleteUserData();;
        Intent intent = new Intent(getActivity(), AuthActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void executeProfile() {
        mUserQueryHandler.query(UserDataQueryHandler.UserToken.GET, PreferenceHelper.getInstance(getContext()).getUserId());
    }

    private void handleMessage() {

        mThreadHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {
                switch (inputMessage.getData().getInt(CheckMessageHelper.KEY)) {
                    case CheckMessageHelper.SUCCESS:

                        setFields(mUser);

                        break;

                    case CheckMessageHelper.FAILED:
                        Snackbar.make(getActivity().findViewById(R.id.sc_profile_fragment_container), R.string.db_anonymous_err, Snackbar.LENGTH_LONG).show();
                        break;
                }
            }
        };

    }

    private void setFields(User user) {
        mTvUsername.setText(user.getUsername());
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}

