package com.peep.peep.Map;

import java.util.ArrayList;

public interface MapInterface {
    void onLoacationLoadSuccess(ArrayList<LocationMap> locations);
    void onLoadFailed(String message);
}
