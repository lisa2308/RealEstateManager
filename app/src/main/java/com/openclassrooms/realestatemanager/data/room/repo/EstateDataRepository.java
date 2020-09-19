package com.openclassrooms.realestatemanager.data.room.repo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.openclassrooms.realestatemanager.data.entities.Estate;
import com.openclassrooms.realestatemanager.data.room.dao.EstateDao;

import java.util.List;

public class EstateDataRepository {

    private final EstateDao estateDao;

    public EstateDataRepository(EstateDao estateDao) {
        this.estateDao = estateDao;
    }

    public Estate getEstate(long estateId) {
        return estateDao.getEstate(estateId);
    }
    public List<Estate> getEstateList() {
        return estateDao.getAllEstates();
    }

    public List<Estate> getSearchEstateList(SupportSQLiteQuery sqLiteQuery) {
        return estateDao.getSearchEstates(sqLiteQuery);
    }

    public long createEstate(Estate estate) {
        return estateDao.insertEstate(estate);
    }

    public void updateEstate(Estate estate) {
        estateDao.updateEstate(estate);
    }
}
