package com.openclassrooms.realestatemanager.data.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.data.room.repo.EstateDataRepository;
import com.openclassrooms.realestatemanager.data.room.repo.EstatePictureDataRepository;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final EstateDataRepository estateDataRepository;
    private final EstatePictureDataRepository estatePictureDataRepository;
    private final Executor executor;

    public ViewModelFactory(EstateDataRepository estateDataRepository,
                            EstatePictureDataRepository estatePictureDataRepository,
                            Executor executor) {
        this.estateDataRepository = estateDataRepository;
        this.estatePictureDataRepository = estatePictureDataRepository;
        this.executor = executor;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EstateViewModel.class)) {
            return (T) new EstateViewModel(estateDataRepository, estatePictureDataRepository, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
