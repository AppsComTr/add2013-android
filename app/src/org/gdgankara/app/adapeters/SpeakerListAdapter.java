package org.gdgankara.app.adapeters;

import java.util.List;
import org.gdgankara.app.R;
import org.gdgankara.app.model.Speaker;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpeakerListAdapter extends ArrayAdapter<Speaker>{
	
	private List<Speaker> speakerlist;
	private LayoutInflater inflater ;
	private View view;
	private TextView text;
	private int textSize;
	private Speaker speaker;

	public SpeakerListAdapter(Context context, List<Speaker> objects,int height) {
		super(context,R.layout.child_of_speakerlist,objects);
		speakerlist=objects;
		inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		setFeaturesTextSize(height);
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		speaker=speakerlist.get(position);
		view=inflater.inflate(R.layout.child_of_speakerlist, null, false);
		text=(TextView)view.findViewById(R.id.speaker_name);
		text.setText(speaker.getName());
		text=(TextView)view.findViewById(R.id.speaker_title);
		text.setText(speaker.getTitle());
		text.setTextSize(textSize);
		return view;
	}
	
	private void setFeaturesTextSize(int height){
		
		
		if(height<=320){
			textSize=9;
		}
		else if(height<=480){
			textSize=10;
		}
		else if(height<=800){
			textSize=11;
		}
		else{
			textSize=12;
		}
		
	}
	
}
