package com.openclassrooms.realestatemanager.data.entities;

public enum EstatePointsOfInterest {
    SCHOOL("School"),
    SHOPS("Shops"),
    TRANSPORTS("Transports"),
    PARCS("Parcs");

    private String pointOfInterest;

    EstatePointsOfInterest(String pointOfInterest) {
        this.pointOfInterest = pointOfInterest ;
    }

    public String getPointOfInterest() {
        return this.pointOfInterest ;
    }
}
