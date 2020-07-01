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

import java.util.Date;

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
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // ---

    private static Callback prepopulateDatabase(){
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValues = new ContentValues();
                contentValues.put("estateId", 1);
                contentValues.put("estateMainPicture", "");
                contentValues.put("estateType", EstateType.VILLA.getType());
                contentValues.put("estatePrice", 1750000d);
                contentValues.put("estateDescription", "Villa luxe de ouf on se met bien");
                contentValues.put("estateSurface", "700m2");
                contentValues.put("estateNbRooms", 10);
                contentValues.put("estateNbBathrooms", 4);
                contentValues.put("estateNbBedrooms", 5);
                contentValues.put("estateStreet", "18 rue Rivoli");
                contentValues.put("estatePostal", "75001");
                contentValues.put("estateCity", "Paris");
                contentValues.put("estateCountry", "France");
                contentValues.put("estatePointsOfInterest", "school parcs");
                contentValues.put("estateHasBeenSold", false);
                contentValues.put("estateCreationDate", "04/04/2020");
                contentValues.put("estateSoldDate", "null");
                contentValues.put("estateAgentName", "Anthony Fillion-Maillet");

                db.insert("Estate", OnConflictStrategy.IGNORE, contentValues);
            }
        };
    }
}
