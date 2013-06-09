package org.gdgankara.app.activities;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import org.gdgankara.app.R;
import org.gdgankara.app.map.GPSTracker;
import org.gdgankara.app.map.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MapActivity extends Activity {

	protected static final LatLng odtuKKM = new LatLng(39.894073, 32.786068);
	protected static final LatLng a1_gate = new LatLng(39.908018, 32.784263);
	protected static final LatLng internalPointsOfA_Kapisi[] = {
			new LatLng(39.908018, 32.784263), new LatLng(39.904041, 32.782985),
			new LatLng(39.894872, 32.784616) };
	protected static final LatLng a4_gate = new LatLng(39.891145, 32.793723);
	protected static final LatLng internalPointsOfB_Kapisi[] = {
			new LatLng(39.891145, 32.793723), new LatLng(39.891059, 32.792738),
			new LatLng(39.890507, 32.791743), new LatLng(39.890157, 32.790423),
			new LatLng(39.890371, 32.790115), new LatLng(39.891036, 32.789961),
			new LatLng(39.891460, 32.789270), new LatLng(39.892754, 32.789015),
			new LatLng(39.893415, 32.785638), };
	private LatLng nearestGate = a1_gate;// by default
	private GoogleMap map = null;
	private GPSTracker tracker = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		try {
			if (!isOnline())
				throw new ConnectException(
						"Lütfen internet baðlantýnýzý kontrol ediniz...");

			map = ((MapFragment) getFragmentManager()
					.findFragmentById(R.id.map)).getMap();

			map.setMyLocationEnabled(true);
//			map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			map.addMarker(new MarkerOptions()
					.position(odtuKKM)
					.title("ODTÜ Kültür ve Kongre Merkezi")
					.snippet("Android Geliþtirici Günleri")
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.dev_days)));

			this.tracker = new GPSTracker(this);

			Location location = tracker.getLocation();

			if (location == null)
				throw new NullPointerException("Konumunuz Bulunumadý...");

			LatLng currentLocation = new LatLng(location.getLatitude(),
					location.getLongitude());

			if (currentLocation != null)
				new connectAsyncTask(this.makeURL(currentLocation.latitude,
						currentLocation.longitude, a1_gate.latitude,
						a1_gate.longitude), this.makeURL(
						currentLocation.latitude, currentLocation.longitude,
						a4_gate.latitude, a4_gate.longitude)).execute();

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

			if (nearestGate.latitude == a1_gate.latitude
					&& nearestGate.longitude == a1_gate.longitude) {
				for (int z = 0; z < internalPointsOfA_Kapisi.length - 1; z++) {
					LatLng src = internalPointsOfA_Kapisi[z];
					LatLng dest = internalPointsOfA_Kapisi[z + 1];
					map.addPolyline(new PolylineOptions()
							.add(new LatLng(src.latitude, src.longitude),
									new LatLng(dest.latitude, dest.longitude))
							.width(2).color(Color.BLUE).geodesic(true));
					map.addMarker(new MarkerOptions()
							.position(a1_gate)
							.title("Nizamiyeden geçiþ yapmanýz gerekmekedir.")
							.icon(BitmapDescriptorFactory
									.fromResource(R.drawable.marker)));
				}

			} else if (nearestGate.latitude == a4_gate.latitude
					&& nearestGate.latitude == a4_gate.longitude) {
				for (int z = 0; z < internalPointsOfB_Kapisi.length - 1; z++) {
					LatLng src = internalPointsOfB_Kapisi[z];
					LatLng dest = internalPointsOfB_Kapisi[z + 1];
					map.addPolyline(new PolylineOptions()
							.add(new LatLng(src.latitude, src.longitude),
									new LatLng(dest.latitude, dest.longitude))
							.width(2).color(Color.BLUE).geodesic(true));

					map.addMarker(new MarkerOptions()
							.position(a4_gate)
							.title("Nizamiyeden geçiþ yapmanýz gerekmekedir.")
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
		getMenuInflater().inflate(R.menu.activity_map, menu);
		return true;
	}

	private class connectAsyncTask extends AsyncTask<Void, Void, String> {
		private ProgressDialog progressDialog;
		String url0;
		String url1;

		connectAsyncTask(String url0, String url1) {
			this.url0 = url0;
			this.url1 = url1;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(MapActivity.this);
			progressDialog.setMessage("Rota çiziliyor, Lütfen bekleyiniz...");
			progressDialog.setIndeterminate(true);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(Void... params) {
			JSONParser jParser = new JSONParser();
			String json0 = jParser.getJSONFromUrl(url0);
			String json1 = jParser.getJSONFromUrl(url1);
			try {
				JSONObject jobj0 = new JSONObject(json0);
				JSONObject jobj1 = new JSONObject(json1);
				
				//get distance to a1 gate of path
				int distanceto_a1 = jobj0.getJSONArray("routes").getJSONObject(0)
						.getJSONArray("legs").getJSONObject(0)
						.getJSONObject("distance").getInt("value");
				
				//get distance to a1 gate of path
				int distanceto_a4 = jobj1.getJSONArray("routes").getJSONObject(0)
						.getJSONArray("legs").getJSONObject(0)
						.getJSONObject("distance").getInt("value");
				
				if (distanceto_a1 <= distanceto_a4) {
					//set nearest gate to a1 
					nearestGate = a1_gate;
					return json0;
				} else {
					//set nearest gate to a4
					nearestGate = a4_gate;
					return json1;
				}

			} catch (JSONException e) {
				
			}
			return json0;
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