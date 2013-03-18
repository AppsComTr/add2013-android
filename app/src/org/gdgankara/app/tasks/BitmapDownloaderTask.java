package org.gdgankara.app.tasks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.gdgankara.app.utils.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

/**
 * The actual AsyncTask that will asynchronously download the image.
 */
public class BitmapDownloaderTask extends AsyncTask<String, Void, String> {
	private static final String TAG = BitmapDownloaderTask.class.getSimpleName();

	private String url;
	private String imageCacheDirectory;

	/**
	 * Actual download method.
	 */
	@Override
	protected String doInBackground(String... params) {
		url = params[0];
		imageCacheDirectory = params[1];
		return downloadBitmap(url);
	}

	/**
	 * Once the image is downloaded, associates it to the imageView
	 */
	@Override
	protected void onPostExecute(String filePath) {
		if (isCancelled()) {
			filePath = null;
		}
	}

	protected String downloadBitmap(String url) {

		// AndroidHttpClient is not allowed to be used from the main thread
		final HttpClient client = AndroidHttpClient.newInstance("Android");
		final HttpGet getRequest = new HttpGet(url);

		try {
			HttpResponse response = client.execute(getRequest);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				Log.w(TAG, "Error " + statusCode
						+ " while retrieving bitmap from " + url);
				return null;
			}

			final HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream inputStream = null;
				try {
					inputStream = entity.getContent();
					Bitmap bitmap = BitmapFactory
							.decodeStream(new Util.FlushedInputStream(
									inputStream));
					String cachedImagePath = cacheBitmap(url, bitmap);
					return cachedImagePath;
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					entity.consumeContent();
				}
			}
		} catch (IOException e) {
			getRequest.abort();
			Log.w(TAG, "I/O error while retrieving bitmap from " + url, e);
		} catch (IllegalStateException e) {
			getRequest.abort();
			Log.w(TAG, "Incorrect URL: " + url);
		} catch (Exception e) {
			getRequest.abort();
			Log.w(TAG, "Error while retrieving bitmap from " + url, e);
		} finally {
			if ((client instanceof AndroidHttpClient)) {
				((AndroidHttpClient) client).close();
			}
		}
		return null;
	}

	protected String cacheBitmap(String url, Bitmap bitmap) throws IOException {
		String fileName = String.valueOf(url.hashCode());
		File cacheFile = new File(imageCacheDirectory, fileName);
		if (!cacheFile.exists()) {
			FileOutputStream fos = null;
			fos = new FileOutputStream(cacheFile);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.close();
			return cacheFile.getPath();
		} else {
			return cacheFile.getPath();
		}
	}
}
