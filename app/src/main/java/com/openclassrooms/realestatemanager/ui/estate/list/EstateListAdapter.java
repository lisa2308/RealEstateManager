package com.openclassrooms.realestatemanager.ui.estate.list;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.data.entities.Estate;
import com.openclassrooms.realestatemanager.utils.RecyclerViewHolderListener;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EstateListAdapter extends RecyclerView.Adapter<EstateListAdapter.EstateListHolder> {

    private List<Estate> estateList;
    private RecyclerViewHolderListener clickListener;
    private DecimalFormat formatter = new DecimalFormat("#,### €");

    public EstateListAdapter(List<Estate> estateList,
                             RecyclerViewHolderListener clickListener) {
        this.estateList = estateList;
        this.clickListener = clickListener;
    }

    public static class EstateListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fragment_estate_list_item_main_picture)
        ImageView mainPicture;

        @BindView(R.id.fragment_estate_list_item_view_filter_sold)
        View viewFilter;

        @BindView(R.id.fragment_estate_list_item_txt_filter_sold)
        TextView txtFilter;

        @BindView(R.id.fragment_estate_list_item_type)
        TextView txtType;

        @BindView(R.id.fragment_estate_list_item_city)
        TextView txtCity;

        @BindView(R.id.fragment_estate_list_item_price)
        TextView txtPrice;

        public EstateListHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public EstateListHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_estate_list_item, parent, false);

        return new EstateListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final EstateListHolder holder, final int position) {
        final Estate estate = estateList.get(position);

        if (estate.getEstateSoldDate() != null) {
            holder.viewFilter.setVisibility(View.VISIBLE);
            holder.txtFilter.setVisibility(View.VISIBLE);
        } else {
            holder.viewFilter.setVisibility(View.GONE);
            holder.txtFilter.setVisibility(View.GONE);
        }

        holder.txtType.setText(estate.getEstateType().getType());
        holder.txtCity.setText(estate.getEstateCity());
        holder.txtPrice.setText(formatter.format(estate.getEstatePrice()));

        if (estate.getEstateMainPicture() != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(estate.getEstateMainPicture(), 0, estate.getEstateMainPicture().length);
            holder.mainPicture.setImageBitmap(bmp);
        }

        holder.itemView.setOnClickListener(v -> clickListener.onItemClicked(holder, estate, position));
    }

    @Override
    public int getItemCount() {
        return estateList.size();
    }

    public void setData(List<Estate> newData) {
        this.estateList = newData;
        notifyDataSetChanged();
    }
}

