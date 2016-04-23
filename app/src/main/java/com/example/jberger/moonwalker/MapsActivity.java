package com.example.jberger.moonwalker;

import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final LatLngBounds.Builder builder = new LatLngBounds.Builder();
        // Add a marker in Sydney and move the camera
        LatLng home = App.locationProvider.getLatLng();
        List<POI> Apollo11MissionLocation = App.poiProvider.getLatLng();
        mMap.addMarker(new MarkerOptions().position(home).title("Landing Zone"));
        builder.include(home);
        for (POI latLng : Apollo11MissionLocation) {
            mMap.addMarker(new MarkerOptions().position(latLng.coordinate).title(latLng.name));
            builder.include(latLng.coordinate);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(home));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 200));
            }
        }, 200);

    }
}
