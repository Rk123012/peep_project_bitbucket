package com.peep.peep.Map;

import android.content.Context;
import android.util.Log;


import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.peep.peep.MyApolloClient;
import com.peep.peep.QueryCameraQuery;
import com.peep.peep.QueryImageQuery;
import com.peep.peep.QueryLoginNewQuery;
import com.peep.peep.Utility.SharedPreferenceManager;

import java.util.ArrayList;

import javax.annotation.Nonnull;

public class ModelMap {
    private MapInterface modelInterface;
    private Context context;

    public ModelMap(MapInterface modelInterface, Context context) {
        this.modelInterface = modelInterface;
        this.context = context;
    }

    public void getLocationList() {

        MyApolloClient.getMyApolloClient().query(QueryCameraQuery.builder().userIds(1).toekn("").build()).
                enqueue(new ApolloCall.Callback<QueryCameraQuery.Data>() {
                    @Override
                    public void onResponse(@Nonnull Response<QueryCameraQuery.Data> response) {
                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {

                    }
                });

        ArrayList<LocationMap> locations = new ArrayList<>();
        LocationMap loc1 = new LocationMap("princ bazer", "mirpur", 23.809591, 90.367447);
        LocationMap loc2 = new LocationMap("mirpur dohs", "mirpur dohs", 23.836468, 90.369539);
        locations.add(loc1);
        locations.add(loc2);

        modelInterface.onLoacationLoadSuccess(locations);


    }


}
