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
import com.openclassrooms.realestatemanager.ui.MainActivity;
import com.openclassrooms.realestatemanager.ui.estate.details.EstateDetailsFragment;
import com.openclassrooms.realestatemanager.utils.RecyclerViewHolderListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EstateListFragment extends Fragment {

    @BindView(R.id.fragment_estate_list_recycler)
    RecyclerView recyclerView;

    private EstateListAdapter estateListAdapter;

    MainActivity mainActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
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
            MainActivity activity = (MainActivity) getActivity();
            activity.addFragment(
                R.id.activity_main_frame_layout_list,
                EstateDetailsFragment.newInstance(String.valueOf(estate.getEstateId())),
                R.anim.slide_in_left,
                R.anim.slide_out_right
            );
        };

        estateListAdapter = new EstateListAdapter(new ArrayList<>(), clickListener);
        recyclerView.setAdapter(estateListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void updateAdapter(List<Estate> estateList) {
        estateListAdapter.setData(estateList);
    }

    private void observeEstateViewModel() {
        if (mainActivity.getViewModel().getCurrentEstateList() != null) {
            mainActivity.getViewModel().getCurrentEstateList().observe(this, estateList -> {
                updateAdapter(estateList);
            });
        }
    }

}
