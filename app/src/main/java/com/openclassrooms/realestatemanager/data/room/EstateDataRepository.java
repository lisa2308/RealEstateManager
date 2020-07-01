package com.openclassrooms.realestatemanager.data.room;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.realestatemanager.data.entities.Estate;

import java.util.List;

public class EstateDataRepository {

    private final EstateDao estateDao;

    public EstateDataRepository(EstateDao estateDao) {
        this.estateDao = estateDao;
    }

    public LiveData<List<Estate>> getEstateList() {
        return estateDao.getAllEstates();
    }
}
