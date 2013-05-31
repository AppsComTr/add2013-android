package org.gdgankara.app;

import java.util.ArrayList;
import org.gdgankara.app.adapeters.StickyListAdapter;
import org.gdgankara.app.model.Session;
import org.gdgankara.app.utils.Util;
import org.gdgankara.app.utils.Util2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import com.emilsjolander.components.stickylistheaders.*;

public class TestFragment extends Fragment implements AdapterView.OnItemClickListener{

	private StickyListHeadersListView programlist;
	private int day;
	private View view;
	private ArrayList<Session> sessions;
	
	public void setDay(int day){
		this.day=day;
	}
	
	public void listeleriHazirla(){
		Util2.arrayDoldur();
		ArrayList<Session> total=Util2.SessionList;
		sessions=new ArrayList<Session>();
		int size=total.size();
		for(int i=0;i<size;i++){
			if(day==total.get(i).getDay()){
				sessions.add(total.get(i));
			}
		}
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	 view=inflater.inflate(R.layout.programlist, container, false);
    	 programlist=(StickyListHeadersListView)view.findViewById(R.id.programlist);
    	 programlist.setAdapter(new StickyListAdapter(getActivity(), sessions, Util.device_height));
    	 programlist.setOnItemClickListener(this);
    	 return view;
    }
    
    @Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Toast.makeText(getActivity(), "Item " + position + " clicked!", Toast.LENGTH_SHORT).show();
	}

}
