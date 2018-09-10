package com.peep.peep.login;

import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.rx2.Rx2Apollo;
import com.peep.peep.MyApolloClient;
import com.peep.peep.QueryLoginNewQuery;

import javax.annotation.Nonnull;

public class LoginModelImp {

    public void checkLogin(String userID, String password, final LoginModel loginModel) {
        /*if (userID.isEmpty())
            loginModel.onLoadUserDataError();
        else loginModel.onLoadUserDataSuccess();*/

        //without rx
        MyApolloClient.getMyApolloClient().query(
                QueryLoginNewQuery.builder().email(userID).password(password).build()).
                enqueue(new ApolloCall.Callback<QueryLoginNewQuery.Data>() {
                    @Override
                    public void onResponse(@Nonnull Response<QueryLoginNewQuery.Data> response) {
                        loginModel.onLoadUserDataSuccess(response);
                        //response.data().login().firstName();
                        Log.d("Log",response.data().toString());
                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {
                        loginModel.onLoadUserDataError();
                    }
                });
       //with rx
        //Rx2Apollo.from(QueryLoginNewQuery.builder().email(userID).password(password).build())
    }
}
