package com.openclassrooms.realestatemanager.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Estate.class,
        parentColumns = "estateId",
        childColumns = "estatePictureEstateId"))
public class EstatePicture {

    @PrimaryKey(autoGenerate = true)
    private long estatePictureId;
    @ColumnInfo(name = "estatePictureEstateId", index = true)
    private long estatePictureEstateId;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] estatePictureImg;
    private String estatePictureDescription;

    public EstatePicture() {}

    public long getEstatePictureId() {
        return estatePictureId;
    }

    public void setEstatePictureId(long estatePictureId) {
        this.estatePictureId = estatePictureId;
    }

    public long getEstatePictureEstateId() {
        return estatePictureEstateId;
    }

    public void setEstatePictureEstateId(long estatePictureEstateId) {
        this.estatePictureEstateId = estatePictureEstateId;
    }

    public byte[] getEstatePictureImg() {
        return estatePictureImg;
    }

    public void setEstatePictureImg(byte[] estatePictureImg) {
        this.estatePictureImg = estatePictureImg;
    }

    public String getEstatePictureDescription() {
        return estatePictureDescription;
    }

    public void setEstatePictureDescription(String estatePictureDescription) {
        this.estatePictureDescription = estatePictureDescription;
    }
}
