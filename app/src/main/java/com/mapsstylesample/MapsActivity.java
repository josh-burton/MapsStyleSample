package com.mapsstylesample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import mapsstylesample.com.mapsstylesample.R;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps_activity);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-36.84, 174.76);
        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Auckland"));

        MapStyleManager styleManager = MapStyleManager.attachToMap(this, map);
        styleManager.addStyle(0, R.raw.map_style_silver_sparse);
        styleManager.addStyle(10, R.raw.map_style_plain_sparse);
        styleManager.addStyle(12, R.raw.map_style_plain);


        CameraUpdate camereUpdate = CameraUpdateFactory.newLatLng(sydney);
        map.moveCamera(camereUpdate);
        map.moveCamera(CameraUpdateFactory.zoomTo(7));
    }
}
