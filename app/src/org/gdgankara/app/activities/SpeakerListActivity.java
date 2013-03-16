package org.gdgankara.app.activities;

import java.util.ArrayList;

import org.gdgankara.app.R;
import org.gdgankara.app.model.Speaker;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SpeakerListActivity extends Activity{

	private int height;
	private ListView speaker_listview;
	private ArrayList<Speaker> speaker_list;
	private ArrayAdapter<Speaker> speakerlist_adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		height=getDimensionsOfScreen();
		setContentView(R.layout.speakerlist);
		
	}
	
	private int getDimensionsOfScreen(){
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.heightPixels;

	}
}
