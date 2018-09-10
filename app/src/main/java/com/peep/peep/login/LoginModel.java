package com.peep.peep.login;

import com.apollographql.apollo.api.Response;
import com.peep.peep.QueryLoginNewQuery;

public interface LoginModel {
    void onLoadUserDataSuccess(Response<QueryLoginNewQuery.Data> response);
    void onLoadUserDataError();
}
