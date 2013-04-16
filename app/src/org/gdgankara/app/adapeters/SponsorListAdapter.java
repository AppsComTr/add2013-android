package org.gdgankara.app.adapeters;

import java.util.List;
import org.gdgankara.app.R;
import org.gdgankara.app.model.Sponsor;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SponsorListAdapter extends ArrayAdapter<Sponsor>{
	
	private List<Sponsor> sponsorlist;
	private LayoutInflater inflater ;
	private View view;
	private TextView text;
	private Sponsor sponsor;
	
	public SponsorListAdapter(Context context, List<Sponsor> objects) {
		super(context,R.layout.child_of_sponsorlist,objects);
		sponsorlist=objects;
		inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		sponsor=sponsorlist.get(position);
		view=inflater.inflate(R.layout.child_of_sponsorlist, null, false);
		text=(TextView)view.findViewById(R.id.sponsor_name);
		text.setText(sponsor.getName());
		
		//Tam burada logoyu da set etmen gerek
		
		return view;
	}

}
