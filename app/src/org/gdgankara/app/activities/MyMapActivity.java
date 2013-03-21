package org.gdgankara.app.activities;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.gdgankara.app.R;
import org.gdgankara.app.map.overlays.MyOwnLocationOverlay;
import org.gdgankara.app.map.overlays.RouteOverlay;
import org.gdgankara.app.map.parsers.GoogleParser;
import org.gdgankara.app.map.parsers.Parser;
import org.gdgankara.app.map.routes.Route;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MyMapActivity extends MapActivity {
	private final GeoPoint aKapisi = getPoint(39.908018, 32.784263);
	private final GeoPoint bKapisi = getPoint(39.891145, 32.793723);
	private final GeoPoint odtuKKM = getPoint(39.894073, 32.786068);
	private MapView mapView = null;
	private LocationManager locationManager = null;
	private MyOwnLocationOverlay myLocationOverlay = null;

	private final LocationListener locationListener = new LocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onProviderDisabled(String provider) {

			updateWithNewLocation(null);
		}

		@Override
		public void onLocationChanged(Location location) {

			int zoomLevel = 15;
			if (mapView != null) {
				zoomLevel = mapView.getZoomLevel();
				mapView.getOverlays().clear();
				mapView.invalidate();
			}
			updateWithNewLocation(location);
			mapView.getController().setZoom(zoomLevel);
			mapView.invalidate();
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {

		networkonMainThreadManagement();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		try {
			mapView = (MapView) findViewById(R.id.map);
			mapView.getController().setZoom(15);
			mapView.invalidate();

			/* get current location and destinatin location */
			Location location = getCurrentLocation();
			
			updateWithNewLocation(location);
			locationManager.requestLocationUpdates(location.getProvider(),
					5000, 25, locationListener);

		} catch (Exception e) {
			Toast.makeText(this, "Please, check your internet connection.",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		this.mapView.getOverlays().clear();
		mapView.invalidate();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (myLocationOverlay != null)
			myLocationOverlay.enableMyLocation();

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_S) {
			mapView.setSatellite(!mapView.isSatellite());
			return (true);
		} else if (keyCode == KeyEvent.KEYCODE_Z) {
			mapView.displayZoomControls(true);
			return (true);
		} else if (keyCode == KeyEvent.KEYCODE_HOME) {
			onPause();
			return true;
		}

		return (super.onKeyDown(keyCode, event));
	}
	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
	}
	private void updateWithNewLocation(Location location) {
		if (location != null) {

			/* choose marker from resources to show locations on map */

			Drawable conferenceMarker = getResources().getDrawable(
					R.drawable.dev_days);

			/* add overlay to mark destination location */
			addMarkerOnMap(conferenceMarker, odtuKKM);
			GeoPoint currentPoint = getPoint(location.getLatitude(),
					location.getLongitude());

			/* add mylocation overlay into map view */
			myLocationOverlay = new MyOwnLocationOverlay(this, mapView);
			myLocationOverlay.enableMyLocation();
			mapView.getOverlays().add(myLocationOverlay);

			/* get the shortest path */
			Route shortestRoute = getShortestPath(currentPoint, aKapisi,
					bKapisi);

			/* draw route between current location and destination location */
			drawRoute(shortestRoute, Color.BLUE);

			// set map view attributes
			mapView.getController().setCenter(
					getPoint(location.getLatitude(), location.getLongitude()));
			mapView.setBuiltInZoomControls(true);

//			mapView.setStreetView(true);
			mapView.setSatellite(false);
			mapView.invalidate();
		}
	}

	/**
	 * compare distance of two locations according to another location and
	 * return shortest route.
	 */
	public Route getShortestPath(GeoPoint start, GeoPoint dest1, GeoPoint dest2) {

		if (start != null && dest1 != null && dest2 != null) {
			Route route1 = directions(start, dest1);
			Route route2 = directions(start, dest2);

			Drawable marker = getResources().getDrawable(R.drawable.marker);
			marker.setBounds(0, 0, marker.getIntrinsicWidth(),
					marker.getIntrinsicHeight());

			if (route1 != null && route2 != null) {
				if (route1.getLength() <= route2.getLength()) {
					addMarkerOnMap(marker, dest1);
					/* bu noktalar kampusun icini dolasabilmek icin eklendi */
					route1.getPoints().add(getPoint(39.904041, 32.782985));
					route1.getPoints().add(getPoint(39.894872, 32.784616));
					addMarkerOnMap(marker, getPoint(39.894872, 32.784616));
					return route1;
				} else {
					addMarkerOnMap(marker, dest2);
					/* bu noktalar kampusun icini dolasabilmek icin eklendi */
					route2.getPoints().add(getPoint(39.891059, 32.792738));
					route2.getPoints().add(getPoint(39.890507, 32.791743));
					route2.getPoints().add(getPoint(39.890157, 32.790423));
					route2.getPoints().add(getPoint(39.890371, 32.790115));
					route2.getPoints().add(getPoint(39.891036, 32.789961));
					route2.getPoints().add(getPoint(39.891460, 32.789270));
					route2.getPoints().add(getPoint(39.892754, 32.789015));
					route2.getPoints().add(getPoint(39.893415, 32.785638));
					addMarkerOnMap(marker, getPoint(39.893415, 32.785638));
					return route2;
				}
			}
		}
		return null;
	}

	/** add marker into a location */
	public void addMarkerOnMap(Drawable marker, GeoPoint geoPoint) {

		if (marker != null && geoPoint != null) {
			MapOverlay mapOverlay = new MapOverlay(marker, geoPoint);
			mapView.getOverlays().add(mapOverlay);
		}
	}

	/** draw route between specified locations. */
	public void drawRoute(Route route, int wayColor) {

		if (route != null) {
			RouteOverlay routeOverlay = new RouteOverlay(route, wayColor);
			mapView.getOverlays().add(routeOverlay);
		}
	}

	/** Manage the android os network main thread */
	public void networkonMainThreadManagement() {
		try {
			Class<?> strictModeClass = Class.forName("android.os.StrictMode",
					true, Thread.currentThread().getContextClassLoader());

			Class<?> threadPolicyClass = Class.forName(
					"android.os.StrictMode$ThreadPolicy", true, Thread
							.currentThread().getContextClassLoader());

			Class<?> threadPolicyBuilderClass = Class.forName(
					"android.os.StrictMode$ThreadPolicy$Builder", true, Thread
							.currentThread().getContextClassLoader());

			Method setThreadPolicyMethod = strictModeClass.getMethod(
					"setThreadPolicy", threadPolicyClass);

			Method detectAllMethod = threadPolicyBuilderClass
					.getMethod("detectAll");
			Method penaltyMethod = threadPolicyBuilderClass
					.getMethod("penaltyLog");
			Method buildMethod = threadPolicyBuilderClass.getMethod("build");

			Constructor<?> threadPolicyBuilderConstructor = threadPolicyBuilderClass
					.getConstructor();
			Object threadPolicyBuilderObject = threadPolicyBuilderConstructor
					.newInstance();

			Object obj = detectAllMethod.invoke(threadPolicyBuilderObject);

			obj = penaltyMethod.invoke(obj);
			Object threadPolicyObject = buildMethod.invoke(obj);
			setThreadPolicyMethod.invoke(strictModeClass, threadPolicyObject);

		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
	}

	/** get current location of user */
	protected Location getCurrentLocation() {
		String context = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) getSystemService(context);
		String bestProvider = "";

		// choose best provider by using criteria
		Criteria criteria = new Criteria();

		bestProvider = locationManager.getBestProvider(criteria, false);
		if (bestProvider != null) {
			Location location = locationManager
					.getLastKnownLocation(bestProvider);
			return location;
		}
		return null;
	}

	@Override
	protected boolean isRouteDisplayed() {
		return (false);
	}


	/* get a geopoint according to latitude and longitude */
	protected GeoPoint getPoint(double lat, double lon) {
		return (new GeoPoint((int) (lat * 1000000.0), (int) (lon * 1000000.0)));
	}

	/** get route */
	private Route directions(final GeoPoint... dest) {
		Parser parser;
		String jsonURL = "http://maps.googleapis.com/maps/api/directions/json?";
		final StringBuffer sBuf = new StringBuffer(jsonURL);
		sBuf.append("origin=");
		sBuf.append(dest[0].getLatitudeE6() / 1E6);
		sBuf.append(',');
		sBuf.append(dest[0].getLongitudeE6() / 1E6);
		sBuf.append("&destination=");
		sBuf.append(dest[1].getLatitudeE6() / 1E6);
		sBuf.append(',');
		sBuf.append(dest[1].getLongitudeE6() / 1E6);
		sBuf.append("&sensor=true&mode=driving");
		parser = new GoogleParser(sBuf.toString());
		Route r = parser.parse();
		return r;
	}

	private class MapOverlay extends ItemizedOverlay<OverlayItem> {
		private List<OverlayItem> items = new ArrayList<OverlayItem>();
		private Drawable marker = null;

		public MapOverlay(Drawable marker, GeoPoint geoPoint) {
			super(marker);
			this.marker = marker;

			items.add(new OverlayItem(geoPoint, "", ""));
			populate();
		}

		@Override
		protected OverlayItem createItem(int i) {
			return (items.get(i));
		}

		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			super.draw(canvas, mapView, shadow);
			boundCenterBottom(marker);
		}

		@Override
		public int size() {
			return (items.size());
		}
	}



}