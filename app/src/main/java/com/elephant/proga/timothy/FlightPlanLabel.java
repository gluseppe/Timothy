package com.elephant.proga.timothy;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elephant.proga.timothy.interfaces.FlightPlanLabelInterface;
import com.elephant.proga.timothy.interfaces.FlightPlanLabelUser;
import com.elephant.proga.timothy.interfaces.GuideLabelInterface;
import com.elephant.proga.timothy.interfaces.GuideLabelUser;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by gluse on 20/02/15.
 */
public class FlightPlanLabel extends Fragment implements FlightPlanLabelInterface,View.OnClickListener {

    private TextView flightPlanTextView;
    private View labelView;

    public FlightPlanLabel() {
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
        View v = inflater.inflate(R.layout.flightplan_label_layout,container,false);

        this.flightPlanTextView = (TextView) v.findViewById(R.id.flightplan_textview);
        v.setOnClickListener(this);
        this.labelView = v;
        v.setTranslationX(0);
        v.setTranslationY(850);
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
    public void onClick(View v) {

    }

    public void setPosition(float x, float y) {
        this.getView().setTranslationX(x);
        this.getView().setTranslationY(y);

    }

    @Override
    public void onFlightPlanChange(ArrayList<WayPoint> flightPLan) {
        Iterator<WayPoint> i = flightPLan.iterator();
        String fp_str = "Plan: ";
        while (i.hasNext()) {
            WayPoint wp = i.next();
            fp_str = fp_str +  wp.getName() + " | ";
        }
        this.flightPlanTextView.setText(fp_str);

    }

    @Override
    public void setLabelUser(FlightPlanLabelUser labelUser) {

    }
}
