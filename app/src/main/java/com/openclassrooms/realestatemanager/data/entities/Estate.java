package com.openclassrooms.realestatemanager.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.openclassrooms.realestatemanager.data.room.typeconverter.DateTypeConverter;
import com.openclassrooms.realestatemanager.data.room.typeconverter.EstateTypeConverter;

import java.util.Date;

@Entity
public class Estate {

    @PrimaryKey(autoGenerate = true)
    private long estateId;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] estateMainPicture;
    @TypeConverters(EstateTypeConverter.class)
    private EstateType estateType;
    private double estatePrice;
    private String estateDescription;
    private String estateSurface;
    private int estateNbRooms;
    private int estateNbBathrooms;
    private int estateNbBedrooms;
    private String estateStreet;
    private String estatePostal;
    private String estateCity;
    private String estateCountry;
    private double estateLat;
    private double estateLng;
    private String estatePointsOfInterest;
    private boolean estateHasBeenSold;
    @TypeConverters(DateTypeConverter.class)
    private Date estateCreationDate;
    @TypeConverters(DateTypeConverter.class)
    private Date estateSoldDate;
    private String estateAgentName;

    public Estate() {}

    public long getEstateId() {
        return estateId;
    }

    public void setEstateId(long estateId) {
        this.estateId = estateId;
    }

    public byte[] getEstateMainPicture() {
        return estateMainPicture;
    }

    public void setEstateMainPicture(byte[] estateMainPicture) {
        this.estateMainPicture = estateMainPicture;
    }

    public EstateType getEstateType() {
        return estateType;
    }

    public void setEstateType(EstateType estateType) {
        this.estateType = estateType;
    }

    public double getEstatePrice() {
        return estatePrice;
    }

    public void setEstatePrice(double estatePrice) {
        this.estatePrice = estatePrice;
    }

    public String getEstateDescription() {
        return estateDescription;
    }

    public void setEstateDescription(String estateDescription) {
        this.estateDescription = estateDescription;
    }

    public String getEstateSurface() {
        return estateSurface;
    }

    public void setEstateSurface(String estateSurface) {
        this.estateSurface = estateSurface;
    }

    public int getEstateNbRooms() {
        return estateNbRooms;
    }

    public void setEstateNbRooms(int estateNbRooms) {
        this.estateNbRooms = estateNbRooms;
    }

    public int getEstateNbBathrooms() {
        return estateNbBathrooms;
    }

    public void setEstateNbBathrooms(int estateNbBathrooms) {
        this.estateNbBathrooms = estateNbBathrooms;
    }

    public int getEstateNbBedrooms() {
        return estateNbBedrooms;
    }

    public void setEstateNbBedrooms(int estateNbBedrooms) {
        this.estateNbBedrooms = estateNbBedrooms;
    }

    public String getEstateStreet() {
        return estateStreet;
    }

    public void setEstateStreet(String estateStreet) {
        this.estateStreet = estateStreet;
    }

    public String getEstatePostal() {
        return estatePostal;
    }

    public void setEstatePostal(String estatePostal) {
        this.estatePostal = estatePostal;
    }

    public String getEstateCity() {
        return estateCity;
    }

    public void setEstateCity(String estateCity) {
        this.estateCity = estateCity;
    }

    public String getEstateCountry() {
        return estateCountry;
    }

    public void setEstateCountry(String estateCountry) {
        this.estateCountry = estateCountry;
    }

    public double getEstateLat() {
        return estateLat;
    }

    public void setEstateLat(double estateLat) {
        this.estateLat = estateLat;
    }

    public double getEstateLng() {
        return estateLng;
    }

    public void setEstateLng(double estateLng) {
        this.estateLng = estateLng;
    }

    public String getEstatePointsOfInterest() {
        return estatePointsOfInterest;
    }

    public void setEstatePointsOfInterest(String estatePointsOfInterest) {
        this.estatePointsOfInterest = estatePointsOfInterest;
    }

    public boolean isEstateHasBeenSold() {
        return estateHasBeenSold;
    }

    public void setEstateHasBeenSold(boolean estateHasBeenSold) {
        this.estateHasBeenSold = estateHasBeenSold;
    }

    public Date getEstateCreationDate() {
        return estateCreationDate;
    }

    public void setEstateCreationDate(Date estateCreationDate) {
        this.estateCreationDate = estateCreationDate;
    }

    public Date getEstateSoldDate() {
        return estateSoldDate;
    }

    public void setEstateSoldDate(Date estateSoldDate) {
        this.estateSoldDate = estateSoldDate;
    }

    public String getEstateAgentName() {
        return estateAgentName;
    }

    public void setEstateAgentName(String estateAgentName) {
        this.estateAgentName = estateAgentName;
    }
}
