package com.elephant.proga.timothy.interfaces;

import android.graphics.Point;

import com.elephant.proga.timothy.WayPoint;

/**
 * Created by gluse on 20/02/15.
 */
public interface WayPointInfoLabelInterface {

    public abstract void onWayPointTouch(WayPoint wp);
    public abstract void onGenericPointTouch(WayPoint wp);
    public abstract void hideLabel();
    public abstract void showLabel(WayPoint wp);

    public abstract Point getSize();

    //public abstract WayPoint addWayPoint();

    public abstract void setLabelUser(WayPointInfoLabelUser labelUser);
    public abstract void setStatus(int status);
    public abstract void setCurrentWayPoint(WayPoint wp);


}
