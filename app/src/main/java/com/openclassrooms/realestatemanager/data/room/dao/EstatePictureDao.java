package com.openclassrooms.realestatemanager.data.room.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.data.entities.Estate;
import com.openclassrooms.realestatemanager.data.entities.EstatePicture;

import java.util.List;

@Dao
public interface EstatePictureDao {

    @Query("SELECT * FROM EstatePicture WHERE estatePictureEstateId = :estateId")
    LiveData<List<EstatePicture>> getAllEstatePictures(int estateId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertEstatePicture(EstatePicture estatePicture);

    @Update
    int updateEstatePicture(EstatePicture estatePicture);

    @Query("DELETE FROM EstatePicture WHERE estatePictureId = :estatePictureId")
    int deleteEstatePicture(int estatePictureId);
}
