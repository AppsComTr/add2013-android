package org.gdgankara.app.receivers;

import org.gdgankara.app.io.SessionsHandler;
import org.gdgankara.app.services.ImageCacheService;
import org.gdgankara.app.utils.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ImageCacheReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		String cacheType = intent.getStringExtra(ImageCacheService.CACHE_TYPE);
		if (action.equals(ImageCacheService.CACHE_COMPLETED)) {
			if (cacheType.equals(ImageCacheService.CACHE_SPEAKER_IMAGES)) {
				SessionsHandler sessionsHandler = new SessionsHandler(context);
				sessionsHandler.updateSpeakerListCacheFile(Util.getDefaultLanguage());
			}
		}
	}

}
