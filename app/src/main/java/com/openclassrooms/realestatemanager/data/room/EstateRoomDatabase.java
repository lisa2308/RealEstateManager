package com.openclassrooms.realestatemanager.data.room;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.openclassrooms.realestatemanager.data.entities.Estate;
import com.openclassrooms.realestatemanager.data.entities.EstatePicture;
import com.openclassrooms.realestatemanager.data.entities.EstateType;
import com.openclassrooms.realestatemanager.data.room.dao.EstateDao;
import com.openclassrooms.realestatemanager.data.room.dao.EstatePictureDao;

@Database(entities = {Estate.class, EstatePicture.class}, version = 1, exportSchema = false)
public abstract class EstateRoomDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile EstateRoomDatabase INSTANCE;

    // --- DAO ---
    public abstract EstateDao estateDao();
    public abstract EstatePictureDao estatePictureDao();

    // --- INSTANCE ---
    public static EstateRoomDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (EstateRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            EstateRoomDatabase.class, "MyDatabase.db")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
