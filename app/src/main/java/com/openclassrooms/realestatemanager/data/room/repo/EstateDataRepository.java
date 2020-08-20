package com.openclassrooms.realestatemanager.data.room.repo;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.data.entities.Estate;
import com.openclassrooms.realestatemanager.data.room.dao.EstateDao;

import java.util.List;

public class EstateDataRepository {

    private final EstateDao estateDao;

    public EstateDataRepository(EstateDao estateDao) {
        this.estateDao = estateDao;
    }

    public LiveData<List<Estate>> getEstateList() {
        return estateDao.getAllEstates();
    }

    public long createEstate(Estate estate) {
        return estateDao.insertEstate(estate);
    }
}
