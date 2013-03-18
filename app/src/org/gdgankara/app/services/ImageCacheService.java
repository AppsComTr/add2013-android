package org.gdgankara.app.services;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.gdgankara.app.io.SessionsHandler;
import org.gdgankara.app.model.Speaker;
import org.gdgankara.app.receivers.ImageCacheReceiver;
import org.gdgankara.app.tasks.BitmapDownloaderTask;
import org.gdgankara.app.utils.Util;

import android.app.IntentService;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class ImageCacheService extends IntentService {
	private static final String TAG = ImageCacheService.class.getSimpleName();
	public static final String NAME = "imageCacheService";
	public static final String CACHE_COMPLETED = "ImageCacheService.COMPLETED";
	public static final String CACHE_STARTED = "ImageCacheService.STARTED";
	public static final String CACHE_TYPE = "cache_type";
	public static final String CACHE_SPEAKER_IMAGES = "cache_speaker_images";
	public static final String CACHE_ANNOUNCEMENT_IMAGES = "cache_announcement_images";
	
	private ImageCacheReceiver imageCacheReceiver;
	public ImageCacheService() {
		super(NAME);
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		String action = intent.getAction();
		String cacheType = intent.getStringExtra(CACHE_TYPE);		
		String imageCacheDirectory = getApplicationContext().getCacheDir().getAbsolutePath();
		
		if(action.equals(CACHE_STARTED)) {
			if (cacheType.equals(CACHE_SPEAKER_IMAGES)) {
				ArrayList<Speaker> speakerList = Util.SpeakerList;
				for (Speaker speaker : speakerList) {
					String imagePath = speaker.getPhoto();
					File cacheFile = new File(imageCacheDirectory,String.valueOf(imagePath.hashCode()));
					if (!cacheFile.exists()) {
						try {
							BitmapDownloaderTask bitmapDownloaderTask = new BitmapDownloaderTask();
							imagePath = bitmapDownloaderTask.execute(imagePath, imageCacheDirectory).get();
						} catch (InterruptedException e) {
							Log.w(TAG, imagePath + " " + e);
							e.printStackTrace();
						} catch (ExecutionException e) {
							Log.w(TAG, imagePath + " " + e);
							e.printStackTrace();
						}
					}else {
						imagePath = cacheFile.getPath();
					}
					speaker.setPhoto(imagePath);
				}
				Util.SpeakerList = speakerList;
				
				Intent imageCacheReceiverIntent = new Intent(CACHE_COMPLETED);
				imageCacheReceiverIntent.putExtra(CACHE_TYPE, CACHE_SPEAKER_IMAGES);
				sendBroadcast(imageCacheReceiverIntent);
			}
			if (cacheType.equals(CACHE_ANNOUNCEMENT_IMAGES)) {
				
			}
		}
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		imageCacheReceiver = new ImageCacheReceiver();
		IntentFilter intentFilter = new IntentFilter(CACHE_COMPLETED);
		registerReceiver(imageCacheReceiver, intentFilter);
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		unregisterReceiver(imageCacheReceiver);
		super.onDestroy();
	}

}
