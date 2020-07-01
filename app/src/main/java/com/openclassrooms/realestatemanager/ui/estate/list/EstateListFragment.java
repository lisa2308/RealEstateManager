package com.openclassrooms.realestatemanager.ui.estate.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_estate_list, container, false);
        ButterKnife.bind(this, view);

        configureViewModel();

        initRecycler();
        updateAdapter(estateList);

        return view;
    }

    private void configureViewModel(){
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getActivity());
        this.estateViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EstateViewModel.class);
        this.estateViewModel.init();
    }

    private void initRecycler() {

        RecyclerViewHolderListener clickListener = (viewHolder, item, pos) -> {
            Estate estate = (Estate) item;
        };

        estateListAdapter = new EstateListAdapter(estateViewModel.getCurrentEstateList().getValue(), clickListener);
        recyclerView.setAdapter(estateListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void updateAdapter(List<Estate> estateList) {
        estateListAdapter.setData(estateList);
    }

}
