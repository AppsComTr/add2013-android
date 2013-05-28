package org.gdgankara.app.io;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class ParticipantHandler extends BaseHandler {
	private static final String TAG = ParticipantHandler.class.getSimpleName();
	private static final String CACHE_FILE_PARTICIPANT = "ParticipantList";
	
	public ParticipantHandler(Context context) {
		super(context);
		this.context = context;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String> getParticipantList(){
		ArrayList<String> participantList = new ArrayList<String>();
		try {
			participantList = (ArrayList<String>) readCacheFile(CACHE_FILE_PARTICIPANT);
		} catch (Exception e) {
			Log.e(TAG, "Error: " + e.getMessage());
			e.printStackTrace();
		}
		if (participantList == null) {
			participantList = new ArrayList<String>();
		}
		return participantList;
	}
	
	public void updateParticipantList(ArrayList<String> participantList){
		try {
			writeListToFile(participantList, CACHE_FILE_PARTICIPANT);
		} catch (Exception e) {
			Log.e(TAG, "Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<?> parseJSONObject(JSONObject jsonObject, String objectName)
			throws JSONException {
		return null;
	}

}
