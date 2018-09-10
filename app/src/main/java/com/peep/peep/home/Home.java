package com.peep.peep.home;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Filter;
import android.widget.SearchView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.peep.peep.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Home extends FragmentActivity implements OnMapReadyCallback,FloatingSearchView.OnQueryChangeListener {

    private GoogleMap mMap;
    @BindView(R.id.search_view)
    FloatingSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    @Override
    public void onSearchTextChanged(String oldQuery, String newQuery) {

        SearchAdapter.findSuggestions(this, newQuery, 5,
                5,new SearchAdapter.OnFindSuggestionsListener() {

                    @Override
                    public void onResults(List<LocationClass> results) {


                        //this will swap the data and
                        //render the collapse/expand animations as necessary
                        searchView.swapSuggestions(results);

                        //let the users know that the background
                        //process has completed
                    }
                });

    }
}
