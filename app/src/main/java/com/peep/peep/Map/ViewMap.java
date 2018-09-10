package com.peep.peep.Map;

import java.util.ArrayList;

public interface ViewMap {
    void startLoading();
    void stopLoading();
    void showError(String message);
    void showLocationList(ArrayList<LocationMap> locationList);
}
