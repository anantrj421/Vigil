package com.example.vigil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Confirmation extends AppCompatActivity implements OnMapReadyCallback {
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private MapView mv;
    private String lat, lon;
    private float latit, longi;
    private Button ybtn, nbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        mv = findViewById(R.id.mv);
        nbtn = findViewById(R.id.nbtn);
        ybtn = findViewById(R.id.ybtn);
        initGoogleMap(savedInstanceState);
        lat = getIntent().getStringExtra("lat");
        lon = getIntent().getStringExtra("lon");
        latit = Float.parseFloat(lat);
        longi = Float.parseFloat(lon);

        ybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lon", lon);
                startActivity(intent);
                finish();
            }
        });
        nbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Thanks.class));
                finish();
            }
        });
    }


    private void initGoogleMap(Bundle savedInstanceState){
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mv.onCreate(mapViewBundle);

        mv.getMapAsync(this);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mv.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng currentLocation = new LatLng(latit,longi);
        googleMap.addMarker(new MarkerOptions().position(currentLocation).title("Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,15));
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
    }

    @Override
    public void onResume() {
        super.onResume();
        mv.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mv.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mv.onStop();
    }

    @Override
    public void onPause() {
        mv.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mv.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mv.onLowMemory();
    }
}
