package com.peep.peep.login;

import android.view.View;

import com.apollographql.apollo.api.Response;
import com.peep.peep.QueryLoginNewQuery;

public interface LoginView {
    void showLoading();

    void stopLoading();

    void showError(String message);

    void showUserData(Response<QueryLoginNewQuery.Data> response);

}
