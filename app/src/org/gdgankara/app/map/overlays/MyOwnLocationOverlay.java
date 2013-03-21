package org.gdgankara.app.map.overlays;

import android.content.Context;
import android.graphics.Canvas;
import android.location.Location;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class MyOwnLocationOverlay extends MyLocationOverlay{

    private MapView mapView;

    public MyOwnLocationOverlay(Context context, MapView mapView) {
        super(context, mapView);
        this.mapView = mapView;
    }
    
    @Override
	protected void drawMyLocation(Canvas canvas, MapView mapView,
			Location lastFix, GeoPoint myLocation, long when) {
		// TODO Auto-generated method stub
		super.drawMyLocation(canvas, mapView, lastFix, myLocation, when);
	}

}