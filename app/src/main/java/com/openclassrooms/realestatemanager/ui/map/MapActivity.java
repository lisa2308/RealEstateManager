package com.openclassrooms.realestatemanager.ui.map;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.data.entities.Estate;
import com.openclassrooms.realestatemanager.data.viewmodel.EstateViewModel;
import com.openclassrooms.realestatemanager.data.viewmodel.ViewModelFactory;
import com.openclassrooms.realestatemanager.di.Injection;
import com.openclassrooms.realestatemanager.ui.estate.list.EstateListFragment;

import butterknife.ButterKnife;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    private EstateViewModel estateViewModel;

    double userLat;
    double userLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);

        setTitle("Map");

        Intent intent = getIntent();
        userLat = intent.getDoubleExtra("latitude", 0);
        userLng = intent.getDoubleExtra("longitude", 0);

        initMap();
    }

    private void initMap() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map_googleMap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(userLat, userLng), 16.5f));

        loadEstatesOnMap();
    }

    private void loadEstatesOnMap() {
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        estateViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EstateViewModel.class);

        estateViewModel.getCurrentEstateList().observe(this, estateList -> {
            for (Estate estate : estateList) {
                drawMarker(estate);
            }
        });
    }

    private void drawMarker(Estate estate) {
        LatLng estatePosition = new LatLng(
            estate.getEstateLat(),
            estate.getEstateLng()
        );
        this.googleMap.addMarker(new MarkerOptions().position(estatePosition).title(String.valueOf(estate.getEstatePrice())));
    }
}
