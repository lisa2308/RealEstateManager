package com.openclassrooms.realestatemanager.data.room;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.data.entities.Estate;

import java.util.List;

@Dao
public interface EstateDao {

    @Query("SELECT * FROM Estate")
    LiveData<List<Estate>> getAllEstates();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertEstate(Estate estate);

    @Update
    int updateEstate(Estate estate);

    @Query("DELETE FROM Estate WHERE estateId = :estateId")
    int deleteEstate(int estateId);
}
