package com.example.cumn_rv;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng club1 = new LatLng(40.50854098290643, -3.8878584080820535);
        mMap.addMarker(new MarkerOptions().position(club1).title("Club de Pádel Navalcarbon"));

        LatLng club2 = new LatLng(40.50567366102648, -3.8887693999999997);
        mMap.addMarker(new MarkerOptions().position(club2).title("Padel Center"));

        LatLng club3 = new LatLng(40.500734526526756, -3.8871295306841964);
        mMap.addMarker(new MarkerOptions().position(club3).title("Rackets Madrid"));

        LatLng club4 = new LatLng(40.49142839526374, -3.888249188355247);
        mMap.addMarker(new MarkerOptions().position(club4).title("Club de tenis Las Rozas"));

        LatLng club5 = new LatLng(40.48596866567326, -3.8710951791772295);
        mMap.addMarker(new MarkerOptions().position(club5).title("Club DUIN"));



        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(club1, 6));
    }
}
