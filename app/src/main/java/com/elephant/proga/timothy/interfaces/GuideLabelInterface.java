package com.elephant.proga.timothy.interfaces;

import com.elephant.proga.timothy.WayPoint;

import java.util.ArrayList;

/**
 * Created by gluse on 20/02/15.
 */
public interface GuideLabelInterface {

    public abstract void onStartSelect(WayPoint start);
    public abstract void onDestinationSelect(WayPoint destination);
    public abstract void onIntermediateWayPointSelect(WayPoint wp);
    public abstract void onFlightPlanChange(ArrayList<WayPoint> flightPLan);


    //actions from the guidelabel
    public abstract void startAgain();
    public abstract void removeLastWayPoint();

    public abstract void setGuideLabelUser(GuideLabelUser labelUser);

    public abstract void setStatus(int status);


}
