package com.openclassrooms.realestatemanager.di;

import android.content.Context;

import com.openclassrooms.realestatemanager.data.room.repo.EstateDataRepository;
import com.openclassrooms.realestatemanager.data.room.EstateRoomDatabase;
import com.openclassrooms.realestatemanager.data.viewmodel.ViewModelFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static EstateDataRepository provideEstateDateRepository(Context context) {
        EstateRoomDatabase database = EstateRoomDatabase.getInstance(context);
        return new EstateDataRepository(database.estateDao());
    }

    public static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        EstateDataRepository dataSourceEstate = provideEstateDateRepository(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceEstate, executor);
    }
}
