package com.elephant.proga.timothy;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elephant.proga.timothy.interfaces.GuideLabelInterface;
import com.elephant.proga.timothy.interfaces.GuideLabelUser;

import java.util.ArrayList;

/**
 * Created by gluse on 20/02/15.
 */
public class GuideLabel extends Fragment implements GuideLabelInterface,View.OnClickListener {

    private GuideLabelUser labelUser;
    private TextView hint;
    private View labelView;
    private int status;

    public GuideLabel() {
        super();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.guide_label_layout,container,false);
        this.hint = (TextView) v.findViewById(R.id.hint);
        v.setOnClickListener(this);
        this.labelView = v;
        v.setTranslationX(0);
        v.setTranslationY(650);
        return v;
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onStartSelect(WayPoint start) {

    }

    @Override
    public void onDestinationSelect(WayPoint destination) {

    }

    @Override
    public void onIntermediateWayPointSelect(WayPoint wp) {

    }

    @Override
    public void onFlightPlanChange(ArrayList<WayPoint> flightPLan) {

    }

    @Override
    public void startAgain() {

    }

    @Override
    public void removeLastWayPoint() {

    }

    @Override
    public void setGuideLabelUser(GuideLabelUser labelUser) {
        this.labelUser = labelUser;

    }

    @Override
    public void onClick(View v) {

    }

    public void setPosition(float x, float y) {
        this.getView().setTranslationX(x);
        this.getView().setTranslationY(y);

    }

    @Override
    public void setStatus(int status) {
        this.status = status;
        switch(this.status) {
            case Status.NO_STARTING_POINT: {
                this.hint.setText("Please select your starting point from the airfields on the map");
                break;
            }
            case Status.STARTING_POINT_SELECTED: {
                this.hint.setText("Please select your destination from the airfields on the map");
                break;
            }

            case Status.END_POINT_SELECTED: {
                this.hint.setText("Now select your intermediate points anywhere on the map");
                break;
            }

        }

    }
}
