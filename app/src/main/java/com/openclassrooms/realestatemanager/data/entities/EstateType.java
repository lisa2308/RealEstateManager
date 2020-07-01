package com.openclassrooms.realestatemanager.data.entities;

public enum EstateType {
    FLAT("Flat"),
    HOUSE("House"),
    DUPLEX("Duplex"),
    PENTHOUSE("Penthouse"),
    VILLA("Villa"),
    MANSION("Mansion");

    private String type;

    EstateType(String type) {
        this.type = type ;
    }

    public String getType() {
        return this.type ;
    }
}
