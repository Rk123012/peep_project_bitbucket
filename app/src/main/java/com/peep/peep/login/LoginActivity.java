package com.peep.peep.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.apollographql.apollo.api.Response;
import com.peep.peep.QueryLoginNewQuery;
import com.peep.peep.R;
import com.peep.peep.Utility.KeyString;
import com.peep.peep.Utility.SharedPreferenceManager;
import com.peep.peep.home.Home;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity implements LoginView {

    // UI references.
    @BindView(R.id.userID)
    EditText userID;
    @BindView(R.id.password)
    EditText mPasswordView;
    @BindView(R.id.sign_in_button)
    Button mSignInButton;

    private LoginPresenterImp loginPresenterImp;
    private SharedPreferenceManager preferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        //initialize
        loginPresenterImp = new LoginPresenterImp(this);
        preferenceManager = new SharedPreferenceManager(this, KeyString.PREFERENCE_NAME);

    }

    @OnClick(R.id.sign_in_button)
    public void submit(View view) {
        loginPresenterImp.checkLogin(userID.getText().toString(), mPasswordView.getText().toString());
    }

    @Override
    public void showLoading() {
        //show loading
    }

    @Override
    public void stopLoading() {
        //stop loading
    }


    @Override
    public void showError(String message) {
        Log.d(message, message);

    }

    @Override
    public void showUserData(Response<QueryLoginNewQuery.Data> userData) {
        //Log.d("message", userData.data().login().firstName());
        preferenceManager.setValue(KeyString.KEY_TOKEN, userData.data().login().loginKey());
        startActivity(new Intent(this, Home.class));
    }
}

