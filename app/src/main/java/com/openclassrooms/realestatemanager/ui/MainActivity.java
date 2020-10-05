package com.openclassrooms.realestatemanager.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.data.viewmodel.EstateViewModel;
import com.openclassrooms.realestatemanager.data.viewmodel.ViewModelFactory;
import com.openclassrooms.realestatemanager.di.Injection;
import com.openclassrooms.realestatemanager.ui.estate.addupdate.EstateAddUpdateActivity;
import com.openclassrooms.realestatemanager.ui.estate.details.EstateDetailsFragment;
import com.openclassrooms.realestatemanager.ui.estate.list.EstateListFragment;
import com.openclassrooms.realestatemanager.ui.estate.search.SearchFragment;
import com.openclassrooms.realestatemanager.ui.loan.LoanActivity;
import com.openclassrooms.realestatemanager.ui.map.MapActivity;
import com.openclassrooms.realestatemanager.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private EstateViewModel estateViewModel;

    @BindView(R.id.activity_main_btn_reinit_filters)
    MaterialButton btnReinitFilters;

    boolean reinitFilterVisible = false;
    boolean isTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        isTablet = getResources().getBoolean(R.bool.isTablet);

        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        estateViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EstateViewModel.class);

        if (savedInstanceState == null) {
            addFragment(R.id.activity_main_frame_layout_list, new EstateListFragment(), 0, 0);
        } else {
            reinitFilterVisible = savedInstanceState.getBoolean("reinitFilterVisible");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("reinitFilterVisible", btnReinitFilters.getVisibility() == View.VISIBLE);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!reinitFilterVisible) {
            estateViewModel.initCurrentEstateList();
        } else {
            btnReinitFilters.setVisibility(View.VISIBLE);
        }

        hideDetailsFragment();
    }

    public EstateViewModel getViewModel() {
        return estateViewModel;
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
                if (!isTablet) {
                    FragmentManager fm = getSupportFragmentManager();
                    Fragment f = fm.findFragmentById(R.id.activity_main_frame_layout_list);
                    if (f instanceof SearchFragment) {
                        onBackPressed();
                    } else {
                        addFragment(
                                R.id.activity_main_frame_layout_list,
                                new SearchFragment(),
                                R.anim.slide_in_top,
                                R.anim.slide_out_bottom
                        );
                    }
                } else {
                    FragmentManager fm = getSupportFragmentManager();
                    Fragment f = fm.findFragmentById(R.id.activity_main_frame_layout_global);
                    if (f instanceof SearchFragment) {
                        onBackPressed();
                    } else {
                        addFragment(
                                R.id.activity_main_frame_layout_global,
                                new SearchFragment(),
                                R.anim.slide_in_top,
                                R.anim.slide_out_bottom
                        );
                    }
                }
                return true;
            case R.id.activity_main_menu_loan_simulator:
                startActivity(new Intent(this, LoanActivity.class));
                return true;
            case R.id.activity_main_menu_map:
                if (Utils.isNetworkAvailable(this)) {
                    askLocationPermission();
                } else {
                    Toast.makeText(this, "Connexion internet indisponible", Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (!isTablet) {
            FragmentManager fm = getSupportFragmentManager();
            Fragment f = fm.findFragmentById(R.id.activity_main_frame_layout_list);
            if (f instanceof SearchFragment || f instanceof EstateDetailsFragment) {
                super.onBackPressed();
                return;
            }
            finishAffinity();
        } else {
            FragmentManager fm = getSupportFragmentManager();
            Fragment f = fm.findFragmentById(R.id.activity_main_frame_layout_global);
            if (f instanceof SearchFragment) {
                super.onBackPressed();
                return;
            }
            finishAffinity();
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

    @SuppressLint("MissingPermission")
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

    @OnClick(R.id.activity_main_btn_reinit_filters)
    public void reinitFilters() {
        estateViewModel.initCurrentEstateList();
        hideReinitFiltersBtn();
    }

    public void showReinitFiltersBtn() {
        btnReinitFilters.setVisibility(View.VISIBLE);
        hideDetailsFragment();
    }

    public void hideReinitFiltersBtn() {
        btnReinitFilters.setVisibility(View.GONE);
    }

    public boolean isTablet() {
        return isTablet;
    }

    public void hideDetailsFragment() {
        // hide details if tablet
        if (isTablet) {
            FragmentManager fm = getSupportFragmentManager();
            Fragment f = fm.findFragmentById(R.id.activity_main_frame_layout_detail);
            if (f instanceof EstateDetailsFragment) {
                fm.beginTransaction().remove(fm.findFragmentById(R.id.activity_main_frame_layout_detail)).commit();
            }
        }
    }
}
