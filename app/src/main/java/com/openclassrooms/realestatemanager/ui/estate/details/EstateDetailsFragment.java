package com.openclassrooms.realestatemanager.ui.estate.details;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.data.entities.Estate;
import com.openclassrooms.realestatemanager.data.entities.EstatePicture;
import com.openclassrooms.realestatemanager.data.viewmodel.EstateViewModel;
import com.openclassrooms.realestatemanager.data.viewmodel.ViewModelFactory;
import com.openclassrooms.realestatemanager.di.Injection;
import com.openclassrooms.realestatemanager.ui.MainActivity;
import com.openclassrooms.realestatemanager.ui.estate.addupdate.EstateAddUpdateActivity;
import com.openclassrooms.realestatemanager.ui.estate.list.EstateListAdapter;
import com.openclassrooms.realestatemanager.utils.RecyclerViewHolderListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EstateDetailsFragment extends Fragment implements OnMapReadyCallback {

    @BindView(R.id.fragment_estate_details_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.fragment_estate_details_txt_description)
    TextView txtDescription;

    @BindView(R.id.fragment_estate_details_txt_surface)
    TextView txtSurface;

    @BindView(R.id.fragment_estate_details_txt_rooms)
    TextView txtRooms;

    @BindView(R.id.fragment_estate_details_txt_bathroom)
    TextView txtBathroom;

    @BindView(R.id.fragment_estate_details_txt_bedroom)
    TextView txtBedroom;

    @BindView(R.id.fragment_estate_details_txt_street)
    TextView txtStreet;

    @BindView(R.id.fragment_estate_details_txt_postal)
    TextView txtPostal;

    @BindView(R.id.fragment_estate_details_txt_city)
    TextView txtCity;

    @BindView(R.id.fragment_estate_details_txt_country)
    TextView txtCountry;

    @BindView(R.id.fragment_estate_details_map)
    MapView mapView;

    private static final String ESTATE_ID = "estateId";
    private String estateId;
    private EstateDetailsAdapter estateListAdapter;

    private GoogleMap googleMap;
    MainActivity mainActivity;

    public EstateDetailsFragment() {}

    public static EstateDetailsFragment newInstance(String param) {
        EstateDetailsFragment fragment = new EstateDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ESTATE_ID, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            estateId = getArguments().getString(ESTATE_ID);
        }
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_estate_details, container, false);
        ButterKnife.bind(this, view);

        initRecycler();
        setupMapView(savedInstanceState);
        observeEstateViewModel();

        return view;
    }

    private void initRecycler() {
        estateListAdapter = new EstateDetailsAdapter(new ArrayList<>());
        recyclerView.setAdapter(estateListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void setupMapView(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    private void observeEstateViewModel() {
        if (mainActivity.getViewModel().getCurrentEstateList() != null) {
            mainActivity.getViewModel().getCurrentEstateList().observe(this, estateList -> {
                for (Estate estate : estateList) {
                    if (String.valueOf(estate.getEstateId()).equals(estateId)) {
                        updateViews(estate);
                        break;
                    }
                }
            });
        }

        mainActivity.getViewModel().getCurrentEstatePictureList(Long.parseLong(estateId)).observe(this, estatePictureList -> {
            updateAdapter(estatePictureList);
        });
    }

    private void updateViews(Estate estate) {
        txtDescription.setText(estate.getEstateDescription());
        txtSurface.setText(estate.getEstateSurface());
        txtRooms.setText(String.valueOf(estate.getEstateNbRooms()));
        txtBathroom.setText(String.valueOf(estate.getEstateNbBathrooms()));
        txtBedroom.setText(String.valueOf(estate.getEstateNbBedrooms()));
        txtStreet.setText(estate.getEstateStreet());
        txtPostal.setText(estate.getEstatePostal());
        txtCity.setText(estate.getEstateCity());
        txtCountry.setText(estate.getEstateCountry());

        showEstateOnMap(estate);
    }

    private void updateAdapter(List<EstatePicture> estatePictureList) {
        estateListAdapter.setData(estatePictureList);
    }

    private void showEstateOnMap(Estate estate) {
        if (this.googleMap == null) {
            return;
        }

        LatLng latLng = new LatLng(
          estate.getEstateLat(),
          estate.getEstateLng()
        );

        this.googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.getUiSettings().setAllGesturesEnabled(false);
        this.googleMap.getUiSettings().setZoomControlsEnabled(false);
        this.googleMap.getUiSettings().setZoomGesturesEnabled(false);
        this.googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        this.googleMap.getUiSettings().setCompassEnabled(false);
        this.googleMap.getUiSettings().setMapToolbarEnabled(false);
    }

    @OnClick(R.id.fragment_estate_details_img_edit)
    public void editEstate() {
        Intent intent = new Intent(getContext(), EstateAddUpdateActivity.class);
        intent.putExtra("estateId", estateId);
        startActivity(intent);
    }
}
