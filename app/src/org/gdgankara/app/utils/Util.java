package org.gdgankara.app.utils;

import java.io.BufferedReader;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

import org.gdgankara.app.io.AnnouncementHandler;
import org.gdgankara.app.io.FavoritesHandler;
import org.gdgankara.app.io.ProgramHandler;
import org.gdgankara.app.io.SponsorHandler;
import org.gdgankara.app.io.TagHandler;
import org.gdgankara.app.model.Announcement;
import org.gdgankara.app.model.Session;
import org.gdgankara.app.model.Speaker;
import org.gdgankara.app.model.Sponsor;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.DisplayMetrics;
import android.util.Log;

public class Util {
	public static final String TAG = Util.class.getSimpleName();
	
	public static ArrayList<Session> SessionList = new ArrayList<Session>();
	public static ArrayList<Speaker> SpeakerList = new ArrayList<Speaker>();
	public static ArrayList<Announcement> AnnouncementList = new ArrayList<Announcement>();
	public static ArrayList<Sponsor> SponsorList = new ArrayList<Sponsor>();
	public static ArrayList<String> TagList = new ArrayList<String>();
	public static ArrayList<Long> FavoritesList = new ArrayList<Long>();
	public static int device_height;
	public static int device_width;
	
	/**
	 * Shared Preferences'ta tutulan versiyon numarasını verilen numara ile
	 * karşılaştırır.
	 * 
	 * @param context
	 * @param number
	 * @return Boolean
	 * @throws JSONException 
	 */
	public static Boolean isVersionUpdated(Context context,  JSONObject jsonObject) {
		long number;
		Boolean state = false;
		try {
			number = jsonObject.getJSONObject("version")
					.getLong("number");
			SharedPreferences settings = context.getSharedPreferences(TAG, 0);
			long mNumber = settings.getLong("version", 0);
			if (mNumber == number) {
				state = false;
			} else if (mNumber != number || mNumber == 0) {
				Editor editor = settings.edit();
				editor.putLong("version", number);
				editor.commit();
				state = true;
			}
		} catch (JSONException e) {
			state = false;
			e.printStackTrace();
		}
		return state;
	}
	
	public static void prepareStaticLists(Context context){
		String lang = getDefaultLanguage();
		ProgramHandler sessionsHandler = new ProgramHandler(context);
		TagHandler tagHandler = new TagHandler(context);
		FavoritesHandler favoritesHandler = new FavoritesHandler(context);
		AnnouncementHandler announcementHandler = new AnnouncementHandler(context);
		SponsorHandler sponsorHandler = new SponsorHandler(context);
		
		FavoritesList = favoritesHandler.getFavoritesList(lang);
		sessionsHandler.initializeLists(lang);
		TagList = tagHandler.getTagList(lang);
		AnnouncementList = announcementHandler.getAnnouncementList(lang);
		SponsorList = sponsorHandler.getSponsorList(lang);
	}
	
	public static void setDeviceHeight(int height){
		device_height=height;
	}
	
	public static void setDeviceWidth(int width){
		device_width=width;
	}
	
	public static void addSessionFavorites(Context context, Long sessionID){
		FavoritesHandler favoritesHandler = new FavoritesHandler(context);
		if (FavoritesList == null) {
			FavoritesList = new ArrayList<Long>();
		}
		if (!FavoritesList.contains(sessionID)) {
			FavoritesList.add(sessionID);
		}
		favoritesHandler.updateFavoritesList(FavoritesList, getDefaultLanguage());
	}
	
	public static void removeSessionFavorites(Context context, Long sessionID){
		FavoritesHandler favoritesHandler = new FavoritesHandler(context);
		if (FavoritesList.contains(sessionID)) {
			FavoritesList.remove(sessionID);
		}
		favoritesHandler.updateFavoritesList(FavoritesList, getDefaultLanguage());
	}
		
	public static String getDefaultLanguage(){
		if(Locale.getDefault().getLanguage().equals("tr")){
			return "tr";
		}
		else{
			return "en";
		}
	}

	/**
	 * Verilen InputStream'i String'e çevirir.
	 * 
	 * @param inputStream
	 * @return String
	 */
	public static String convertInputStreamToString(InputStream inputStream) {
		String result = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream), 8);
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}
			inputStream.close();
			result = stringBuilder.toString();

		} catch (Exception e) {
			Log.w(TAG, e.toString());
			e.printStackTrace();
		}
		return result;
	}
	
	/*
     * An InputStream that skips the exact number of bytes provided, unless it reaches EOF.
     */
    public static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }

        @Override
        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0L;
            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0L) {
                    int b = read();
                    if (b < 0) {
                        break;  // we reached EOF
                    } else {
                        bytesSkipped = 1; // we read one byte
                    }
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }

}
