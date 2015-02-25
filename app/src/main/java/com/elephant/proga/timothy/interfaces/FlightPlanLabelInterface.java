package com.elephant.proga.timothy.interfaces;

import com.elephant.proga.timothy.WayPoint;

import java.util.ArrayList;

/**
 * Created by gluse on 20/02/15.
 */
public interface FlightPlanLabelInterface {

    public abstract void onFlightPlanChange(ArrayList<WayPoint> flightPLan);

    public abstract void setLabelUser(FlightPlanLabelUser labelUser);
}
