package com.peep.peep.Map;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.arlib.floatingsearchview.util.Util;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.peep.peep.R;
import com.peep.peep.floatingview.CustomFloatingSearchView;
import com.peep.peep.floatingview.SearchCustomAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindPlaceFragment extends Fragment implements OnMapReadyCallback, ViewMap {

    View rootView;
    Context context;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    PresentMap presentMap;
    private CustomFloatingSearchView mSearchView;
    ArrayList<LocationMap> locationList;

    public FindPlaceFragment() {
        // Required empty public constructor
    }

    public static FindPlaceFragment newInstance() {
        FindPlaceFragment fragment = new FindPlaceFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        rootView = inflater.inflate(R.layout.fragment_find_place, container, false);
        ButterKnife.bind(this, rootView);
        presentMap = new PresentMap(this, context);
        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initGMaps();
        mSearchView = (CustomFloatingSearchView) view.findViewById(R.id.floating_search_view);
        presentMap.getLocationList();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setLocationOnMap(locationList);
    }

    private void initGMaps() {
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            mapFragment.getMapAsync(this);
        }
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.map2, mapFragment).commit();

    }

    @Override
    public void startLoading() {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showLocationList(ArrayList<LocationMap> locationList) {
        this.locationList = locationList;
        mSearchView.setOnBindSuggestionCallback(new SearchCustomAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(View suggestionView, ImageView leftIcon, TextView textView, SearchSuggestion item, int itemPosition) {


                    leftIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                            R.drawable.ic_history_black_24dp, null));


                    leftIcon.setAlpha(.36f);

            }

        });

        mSearchView.setOnQueryChangeListener(new CustomFloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {
                List<SearchSuggestion> list = new ArrayList<SearchSuggestion>();
                for (LocationMap item : locationList) {
                    if (item.getName().contains(newQuery)) {
                        list.add(new SimpleSuggestions(item.getName()));
                    }
                }
                mSearchView.swapSuggestions(list);
            }
        });

        mSearchView.setOnSearchListener(new CustomFloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                Toast.makeText(context, "" + getLocationMap(searchSuggestion.getBody()).lat, Toast.LENGTH_SHORT).show();
                setLOcationFocus(getLocationMap(searchSuggestion.getBody()));
            }

            @Override
            public void onSearchAction(String currentQuery) {

            }
        });

    }


    private static class SimpleSuggestions implements SearchSuggestion {
        private final String mData;

        public SimpleSuggestions(String string) {
            mData = string;
        }

        @Override
        public String getBody() {
            return mData;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mData);
        }

        public static final Parcelable.Creator<SimpleSuggestions> CREATOR
                = new Parcelable.Creator<SimpleSuggestions>() {
            public SimpleSuggestions createFromParcel(Parcel in) {
                return new SimpleSuggestions(in);
            }

            public SimpleSuggestions[] newArray(int size) {
                return new SimpleSuggestions[size];
            }
        };

        private SimpleSuggestions(Parcel in) {
            mData = in.readString();
        }
    }


    public LocationMap getLocationMap(String locationName) {
        for (LocationMap item : locationList) {
            if (item.getName().contains(locationName)) {
                return item;
            }
        }

        return null;
    }

    public void setLocationOnMap(ArrayList<LocationMap> locationList) {
        LatLng locFocus = new LatLng(locationList.get(0).getLat(), locationList.get(0).getLon());
        for (LocationMap locationMap : locationList) {
            LatLng loc = new LatLng(locationMap.getLat(), locationMap.getLon());
            mMap.addMarker(new MarkerOptions().position(loc).title(locationMap.getName()));
        }

        // mMap.addMarker(new MarkerOptions().position(locFocus).title(locationList.get(0).getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(locFocus));
    }

    public void setLOcationFocus(LocationMap locationMap) {
        LatLng locFocus = new LatLng(locationMap.getLat(), locationMap.getLon());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(locFocus));

    }


}
