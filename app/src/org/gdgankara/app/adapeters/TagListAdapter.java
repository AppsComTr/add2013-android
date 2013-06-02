package org.gdgankara.app.adapeters;

import java.util.List;
import org.gdgankara.app.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TagListAdapter extends ArrayAdapter<String>{

	private List<String> taglist;
	private LayoutInflater inflater ;
	private View view;
	private TextView text;
	private Context context;
	private Animation a;
	
	public TagListAdapter(Context context, List<String> objects) {
		super(context,R.layout.child_of_taglist,objects);
		this.context=context;
		this.taglist=objects;
		inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		view=inflater.inflate(R.layout.child_of_taglist, null, false);
		text=(TextView)view.findViewById(R.id.tag_name);
		text.setText(taglist.get(position));
		return view;
	}

	
}
