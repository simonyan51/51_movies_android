package com.simonyan51.a51movies.ui.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.DateFormat;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.simonyan51.a51movies.R;
import com.simonyan51.a51movies.db.DatabaseHelper;
import com.simonyan51.a51movies.db.query.DataQueryHandler;
import com.simonyan51.a51movies.db.query.UserDataQueryHandler;
import com.simonyan51.a51movies.db.table.UserDatabase;
import com.simonyan51.a51movies.pojo.User;
import com.simonyan51.a51movies.util.CheckMessageHelper;
import com.simonyan51.a51movies.util.Constant;
import com.simonyan51.a51movies.util.PreferenceHelper;

import java.util.Date;

public class RegisterActivity extends BaseActivity implements UserDataQueryHandler.DataQueryListener,
        View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    // ===========================================================
    // Constants
    // ===========================================================

    private static final String LOG_TAG = RegisterActivity.class.getSimpleName();

    // ===========================================================
    // Fields
    // ===========================================================

    private EditText mEtUsername;
    private EditText mEtFirstName;
    private EditText mEtLastName;
    //TODO just for simple
    private EditText mEtBirthDate;
    private Button mBtnBirthDate;
    private RadioGroup mRgGender;
    private EditText mEtEmail;
    private EditText mEtPassword;
    private EditText mEtRePassword;
    private Button mBtnRegister;

    private SQLiteOpenHelper mDbHelper;
    private DataQueryHandler mUserQueryHandler;
    private Handler mThreadHandler;

    private User mUser;

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
        findViews();
        setListeners();
        init();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_register;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // ===========================================================
    // Listeners, methods for/from Interfaces
    // ===========================================================

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_reg_date_birth:
                selectDateTime();
                break;
            case R.id.btn_reg_submit:
                register();
                break;
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (radioGroup.getId()) {
            case R.id.rb_reg_gender:
                switch (i) {
                    case R.id.rb_reg_male:
                        mUser.setGender(Constant.Gender.MALE);
                        break;
                    case R.id.rb_reg_female:
                        mUser.setGender(Constant.Gender.FEMALE);
                        break;
                }
                break;
        }
    }

    @Override
    public void onQueryComplete(int token, Object o) {

    }

    @Override
    public void onInsertComplete(int token, Object o) {

        Message message = mThreadHandler.obtainMessage();
        Bundle bundle = new Bundle();

        switch (token) {
            case UserDataQueryHandler.UserToken.INSERT:
                PreferenceHelper.getInstance(this).setUser(mUser);
                mThreadHandler.sendMessage(CheckMessageHelper.executeMessage(mThreadHandler, CheckMessageHelper.SUCCESS));
                break;

            case UserDataQueryHandler.UserToken.ERROR:
                mThreadHandler.sendMessage(CheckMessageHelper.executeMessage(mThreadHandler, CheckMessageHelper.FAILED));
                break;
        }

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
        setActionBarTitle(getString(R.string.reg_title));

        mUser = new User();
        mUser.setGender(Constant.Gender.MALE);

        mDbHelper = new DatabaseHelper(this, UserDatabase.TABLE_NAME, UserDatabase.CREATE_TABLE);
        mUserQueryHandler = new UserDataQueryHandler(mDbHelper, this);

        mThreadHandler = new Handler(Looper.getMainLooper()) {

            @Override
            public void handleMessage(Message inputMessage) {
                switch (inputMessage.getData().getInt(CheckMessageHelper.KEY)) {
                    case CheckMessageHelper.SUCCESS:
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                        break;

                    case CheckMessageHelper.FAILED:
                        Snackbar.make(findViewById(R.id.reg_container), R.string.db_anonymous_err, Snackbar.LENGTH_LONG).show();
                        break;
                }
            }
        };
    }

    private void findViews() {
        mEtUsername = (EditText) findViewById(R.id.et_reg_username);
        mEtFirstName = (EditText) findViewById(R.id.et_reg_first_name);
        mEtLastName = (EditText) findViewById(R.id.et_reg_last_name);
        //TODO just for simple
        mEtBirthDate = (EditText) findViewById(R.id.et_reg_birth_date);
        mBtnBirthDate = (Button) findViewById(R.id.btn_reg_date_birth);
        mRgGender = (RadioGroup) findViewById(R.id.rb_reg_gender);
        mEtEmail = (EditText) findViewById(R.id.et_reg_email);
        mEtPassword = (EditText) findViewById(R.id.et_reg_pass);
        mEtRePassword = (EditText) findViewById(R.id.et_reg_pass_re);
        mBtnRegister = (Button) findViewById(R.id.btn_reg_submit);
    }

    private void setListeners() {
        mBtnRegister.setOnClickListener(this);
        mRgGender.setOnCheckedChangeListener(this);
        mBtnBirthDate.setOnClickListener(this);
    }

    private void register() {
        if (validation()) {
            prepareUser(mUser);
            mUserQueryHandler.insert(UserDataQueryHandler.UserToken.INSERT, mUser);

        } else {
            Snackbar.make(findViewById(R.id.reg_container), R.string.valid_fields_err, Snackbar.LENGTH_LONG).show();
        }
    }

    private boolean validation() {

        if (mEtUsername.getText().toString().equals("")) {
            return false;
        }
        if (mEtFirstName.getText().toString().equals("")) {
            return false;
        }
        if (mEtLastName.getText().toString().equals("")) {
            return false;
        }
//        if (mUser.getBirthDate().equals(null)) {
//            return false;
//        }
        if (mUser.getGender().equals("")) {
            return false;
        }
        if (mEtEmail.getText().toString().equals("")) {
            return false;
        }
        if (mEtPassword.getText().toString().equals("")) {
            return false;
        }
        if (mEtRePassword.getText().toString().equals("")) {
            return false;
        }

        if (!mEtPassword.getText().toString().equals(mEtPassword.getText().toString())) {
            Snackbar.make(findViewById(R.id.reg_container), R.string.pass_equal_err, Snackbar.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void prepareUser(User user) {
        user.setId(System.currentTimeMillis());
        user.setUsername(mEtUsername.getText().toString());
        user.setFirstName(mEtFirstName.getText().toString());
        user.setLastName(mEtLastName.getText().toString());
        user.setBirthDate(mEtBirthDate.getText().toString());
        user.setEmail(mEtEmail.getText().toString());
        user.setPassword(mEtPassword.getText().toString());
        user.setIsAdmin(0);
    }


    //TODO check whats happened
    private void selectDateTime() {
        SlideDateTimeListener listener = new SlideDateTimeListener() {

            @Override
            public void onDateTimeSet(Date date)
            {
                mUser.setBirthDate(date.toString());

            }

            @Override
            public void onDateTimeCancel()
            {

            }
        };

        new SlideDateTimePicker.Builder(getSupportFragmentManager())
                .setListener(listener)
                .setInitialDate(new Date())
                .build()
                .show();

    }


    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
