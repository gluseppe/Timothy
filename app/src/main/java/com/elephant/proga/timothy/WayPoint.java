package com.elephant.proga.timothy;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by gluse on 20/02/15.
 */
public class WayPoint {

    private LatLng position;
    private double altitude;
    private String name;
    private boolean known;


    private Marker marker;

    public WayPoint(LatLng position, double altitude, Marker marker, boolean known, String name) {
        this.position = position;
        this.altitude = altitude;
        this.marker = marker;
        this.known = known;
        this.name = name;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public boolean isKnown() {
        return known;
    }

    public void setKnown(boolean known) {
        this.known = known;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
