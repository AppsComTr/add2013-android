package org.gdgankara.app.activities;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import org.gdgankara.app.R;
import org.gdgankara.app.map.GPSTracker;
import org.gdgankara.app.map.JSONParser;
import org.gdgankara.app.map.LocationFinder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

@SuppressLint("NewApi")
public class MapActivity extends Activity {

	protected static final LatLng odtuKKM = new LatLng(39.894073, 32.786068);
	protected static final LatLng aKapisi = new LatLng(39.908018, 32.784263);
	protected static final LatLng internalPointsOfA_Kapisi[] = {
			new LatLng(39.908018, 32.784263), new LatLng(39.904041, 32.782985),
			new LatLng(39.894872, 32.784616) };
	protected static final LatLng bKapisi = new LatLng(39.891145, 32.793723);
	protected static final LatLng internalPointsOfB_Kapisi[] = {
			new LatLng(39.891145, 32.793723), new LatLng(39.891059, 32.792738),
			new LatLng(39.890507, 32.791743), new LatLng(39.890157, 32.790423),
			new LatLng(39.890371, 32.790115), new LatLng(39.891036, 32.789961),
			new LatLng(39.891460, 32.789270), new LatLng(39.892754, 32.789015),
			new LatLng(39.893415, 32.785638), };
	private LatLng nearestGate = null;
	private GoogleMap map = null;
	private GPSTracker tracker = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		try {
			if (!isOnline())
				throw new ConnectException(
						"L�tfen internet ba�lant�n�z� kontrol ediniz...");

			map = ((MapFragment) getFragmentManager()
					.findFragmentById(R.id.map)).getMap();

			map.setMyLocationEnabled(true);
			map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			map.addMarker(new MarkerOptions()
					.position(odtuKKM)
					.title("ODT� K�lt�r ve Kongre Merkezi")
					.snippet("Android Geli�tirici G�nleri")
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.dev_days)));

			this.tracker = new GPSTracker(this);

			Location location = tracker.getLocation();

			if (location == null)
				throw new NullPointerException("Konumunuz Bulunumad�...");

			LatLng currentLocation = new LatLng(location.getLatitude(),
					location.getLongitude());

			/**
			 * TODO 1. asynctask ba�lamadan �nce k�sa olan mesafeyi bulman
			 * gerekiyor. 2.burada location finder s�n�f�n�n static methodunu
			 * �al��t�racaks�n
			 * 
			 * */

			nearestGate = LocationFinder.getNearestLocation(currentLocation,
					aKapisi, bKapisi);

			if (nearestGate != null)
				new connectAsyncTask(this.makeURL(currentLocation.latitude,
						currentLocation.longitude, nearestGate.latitude,
						nearestGate.longitude)).execute();

			// Move the camera instantly to burgu with a zoom of 15
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,
					15));
		} catch (ConnectException e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
			this.finish();
		} catch (NullPointerException e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (map != null)
			this.map.clear();
		this.finish();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	private List<LatLng> decodePoly(String encoded) {

		List<LatLng> poly = new ArrayList<LatLng>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			LatLng p = new LatLng((((double) lat / 1E5)),
					(((double) lng / 1E5)));
			poly.add(p);
		}

		return poly;
	}

	public String makeURL(double sourcelat, double sourcelog, double destlat,
			double destlog) {
		StringBuilder urlString = new StringBuilder();
		urlString.append("http://maps.googleapis.com/maps/api/directions/json");
		urlString.append("?origin=");// from
		urlString.append(Double.toString(sourcelat));
		urlString.append(",");
		urlString.append(Double.toString(sourcelog));
		urlString.append("&destination=");// to
		urlString.append(Double.toString(destlat));
		urlString.append(",");
		urlString.append(Double.toString(destlog));
		urlString.append("&sensor=false&mode=driving&alternatives=true");
		return urlString.toString();
	}

	public void drawPath(String result) {

		try {
			// Tranform the string into a json object
			final JSONObject json = new JSONObject(result);
			JSONArray routeArray = json.getJSONArray("routes");
			JSONObject routes = routeArray.getJSONObject(0);
			JSONObject overviewPolylines = routes
					.getJSONObject("overview_polyline");
			String encodedString = overviewPolylines.getString("points");
			List<LatLng> list = decodePoly(encodedString);

			for (int z = 0; z < list.size() - 1; z++) {
				LatLng src = list.get(z);
				LatLng dest = list.get(z + 1);
				map.addPolyline(new PolylineOptions()
						.add(new LatLng(src.latitude, src.longitude),
								new LatLng(dest.latitude, dest.longitude))
						.width(2).color(Color.BLUE).geodesic(true));
			}

			if (nearestGate.latitude == aKapisi.latitude
					&& nearestGate.longitude == aKapisi.longitude) {
				for (int z = 0; z < internalPointsOfA_Kapisi.length - 1; z++) {
					LatLng src = internalPointsOfA_Kapisi[z];
					LatLng dest = internalPointsOfA_Kapisi[z + 1];
					map.addPolyline(new PolylineOptions()
							.add(new LatLng(src.latitude, src.longitude),
									new LatLng(dest.latitude, dest.longitude))
							.width(2).color(Color.BLUE).geodesic(true));
					map.addMarker(new MarkerOptions()
							.position(aKapisi)
							.title("Nizamiyeden ge�i� yapman�z gerekmekedir.")
							.icon(BitmapDescriptorFactory
									.fromResource(R.drawable.marker)));
				}

			} else if (nearestGate.latitude == bKapisi.latitude
					&& nearestGate.latitude == bKapisi.longitude) {
				for (int z = 0; z < internalPointsOfB_Kapisi.length - 1; z++) {
					LatLng src = internalPointsOfB_Kapisi[z];
					LatLng dest = internalPointsOfB_Kapisi[z + 1];
					map.addPolyline(new PolylineOptions()
							.add(new LatLng(src.latitude, src.longitude),
									new LatLng(dest.latitude, dest.longitude))
							.width(2).color(Color.BLUE).geodesic(true));

					map.addMarker(new MarkerOptions()
							.position(bKapisi)
							.title("Nizamiyeden ge�i� yapman�z gerekmekedir.")
							.icon(BitmapDescriptorFactory
									.fromResource(R.drawable.marker)));
				}
			}

		} catch (JSONException e) {

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// agetMenuInflater().inflate(R.menu.activity_map, menu);
		return true;
	}

	private class connectAsyncTask extends AsyncTask<Void, Void, String> {
		private ProgressDialog progressDialog;
		String url;

		connectAsyncTask(String urlPass) {
			url = urlPass;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(MapActivity.this);
			progressDialog.setMessage("Rota �iziliyor, L�tfen bekleyiniz...");
			progressDialog.setIndeterminate(true);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(Void... params) {
			JSONParser jParser = new JSONParser();
			String json = jParser.getJSONFromUrl(url);
			return json;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progressDialog.hide();
			if (result != null) {
				drawPath(result);
			}
		}
	}
}
