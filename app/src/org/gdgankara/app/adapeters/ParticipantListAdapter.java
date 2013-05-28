package org.gdgankara.app.adapeters;

import java.util.List;
import org.gdgankara.app.R;
import org.gdgankara.app.customview.EllipsizingTextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ParticipantListAdapter extends ArrayAdapter<String>{
	
	private List<String> participantlist;
	private LayoutInflater inflater ;
	private View view;
	private TextView text;
	private String[] temp; 
	private int textSize;

	public ParticipantListAdapter(Context context, List<String> objects,int height) {
		super(context,R.layout.child_of_participantlist, objects);
		this.participantlist=objects;
		inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		setFeaturesTextSize(height);
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		temp=participantlist.get(position).split("<#>");
		view=inflater.inflate(R.layout.child_of_participantlist, null, false);
		text=(TextView)view.findViewById(R.id.participantname);
		text.setText(temp[0]+" "+temp[1]);
		text=(TextView)view.findViewById(R.id.participantcompany);
		text.setText(temp[5]);
		text.setTextSize(textSize);
		text=(TextView)view.findViewById(R.id.participanttitle);
		text.setText(temp[6]);
		text.setTextSize(textSize);
		return view;
	}
	
	private void setFeaturesTextSize(int height){
		
		
		if(height<=320){
			textSize=10;
		}
		else if(height<=480){
			textSize=11;
		}
		else if(height<=800){
			textSize=12;
		}
		else{
			textSize=13;
		}
		
	}

}
