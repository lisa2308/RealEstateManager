package com.openclassrooms.realestatemanager.di;

import android.content.Context;

import com.openclassrooms.realestatemanager.data.room.repo.EstateDataRepository;
import com.openclassrooms.realestatemanager.data.room.EstateRoomDatabase;
import com.openclassrooms.realestatemanager.data.room.repo.EstatePictureDataRepository;
import com.openclassrooms.realestatemanager.data.viewmodel.ViewModelFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static EstateDataRepository provideEstateDataRepository(Context context) {
        EstateRoomDatabase database = EstateRoomDatabase.getInstance(context);
        return new EstateDataRepository(database.estateDao());
    }

    public static EstatePictureDataRepository provideEstatePictureDataRepository(Context context) {
        EstateRoomDatabase database = EstateRoomDatabase.getInstance(context);
        return new EstatePictureDataRepository(database.estatePictureDao());
    }

    public static Executor provideExecutor() { return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        EstateDataRepository dataSourceEstate = provideEstateDataRepository(context);
        EstatePictureDataRepository dataSourceEstatePicture = provideEstatePictureDataRepository(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceEstate, dataSourceEstatePicture, executor);
    }
}
