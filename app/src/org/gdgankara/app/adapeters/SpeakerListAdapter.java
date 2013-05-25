package org.gdgankara.app.adapeters;

import java.util.List;
import org.gdgankara.app.R;
import org.gdgankara.app.customview.EllipsizingTextView;
import org.gdgankara.app.model.Speaker;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SpeakerListAdapter extends ArrayAdapter<Speaker>{
	
	private Context context;
	private List<Speaker> speakerlist;
	private LayoutInflater inflater ;
	private View view;
	private TextView text;
	private ImageView imageview;
	private int textSize;
	private Speaker speaker;

	public SpeakerListAdapter(Context context, List<Speaker> objects,int height) {
		super(context,R.layout.child_of_speakerlist,objects);
		this.context=context;
		speakerlist=objects;
		inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		setFeaturesTextSize(height);
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		speaker=speakerlist.get(position);
		view=inflater.inflate(R.layout.child_of_speakerlist, null, false);
		imageview=(ImageView)view.findViewById(R.id.speaker_image);
		UrlImageViewHelper.setUrlDrawable(imageview, speaker.getPhoto(),R.drawable.loading);
		text=(TextView)view.findViewById(R.id.speaker_name);
		text.setText(speaker.getName());
		text=(EllipsizingTextView)view.findViewById(R.id.speaker_bio);
		text.setText(speaker.getBiography());
		text.setTextSize(textSize);
		text.setMaxLines(3);
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
