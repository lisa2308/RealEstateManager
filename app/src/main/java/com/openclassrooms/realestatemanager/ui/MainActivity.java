package com.openclassrooms.realestatemanager.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.estate.addupdate.EstateAddUpdateActivity;
import com.openclassrooms.realestatemanager.ui.estate.list.EstateListFragment;
import com.openclassrooms.realestatemanager.ui.loan.LoanActivity;
import com.openclassrooms.realestatemanager.ui.map.MapActivity;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        addFragment(R.id.activity_main_frame_layout_list, new EstateListFragment(), 0, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.activity_main_menu_add_estate:
                startActivity(new Intent(this, EstateAddUpdateActivity.class));
                return true;
            case R.id.activity_main_menu_search:
                return true;
            case R.id.activity_main_menu_loan_simulator:
                startActivity(new Intent(this, LoanActivity.class));
                return true;
            case R.id.activity_main_menu_map:
                if (Utils.isInternetAvailable(this)) {
                    askLocationPermission();
                } else {
                    Toast.makeText(this, "Connexion internet indisponible", Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void askLocationPermission() {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {
                        Toast.makeText(MainActivity.this, "Récupération de votre position", Toast.LENGTH_SHORT).show();
                        requestLocation();
                    }
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {

                    }
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void requestLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            startMapActivity(location.getLatitude(), location.getLongitude());
                        } else {
                            Toast.makeText(MainActivity.this, "Nous n'arrivons pas à accéder à votre localisation", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void startMapActivity(double lat, double lng) {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("latitude", lat);
        intent.putExtra("longitude", lng);
        startActivity(intent);
    }

    public void addFragment(int frameLayout, Fragment fragment, int enterAnim, int exitAnim) {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(enterAnim, 0, 0, exitAnim);
        transaction.add(frameLayout, fragment, fragment.getClass().getSimpleName());
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    public void replaceFragment(int frameLayout, Fragment fragment, int enterAnim, int exitAnim) {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(enterAnim, 0, 0, exitAnim);
        transaction.replace(frameLayout, fragment, fragment.getClass().getSimpleName());
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }


}
