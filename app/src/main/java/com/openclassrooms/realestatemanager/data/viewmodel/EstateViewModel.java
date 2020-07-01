package com.openclassrooms.realestatemanager.data.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.data.entities.Estate;
import com.openclassrooms.realestatemanager.data.room.EstateDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class EstateViewModel extends ViewModel {

    // REPOSITORIES
    private final EstateDataRepository estateDataRepository;
    private final Executor executor;

    // DATA
    private LiveData<List<Estate>> currentEstateList;

    public EstateViewModel(EstateDataRepository estateDataRepository, Executor executor) {
        this.estateDataRepository = estateDataRepository;
        this.executor = executor;
    }

    public void init() {
        if (this.currentEstateList != null) {
            return;
        }
        currentEstateList = estateDataRepository.getEstateList();
    }


    public LiveData<List<Estate>> getCurrentEstateList() {
        return this.currentEstateList;
    }

}