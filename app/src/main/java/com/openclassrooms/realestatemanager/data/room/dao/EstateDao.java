package com.openclassrooms.realestatemanager.data.room.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.openclassrooms.realestatemanager.data.entities.Estate;

import java.util.List;

@Dao
public interface EstateDao {

    @Query("SELECT * FROM Estate WHERE estateId = :estateId")
    Estate getEstate(long estateId);

    @Query("SELECT * FROM Estate")
    List<Estate> getAllEstates();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertEstate(Estate estate);

    @Update
    int updateEstate(Estate estate);

    @Query("DELETE FROM Estate WHERE estateId = :estateId")
    int deleteEstate(long estateId);

    @RawQuery(observedEntities = Estate.class)
    List<Estate> getSearchEstates(SupportSQLiteQuery query);

    // CONTENT PROVIDER
    @Query("SELECT * FROM Estate WHERE estateId = :estateId")
    Cursor getEstateCursor(long estateId);
}
