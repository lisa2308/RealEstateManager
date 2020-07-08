package com.openclassrooms.realestatemanager.ui.estate.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.data.entities.Estate;
import com.openclassrooms.realestatemanager.data.entities.EstateType;
import com.openclassrooms.realestatemanager.data.room.EstateRoomDatabase;
import com.openclassrooms.realestatemanager.data.viewmodel.EstateViewModel;
import com.openclassrooms.realestatemanager.data.viewmodel.ViewModelFactory;
import com.openclassrooms.realestatemanager.di.Injection;
import com.openclassrooms.realestatemanager.utils.RecyclerViewHolderListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EstateListFragment extends Fragment {

    @BindView(R.id.fragment_estate_list_recycler)
    RecyclerView recyclerView;

    List<Estate> estateList = new ArrayList<>();
    EstateListAdapter estateListAdapter;

    EstateViewModel estateViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getActivity());
        estateViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EstateViewModel.class);
        estateViewModel.init();

//        Estate estate = new Estate();
//        estate.setEstateId(1);
//        estate.setEstateMainPicture("");
//        estate.setEstateType(EstateType.VILLA);
//        estate.setEstatePrice(1750000d);
//        estate.setEstateDescription("Villa luxe de ouf on se met bien");
//        estate.setEstateSurface("700m2");
//        estate.setEstateNbRooms(10);
//        estate.setEstateNbBathrooms(4);
//        estate.setEstateNbBedrooms(5);
//        estate.setEstateStreet("18 rue Rivoli");
//        estate.setEstatePostal("75001");
//        estate.setEstateCity("Paris");
//        estate.setEstateCountry("France");
//        estate.setEstatePointsOfInterest("school parcs");
//        estate.setEstateHasBeenSold(false);
//        estate.setEstateCreationDate(null);
//        estate.setEstateSoldDate(null);
//        estate.setEstateAgentName("Anthony Fillion-Maillet");
//        estateViewModel.createEstate(estate);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_estate_list, container, false);
        ButterKnife.bind(this, view);

        initRecycler();
        observeEstateViewModel();

        return view;
    }

    private void initRecycler() {

        RecyclerViewHolderListener clickListener = (viewHolder, item, pos) -> {
            Estate estate = (Estate) item;

        };

        estateListAdapter = new EstateListAdapter(new ArrayList<>(), clickListener);
        recyclerView.setAdapter(estateListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void updateAdapter(List<Estate> estateList) {
        estateListAdapter.setData(estateList);
    }

    private void observeEstateViewModel() {
        if (estateViewModel.getCurrentEstateList() != null) {
            estateViewModel.getCurrentEstateList().observe(this, estateList -> {
                updateAdapter(estateList);
            });
        }
    }

}
