package com.openclassrooms.realestatemanager.data.room.repo;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.data.entities.Estate;
import com.openclassrooms.realestatemanager.data.entities.EstatePicture;
import com.openclassrooms.realestatemanager.data.room.dao.EstateDao;
import com.openclassrooms.realestatemanager.data.room.dao.EstatePictureDao;

import java.util.List;

public class EstatePictureDataRepository {

    private final EstatePictureDao estatePictureDao;

    public EstatePictureDataRepository(EstatePictureDao estatePictureDao) {
        this.estatePictureDao = estatePictureDao;
    }

    public LiveData<List<EstatePicture>> getEstatePictureList(long estateId) {
        return estatePictureDao.getAllEstatePictures(estateId);
    }

    public long createEstatePicture(EstatePicture estatePicture) {
        return estatePictureDao.insertEstatePicture(estatePicture);
    }

    public void updateEstatePicture(EstatePicture estatePicture) {
        estatePictureDao.updateEstatePicture(estatePicture);
    }
}
