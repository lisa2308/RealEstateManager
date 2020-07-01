package com.openclassrooms.realestatemanager.data.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.data.room.EstateDataRepository;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final EstateDataRepository estateDataRepository;
    private final Executor executor;

    public ViewModelFactory(EstateDataRepository estateDataRepository, Executor executor) {
        this.estateDataRepository = estateDataRepository;
        this.executor = executor;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EstateViewModel.class)) {
            return (T) new EstateViewModel(estateDataRepository, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
