package com.elephant.proga.timothy.interfaces;

import com.elephant.proga.timothy.WayPoint;

import java.util.ArrayList;

/**
 * Created by gluse on 24/02/15.
 */
public interface KnownPointsHandler {
    public abstract void onKnownPointsReceived(ArrayList<WayPoint> wayPoints);
}
