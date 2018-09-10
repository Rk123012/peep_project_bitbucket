package com.peep.peep.login;


import com.apollographql.apollo.api.Response;
import com.peep.peep.QueryLoginNewQuery;

public class LoginPresenterImp implements LoginModel,LoginPresenter {
    private LoginView view;
    private LoginModelImp loginModelImp = new LoginModelImp();

    public LoginPresenterImp(LoginView view) {
        this.view = view;

    }

    @Override
    public void checkLogin(String userID, String userPassword) {
        loginModelImp.checkLogin(userID, userPassword, this);
    }

    @Override
    public void validateUserCredential(String userID, String userPassword) {

    }

    @Override
    public void onLoadUserDataSuccess(Response<QueryLoginNewQuery.Data> response) {
        view.showUserData(response);

    }

    @Override
    public void onLoadUserDataError() {
        view.showError("failed");
    }
}
