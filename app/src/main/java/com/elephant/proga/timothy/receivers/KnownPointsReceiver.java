package com.elephant.proga.timothy.receivers;

import android.util.Log;

import com.elephant.proga.timothy.WayPoint;
import com.elephant.proga.timothy.interfaces.KnownPointsHandler;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by gluse on 24/02/15.
 */
public class KnownPointsReceiver extends Receiver {

    private KnownPointsHandler handler;

    public KnownPointsReceiver(KnownPointsHandler handler, String source, long sleeptime) {
        super(source, sleeptime);
        this.handler = handler;
    }

    public boolean setRequestParams(String queryname) {
        this.source = this.source + buildRequestString(queryname);
        return true;
    }

    private String buildRequestString(String queryname) {
        return "?" + "item=knownpoints&" + "queryname=" + queryname;
    }


    @Override
    public void run() {
        this.content = GET();
        if (this.content != null) {
            JSONObject jpoints = this.toJSON(content);
            if (jpoints == null)
                Log.d("KNOWNPOINTS", "SOMETHING WENT WRONG RECEIVING HOTSPOTS");
                //this.handler.onPredictionFailed(PredictionReceiver.PREDICTION_FAILED_STRING_TO_JSON);
            else {

                try {
                    JSONArray jpoints_arr = jpoints.getJSONArray("known_points");
                    ArrayList<WayPoint> wayPoints = new ArrayList();
                    for (int i=0; i < jpoints_arr.length(); i++){
                        JSONObject jWP = jpoints_arr.getJSONObject(i);
                        double lat = jWP.getDouble("lat");
                        double lon = jWP.getDouble("lon");
                        double h = jWP.getDouble("h");
                        String name =jWP.getString("name");
                        jWP = null;
                        WayPoint wp = new WayPoint(new LatLng(lat,lon), h, null, true, name);
                        wayPoints.add(wp);
                    }
                    jpoints = null;
                    jpoints_arr = null;
                    this.handler.onKnownPointsReceived(wayPoints);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
        else
            //this.handler.onPredictionFailed(PredictionReceiver.PREDICTION_FAILED_SERVER_RETURNED_NULL);
            Log.d("HOTSPOTS","EMPTY CONTENT");

    }
}
