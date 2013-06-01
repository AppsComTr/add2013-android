package org.gdgankara.app.fragment;

import java.util.ArrayList;

import org.gdgankara.app.activities.SessionPageActivity;
import org.gdgankara.app.adapeters.StickyListAdapter;
import org.gdgankara.app.model.Session;
import org.gdgankara.app.utils.Util;
import org.gdgankara.app.utils.Util2;
import org.gdgankara.app.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.emilsjolander.components.stickylistheaders.*;

public class ProgramFragment extends Fragment implements AdapterView.OnItemClickListener{

	private StickyListHeadersListView programlist;
	private int day;
	private View view;
	private ArrayList<Session> sessions;
	private Context context;
	private StickyListAdapter sticky_adapter;
	
	public void setDay(int day){
		this.day=day;
	}
	
	public void setContext(Context context){
		this.context=context;
	}
	
	public void listeleriHazirla(){
		ArrayList<Session> total=Util.SessionList;
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
    	 sticky_adapter=new StickyListAdapter(getActivity(), sessions, Util.device_height);
    	 programlist.setAdapter(sticky_adapter);
    	 programlist.setOnItemClickListener(this);
    	 return view;
    }
    
    @Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
    	startSessionPage(position);
	}
    
    public void listeleriYenile(){
    	sticky_adapter.notifyDataSetChanged();
    }

	private void startSessionPage(int position) {
		if(!sessions.get(position).isBreak()){
			Intent intent=new Intent(context,SessionPageActivity.class);
			Bundle b=new Bundle();
			Long id=sessions.get(position).getId();
			b.putLong("id", id);
			intent.putExtras(b);
			this.startActivity(intent);
		}
	}
    

}
