package com.elephant.proga.timothy;

import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.elephant.proga.timothy.interfaces.GuideLabelUser;
import com.elephant.proga.timothy.interfaces.KnownPointsHandler;
import com.elephant.proga.timothy.interfaces.WayPointInfoLabelUser;
import com.elephant.proga.timothy.receivers.KnownPointsReceiver;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Iterator;


public class MainActivity extends FragmentActivity implements GoogleMap.OnMarkerClickListener, GuideLabelUser, WayPointInfoLabelUser, GoogleMap.OnMapClickListener,
        KnownPointsHandler
{

    private final String ROOTSOURCE = "http://192.168.2.33:8080";
    private final String HOTSPOTSSOURCE = ROOTSOURCE + "/hotspots";
    private final String KNOWN_POINTS_SOURCE = ROOTSOURCE + "/planner";
    private final String QUERY1 = "query1";
    private final String ACTIVE_QUERY = QUERY1;

    private GoogleMap mMap;
    private SupportMapFragment mMapFragment;
    private FrameLayout mFrameLayout;
    private GuideLabel guideLabel;
    private FlightPlanLabel flightPlanLabel;
    private WayPointInfoLabel wayPointInfoLabel;
    private View mView;

    private Thread kpr_thread;


    private int status = Status.NO_STARTING_POINT;
    private ArrayList<WayPoint> wayPoints;
    private WayPoint pendingWayPoint;
    private int nextWpIndex = 0;
    private ArrayList<WayPoint> currentFlightPlan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFrameLayout = (FrameLayout) findViewById(R.id.mainFrameLayout);
        this.currentFlightPlan = new ArrayList<WayPoint>();
        setUpMapIfNeeded();
        setUpLabels();
    }

    @Override
    public View onCreateView(String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        this.mView = super.onCreateView(name, context, attrs);
        return this.mView;
    }

    private void setUpLabels() {

        //setup guide label
        this.guideLabel = new GuideLabel();
        this.guideLabel.setGuideLabelUser(this);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.mainFrameLayout, this.guideLabel).commit();

        //setup flightplan label
        this.flightPlanLabel = new FlightPlanLabel();
        ft = getFragmentManager().beginTransaction();
        ft.add(R.id.mainFrameLayout, this.flightPlanLabel).commit();

        //setup wpinfo label
        this.wayPointInfoLabel = new WayPointInfoLabel();
        this.wayPointInfoLabel.setLabelUser(this);
        ft = getFragmentManager().beginTransaction();
        ft.add(R.id.mainFrameLayout, this.wayPointInfoLabel).commit();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        //mMap.setOnCameraChangeListener(this);

        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);

        receiveKnownPoints();
    }

    //KNOWNPOINTSHANDLER METHODS
    @Override
    public void onKnownPointsReceived(final ArrayList<WayPoint> wayPoints) {
        if (wayPoints == null)
        {
            Log.d("KNOWNPOINTS", "RECEIVED NULL");
        }
        else
        {
            this.wayPoints = null;
            this.wayPoints = wayPoints;
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Log.d("TRAFFIC", "Updating traffic");
                    addWayPointsOnMap(mMap, wayPoints);
                }
            });

        }


    }
    //END OF KNOWNPOINTSHANDLER METHODS

    private void addWayPointsOnMap(GoogleMap map, ArrayList<WayPoint> wayPoints) {
        Iterator<WayPoint> i = wayPoints.iterator();
        while(i.hasNext()) {
            WayPoint wp = i.next();
            wp.setMarker(map.addMarker(new MarkerOptions()
                .position(wp.getPosition())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.airport_black_30))
                .title(wp.getName())
                .flat(true)));
        }


    }

    private void receiveKnownPoints() {
        KnownPointsReceiver kpr = new KnownPointsReceiver(this, KNOWN_POINTS_SOURCE, -1);
        kpr.setRequestParams(ACTIVE_QUERY);
        this.kpr_thread = new Thread(kpr);
        this.kpr_thread.start();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (this.status != Status.NO_STARTING_POINT)
        {
            //USER IS SELECTING STARTING POINT
            //TELL LABEL TO SHOW STARTING POINT SELECTION MODE
            //this.wayPointInfoLabel.setMode(Status.NO_STARTING_POINT);
            //this.guideLabel.setMode(Status.NO_STARTING_POINT);


        }
        if (this.status == Status.STARTING_POINT_SELECTED)
        {
            //USER IS SELECTING END POINT
            //TELL LABELS TO SHOW END POINT SELECTION MODE (no, in realta viene fatto quando l azione viene davvero compiuta)

        }
        if (this.status == Status.END_POINT_SELECTED)
        {
            //USER IS SELECTING INTERMEDIATE POINTS
            //TELL LABELS TO SHOW INTERMEDIATE POINTS SELECTION MODE
        }



        Log.d("KNOWNPOINT","This is a marker");
        Point corrected_p = correctLabelLocationIfNeeded(marker.getPosition());
        //set label to be referred to known point
        this.wayPointInfoLabel.setPointName(marker.getTitle());
        this.wayPointInfoLabel.setPosition(corrected_p.x, corrected_p.y);


        this.pendingWayPoint = new WayPoint(marker.getPosition(),0,marker,true,marker.getTitle());
        this.wayPointInfoLabel.showLabel(this.pendingWayPoint);

        return true;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (this.status >= Status.END_POINT_SELECTED) {
            Log.d("GENERIC POINT", "This is a generic point");
            Point corrected_p = correctLabelLocationIfNeeded(latLng);

            //remove last pending waypoint
            if (this.pendingWayPoint != null)
                if (!this.pendingWayPoint.isKnown())
                    this.pendingWayPoint.getMarker().remove();
            this.pendingWayPoint = null;
            String pName = String.format("%.1f, %.1f", latLng.latitude, latLng.longitude);
            this.wayPointInfoLabel.setPointName(pName);
            this.wayPointInfoLabel.setPosition(corrected_p.x, corrected_p.y);
            //crea un nuovo waypoint sconosciuto, aggiungi marker associandolo a waypoint
            //retain waypoint in qualche modo, ne abbiamo uno solo per volta
            //passa tutto a showlabel
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pending_way_point))
                    .draggable(true));
            this.pendingWayPoint = new WayPoint(latLng, 0, marker, false, pName);
            this.wayPointInfoLabel.showLabel(this.pendingWayPoint);
            this.wayPointInfoLabel.showLabel(this.pendingWayPoint);

            //this.wayPointInfoLabel.showLabel();
        }
        else {
            Log.d("STATUS","Only select airfields for starting and end points");
        }
    }


    @Override
    public void onStartAgain() {

    }

    @Override
    public void onRemoveLastWayPoint() {

    }

    @Override
    public void onWaypointSelect(WayPoint waypoint) {
        this.status ++;
        if (this.status > Status.INTERMEDIATE_SELECTED) this.status = Status.INTERMEDIATE_SELECTED;
        this.guideLabel.setStatus(this.status);
        this.pendingWayPoint = null;
        if (! waypoint.isKnown())
            waypoint.getMarker().setIcon(BitmapDescriptorFactory.fromResource(R.drawable.confirmed_way_point));
        this.currentFlightPlan.add(waypoint);
        this.flightPlanLabel.onFlightPlanChange(this.currentFlightPlan);

    }



    private Point correctLabelLocationIfNeeded(LatLng latLng) {
        Point p = mMap.getProjection().toScreenLocation(latLng);

        int h = mFrameLayout.getHeight();
        int w = mFrameLayout.getWidth();
        Point labelSize = this.wayPointInfoLabel.getSize();
        int label_width = labelSize.x;
        int label_height = labelSize.y;
        Log.d("DIMENSION", String.format("View dimension is %d x %d", w, h));
        Log.d("DIMENSION", String.format("Point is %d x %d", p.x, p.y));

        int g_y = p.y - label_height;
        int g_x = p.x - (label_width)/2;
        if (g_x < 0) g_x = 0;
        if (g_x + label_width > w) g_x = g_x - (label_width/2);

        if (g_y < 0) g_y = 0;

        return new Point(g_x,g_y);

    }


}
