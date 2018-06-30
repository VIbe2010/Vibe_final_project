package com.vibe.org.vibe;



import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double latitude=9.014472;
    private double longitude=38.748497;
    private String placeName;
    private Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mBundle=getIntent().getExtras();
        latitude= getIntent().getDoubleExtra("latitude",9.014472);
        longitude=getIntent().getDoubleExtra("longitude",38.748497);
        Log.d("LOCATION", "onCreate: "+latitude+","+longitude);
        placeName=getIntent().getStringExtra("placename");
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        /*LatLng sydney = new LatLng(mSharedPrefPlace.getPlaceLatitude(), mSharedPrefPlace.getPlaceLongitude());
        mMap.addMarker(new MarkerOptions().position(sydney).title(mSharedPrefPlace.getPlaceName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/


        LatLng placeLocation= new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(placeLocation).title(placeName));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new
                LatLng(latitude, longitude), 16.0f));

    }
}
