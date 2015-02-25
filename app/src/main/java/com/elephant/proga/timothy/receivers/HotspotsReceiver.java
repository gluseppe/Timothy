package com.elephant.proga.timothy.receivers;

import android.util.Log;

import com.elephant.proga.timothy.interfaces.HotspotsHandler;

import org.json.JSONArray;

/**
 * Created by gluse on 04/02/15.
 */
public class HotspotsReceiver extends Receiver
{
    HotspotsHandler handler;
    public HotspotsReceiver(HotspotsHandler handler, String source, long sleeptime) {
        super(source, sleeptime);
        this.handler = handler;
    }

    @Override
    public void run() {
        this.content = GET();
        if (this.content != null) {
                JSONArray jhotspot = this.toJSONArray(content);
                if (jhotspot == null)
                    Log.d("HOTSPOTS","SOMETHING WENT WRONG RECEIVING HOTSPOTS");
                    //this.handler.onPredictionFailed(PredictionReceiver.PREDICTION_FAILED_STRING_TO_JSON);
                else {
                        this.handler.onHotspotReceived(jhotspot);
                }
        }
        else
            //this.handler.onPredictionFailed(PredictionReceiver.PREDICTION_FAILED_SERVER_RETURNED_NULL);
            Log.d("HOTSPOTS","EMPTY CONTENT");

    }
}
