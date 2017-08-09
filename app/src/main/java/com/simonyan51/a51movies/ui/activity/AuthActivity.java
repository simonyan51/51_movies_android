package com.simonyan51.a51movies.ui.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.simonyan51.a51movies.R;
import com.simonyan51.a51movies.db.DatabaseHelper;
import com.simonyan51.a51movies.db.table.UserDatabase;
import com.simonyan51.a51movies.db.query.DataQueryHandler;
import com.simonyan51.a51movies.db.query.UserDataQueryHandler;
import com.simonyan51.a51movies.pojo.User;
import com.simonyan51.a51movies.util.CheckMessageHelper;
import com.simonyan51.a51movies.util.Constant;
import com.simonyan51.a51movies.util.PreferenceHelper;

public class AuthActivity extends BaseActivity implements DataQueryHandler.DataQueryListener,
        View.OnClickListener {

    // ===========================================================
    // Constants
    // ===========================================================

    private static final String LOG_TAG = AuthActivity.class.getSimpleName();

    // ===========================================================
    // Fields
    // ===========================================================

    private EditText mEtEmail;
    private EditText mEtPassword;
    private Button mBSignButton;
    private TextView mTvRegisterButton;

    private DataQueryHandler mDataQueryHandler;
    private SQLiteOpenHelper mDataHelper;
    private Handler mThreadHandler;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        findViews();
        setListeners();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_auth;
    }

    // ===========================================================
    // Listeners, methods for/from Interfaces
    // ===========================================================

    @Override
    public void onQueryComplete(int token, Object o) {

        switch (token) {

            case UserDataQueryHandler.UserToken.GET_EMAIL:

                User user = (User) o;

                if (mEtPassword.getText().toString().equals(((User) o).getPassword())) {

                    PreferenceHelper.getInstance(this).setUser((User) o);


                    mThreadHandler.sendMessage(CheckMessageHelper.executeMessage(mThreadHandler, CheckMessageHelper.SUCCESS));

                } else {
                    mThreadHandler.sendMessage(CheckMessageHelper.executeMessage(mThreadHandler, CheckMessageHelper.FAILED));
                }
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

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id) {
            case R.id.btn_auth_sign:
                auth();
                break;

            case R.id.btn_auth_reg:
                register();
                break;
        }

    }

    // ===========================================================
    // Methods
    // ===========================================================

    private void init() {

        mDataHelper = new DatabaseHelper(this, UserDatabase.TABLE_NAME, UserDatabase.CREATE_TABLE);
        mDataQueryHandler = new UserDataQueryHandler(mDataHelper, this);

        mThreadHandler = new Handler(Looper.getMainLooper()) {

            @Override
            public void handleMessage(Message inputMessage) {
                switch (inputMessage.getData().getInt(CheckMessageHelper.KEY)) {
                    case CheckMessageHelper.SUCCESS:
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                        break;

                    case CheckMessageHelper.FAILED:
                        Snackbar.make(findViewById(R.id.sc_auth_container), R.string.auth_err, Snackbar.LENGTH_LONG).show();
                        break;
                }
            }
        };

    }

    private void findViews() {
        mEtEmail = (EditText) findViewById(R.id.tiet_auth_email);
        mEtPassword = (EditText) findViewById(R.id.tiet_auth_pass);
        mBSignButton = (Button) findViewById(R.id.btn_auth_sign);
        mTvRegisterButton = (TextView) findViewById(R.id.btn_auth_reg);
    }

    private void setListeners() {
        mBSignButton.setOnClickListener(this);
        mTvRegisterButton.setOnClickListener(this);
    }


    private void auth() {
        String email = mEtEmail.getText().toString();
        mDataQueryHandler.query(UserDataQueryHandler.UserToken.GET_EMAIL, email);
    }

    private void register() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
