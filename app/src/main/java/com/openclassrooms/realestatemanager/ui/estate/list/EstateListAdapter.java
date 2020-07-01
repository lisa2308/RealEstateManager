package com.openclassrooms.realestatemanager.ui.estate.list;

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

public class EstateListAdapter extends RecyclerView.Adapter<EstateListAdapter.FragmentEstateHolder> {

    private List<Estate> estateList;
    private RecyclerViewHolderListener clickListener;
    private DecimalFormat formatter = new DecimalFormat("#,### €");

    public EstateListAdapter(List<Estate> estateList,
                             RecyclerViewHolderListener clickListener) {
        this.estateList = estateList;
        this.clickListener = clickListener;
    }

    public static class FragmentEstateHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fragment_estate_list_item_main_picture)
        ImageView mainPicture;

        @BindView(R.id.fragment_estate_list_item_type)
        TextView txtType;

        @BindView(R.id.fragment_estate_list_item_city)
        TextView txtCity;

        @BindView(R.id.fragment_estate_list_item_price)
        TextView txtPrice;

        public FragmentEstateHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public FragmentEstateHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_estate_list_item, parent, false);

        return new FragmentEstateHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FragmentEstateHolder holder, final int position) {
        final Estate estate = estateList.get(position);

        holder.txtType.setText(estate.getEstateType().getType());
        holder.txtCity.setText(estate.getEstateCity());
        holder.txtPrice.setText(formatter.format(estate.getEstatePrice()));

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

