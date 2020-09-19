package com.openclassrooms.realestatemanager.ui.estate.details;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.data.entities.EstatePicture;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EstateDetailsAdapter extends RecyclerView.Adapter<EstateDetailsAdapter.EstateDetailsHolder> {

    private List<EstatePicture> estatePictureList;

    public EstateDetailsAdapter(List<EstatePicture> estatePictureList) {
        this.estatePictureList = estatePictureList;
    }

    public static class EstateDetailsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fragment_estate_details_item_img_pic)
        ImageView imgPicture;

        @BindView(R.id.fragment_estate_details_item_txt_description)
        TextView txtDescription;

        public EstateDetailsHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public EstateDetailsHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_estate_details_item, parent, false);

        return new EstateDetailsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final EstateDetailsHolder holder, final int position) {
        final EstatePicture estatePicture = estatePictureList.get(position);

        holder.txtDescription.setText(estatePicture.getEstatePictureDescription());

        if (estatePicture.getEstatePictureImg() != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(estatePicture.getEstatePictureImg(), 0, estatePicture.getEstatePictureImg().length);
            holder.imgPicture.setImageBitmap(bmp);
        }
    }

    @Override
    public int getItemCount() {
        return estatePictureList.size();
    }

    public void setData(List<EstatePicture> newData) {
        this.estatePictureList = newData;
        notifyDataSetChanged();
    }
}