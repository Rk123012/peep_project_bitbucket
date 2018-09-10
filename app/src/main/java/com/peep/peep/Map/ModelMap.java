package com.peep.peep.Map;

import android.content.Context;


import java.util.ArrayList;

public class ModelMap {
    MapInterface modelInterface;
    Context context;

    public ModelMap(MapInterface modelInterface, Context context) {
        this.modelInterface = modelInterface;
        this.context = context;
    }

    public void getLocationList() {

        ArrayList<LocationMap> locations = new ArrayList<>();
        LocationMap loc1 = new LocationMap("princ bazer", "mirpur", 23.809591, 90.367447);
        LocationMap loc2 = new LocationMap("mirpur dohs", "mirpur dohs", 23.836468, 90.369539);
        locations.add(loc1);
        locations.add(loc2);

        modelInterface.onLoacationLoadSuccess(locations);


    }


}
