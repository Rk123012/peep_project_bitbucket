package com.peep.peep.Map;

import android.content.Context;


import java.util.ArrayList;

public class PresentMap implements MapInterface {
    ViewMap view;
    ModelMap model;
    Context context;

    public PresentMap(ViewMap view, Context context) {
        this.view = view;
        model = new ModelMap(this,context);
        this.context=context;
    }
    public void getLocationList() {
        model.getLocationList();
        view.startLoading();
    }


    @Override
    public void onLoacationLoadSuccess(ArrayList<LocationMap> locations) {
        view.stopLoading();
        view.showLocationList(locations);
    }

    @Override
    public void onLoadFailed(String message) {

    }
}
