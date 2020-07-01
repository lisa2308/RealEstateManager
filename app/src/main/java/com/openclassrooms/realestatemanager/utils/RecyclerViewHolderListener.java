package com.openclassrooms.realestatemanager.utils;

import androidx.recyclerview.widget.RecyclerView;

public interface RecyclerViewHolderListener<T, VH extends RecyclerView.ViewHolder> {
    void onItemClicked(VH vh, T item, int pos);
}

