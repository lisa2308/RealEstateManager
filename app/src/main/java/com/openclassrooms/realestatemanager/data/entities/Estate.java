package com.openclassrooms.realestatemanager.data.entities;

import android.content.ContentValues;

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
    private int estatePrice;
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

    public int getEstatePrice() {
        return estatePrice;
    }

    public void setEstatePrice(int estatePrice) {
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

    // --- UTILS ---
    public static Estate fromContentValues(ContentValues values) {
        final Estate estate = new Estate();

        if (values.containsKey("estateId")) estate.setEstateId(values.getAsLong("estateId"));
        if (values.containsKey("estateMainPicture")) estate.setEstateMainPicture(values.getAsByteArray("estateMainPicture"));
        if (values.containsKey("estateType")) estate.setEstateType(EstateType.fromDisplayString(values.getAsString("estateType")));
        if (values.containsKey("estatePrice")) estate.setEstatePrice(values.getAsInteger("estatePrice"));
        if (values.containsKey("estateDescription")) estate.setEstateDescription(values.getAsString("estateDescription"));
        if (values.containsKey("estateSurface")) estate.setEstateSurface(values.getAsString("estateSurface"));
        if (values.containsKey("estateNbRooms")) estate.setEstateNbRooms(values.getAsInteger("estateNbRooms"));
        if (values.containsKey("estateNbBathrooms")) estate.setEstateNbBathrooms(values.getAsInteger("estateNbBathrooms"));
        if (values.containsKey("estateNbBedrooms")) estate.setEstateNbBedrooms(values.getAsInteger("estateNbBedrooms"));
        if (values.containsKey("estateStreet")) estate.setEstateStreet(values.getAsString("estateStreet"));
        if (values.containsKey("estatePostal")) estate.setEstatePostal(values.getAsString("estatePostal"));
        if (values.containsKey("estateCity")) estate.setEstateCity(values.getAsString("estateCity"));
        if (values.containsKey("estateCountry")) estate.setEstateCountry(values.getAsString("estateCountry"));
        if (values.containsKey("estateLat")) estate.setEstateLat(values.getAsDouble("estateLat"));
        if (values.containsKey("estateLng")) estate.setEstateLng(values.getAsDouble("estateLng"));
        if (values.containsKey("estatePointsOfInterest")) estate.setEstatePointsOfInterest(values.getAsString("estatePointsOfInterest"));
        if (values.containsKey("estateHasBeenSold")) estate.setEstateHasBeenSold(values.getAsBoolean("estateHasBeenSold"));
        if (values.containsKey("estateCreationDate")) estate.setEstateCreationDate(new Date(values.getAsLong("estateCreationDate")));
        if (values.containsKey("estateSoldDate")) estate.setEstateSoldDate(new Date(values.getAsLong("estateSoldDate")));
        if (values.containsKey("estateAgentName")) estate.setEstateAgentName(values.getAsString("estateAgentName"));

        return estate;
    }
}
