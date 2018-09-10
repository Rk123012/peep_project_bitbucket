package com.peep.peep.login;

public interface LoginPresenter {
    void checkLogin(String userID, String userPassword);

    void validateUserCredential(String userID, String userPassword);

}
