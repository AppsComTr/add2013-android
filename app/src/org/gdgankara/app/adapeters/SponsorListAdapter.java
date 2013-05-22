package org.gdgankara.app.adapeters;

import java.util.ArrayList;
import java.util.List;
import org.gdgankara.app.R;
import org.gdgankara.app.model.Sponsor;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SponsorListAdapter extends BaseAdapter{
	
	private ArrayList<View> view_list;

	public SponsorListAdapter(ArrayList<View> view_list){
		this.view_list=view_list;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		return view_list.get(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return view_list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}
