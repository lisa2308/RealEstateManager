package com.openclassrooms.realestatemanager.ui.estate.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.openclassrooms.realestatemanager.R;

public class EstateDetailsFragment extends Fragment {

    private static final String ESTATE_ID = "estateId";
    private String mParam2;

    public EstateDetailsFragment() {}

    public static EstateDetailsFragment newInstance(String param2) {
        EstateDetailsFragment fragment = new EstateDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ESTATE_ID, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam2 = getArguments().getString(ESTATE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_estate_details, container, false);

        return view;
    }

}
