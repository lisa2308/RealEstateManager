package com.openclassrooms.realestatemanager.data.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.data.entities.Estate;
import com.openclassrooms.realestatemanager.data.entities.EstatePicture;
import com.openclassrooms.realestatemanager.data.room.repo.EstateDataRepository;
import com.openclassrooms.realestatemanager.data.room.repo.EstatePictureDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class EstateViewModel extends ViewModel {

    // REPOSITORIES
    private final EstateDataRepository estateDataRepository;
    private final EstatePictureDataRepository estatePictureDataRepository;
    private final Executor executor;

    // DATA
    private LiveData<List<Estate>> currentEstateList;

    public EstateViewModel(EstateDataRepository estateDataRepository,
                           EstatePictureDataRepository estatePictureDataRepository,
                           Executor executor) {
        this.estateDataRepository = estateDataRepository;
        this.estatePictureDataRepository = estatePictureDataRepository;
        this.executor = executor;
    }

    public LiveData<List<Estate>> getCurrentEstateList() {
        return estateDataRepository.getEstateList();
    }

    public long createEstate(Estate estate) {
        return estateDataRepository.createEstate(estate);
    }

    public long createEstatePicture(EstatePicture estatePicture) {
        return estatePictureDataRepository.createEstatePicture(estatePicture);
    }

    public void updateEstate(long estateId ) {

    }
}