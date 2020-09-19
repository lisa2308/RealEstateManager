package com.openclassrooms.realestatemanager.data.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.sqlite.db.SupportSQLiteQuery;

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
    private MutableLiveData<List<Estate>> searchEstateList = new MutableLiveData<>();

    public EstateViewModel(EstateDataRepository estateDataRepository,
                           EstatePictureDataRepository estatePictureDataRepository,
                           Executor executor) {
        this.estateDataRepository = estateDataRepository;
        this.estatePictureDataRepository = estatePictureDataRepository;
        this.executor = executor;
    }

    public void initCurrentEstateList() {
        searchEstateList.setValue(estateDataRepository.getEstateList());
    }

    public MutableLiveData<List<Estate>> getCurrentEstateList() {
        return searchEstateList;
    }

    public void getSearchEstateList(SupportSQLiteQuery sqLiteQuery) {
        searchEstateList.setValue(estateDataRepository.getSearchEstateList(sqLiteQuery));
    }

    public Estate getEstate(long estateId) {
        return estateDataRepository.getEstate(estateId);
    }

    public long createEstate(Estate estate) {
        return estateDataRepository.createEstate(estate);
    }

    public void updateEstate(Estate estate) {
        estateDataRepository.updateEstate(estate);
    }

    public LiveData<List<EstatePicture>> getCurrentEstatePictureList(long estateId) {
        return estatePictureDataRepository.getEstatePictureList(estateId);
    }

    public long createEstatePicture(EstatePicture estatePicture) {
        return estatePictureDataRepository.createEstatePicture(estatePicture);
    }

    public void updateEstatePicture(EstatePicture estatePicture) {
        estatePictureDataRepository.updateEstatePicture(estatePicture);
    }
}