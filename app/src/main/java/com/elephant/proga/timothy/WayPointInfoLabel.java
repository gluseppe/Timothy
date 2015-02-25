package com.elephant.proga.timothy;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elephant.proga.timothy.interfaces.GuideLabelInterface;
import com.elephant.proga.timothy.interfaces.GuideLabelUser;
import com.elephant.proga.timothy.interfaces.WayPointInfoLabelInterface;
import com.elephant.proga.timothy.interfaces.WayPointInfoLabelUser;

import java.util.ArrayList;

/**
 * Created by gluse on 20/02/15.
 */
public class WayPointInfoLabel extends Fragment implements WayPointInfoLabelInterface,View.OnClickListener, View.OnTouchListener {

    private WayPointInfoLabelUser labelUser;

    private TextView pointInfo;
    private View labelView;
    private int status;
    private WayPoint current;

    public WayPointInfoLabel() {

        super();
        this.status = Status.NO_STARTING_POINT;

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
        View v = inflater.inflate(R.layout.info_label_layout,container,false);
        this.pointInfo = (TextView) v.findViewById(R.id.pointInfo);
        v.setOnClickListener(this);
        v.setOnTouchListener(this);
        v.setTranslationX(0);
        v.setTranslationY(0);
        this.labelView = v;
        this.hideLabel();
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
    public void onWayPointTouch(WayPoint wp) {

    }

    @Override
    public void onGenericPointTouch(WayPoint wp) {

    }

    @Override
    public void hideLabel() {
        this.labelView.setVisibility(View.INVISIBLE);

    }

    @Override
    public void showLabel(WayPoint wp) {
        setCurrentWayPoint(wp);
        this.labelView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setLabelUser(WayPointInfoLabelUser labelUser) {
        this.labelUser = labelUser;

    }

    @Override
    public Point getSize() {
        return new Point(this.labelView.getWidth(), this.labelView.getHeight());
    }

    public void setPosition(float x, float y) {
        this.labelView.setTranslationX(x);
        this.labelView.setTranslationY(y);

    }

    public String getPointInfo() {
        return this.pointInfo.getText().toString();
    }

    public void setPointName(String pointName) {
        this.pointInfo.setText(pointName);
    }

    @Override
    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public void setCurrentWayPoint(WayPoint wp) {
        this.current = wp;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int source = v.getId();
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                //if (source == callToPredict.getId() || source == labelView.getId() || source == altitudeTextView.getId() || source == flightIDTextView.getId()) {
                labelView.setBackgroundColor(getResources().getColor(R.color.primary_dark));
                //}
                //else
                //{
                //    View sourceView = labelView.findViewById(source);
                //    sourceView.setBackgroundColor(accent);
                //}
                return true;
            }

            case MotionEvent.ACTION_UP: {
                if (source == this.pointInfo.getId() || source == labelView.getId()) {
                    labelView.setBackgroundColor(getResources().getColor(R.color.primary_transparent));
                }
                //this will call the registered clicklistener
                this.labelView.performClick();
                return false;
            }

            default:
                return false;

        }
    }

    @Override
    public void onClick(View v) {
        this.labelUser.onWaypointSelect(this.current);

    }


}
