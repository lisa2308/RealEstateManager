package com.openclassrooms.realestatemanager.data.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Estate.class,
        parentColumns = "estateId",
        childColumns = "estatePictureEstateId"))
public class EstatePicture {

    @PrimaryKey(autoGenerate = true)
    private int estatePictureId;
    private int estatePictureEstateId;
    private int estatePictureImg;
    private String estatePictureDescription;

    public EstatePicture() {}

    public int getEstatePictureId() {
        return estatePictureId;
    }

    public void setEstatePictureId(int estatePictureId) {
        this.estatePictureId = estatePictureId;
    }

    public int getEstatePictureEstateId() {
        return estatePictureEstateId;
    }

    public void setEstatePictureEstateId(int estatePictureEstateId) {
        this.estatePictureEstateId = estatePictureEstateId;
    }

    public int getEstatePictureImg() {
        return estatePictureImg;
    }

    public void setEstatePictureImg(int estatePictureImg) {
        this.estatePictureImg = estatePictureImg;
    }

    public String getEstatePictureDescription() {
        return estatePictureDescription;
    }

    public void setEstatePictureDescription(String estatePictureDescription) {
        this.estatePictureDescription = estatePictureDescription;
    }
}
