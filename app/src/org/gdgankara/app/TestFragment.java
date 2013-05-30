package org.gdgankara.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.emilsjolander.components.stickylistheaders.*;

public class TestFragment extends Fragment {

	private StickyListHeadersListView programlist;
	private int day;
	private View view;
	
	public void setDay(int day){
		this.day=day;
	}
	
	public void listeleriHazirla(){
		
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	 /*view=inflater.inflate(R.layout.programlist, container, false);
    	 programlist=(StickyListHeadersListView)view.findViewById(R.id.programlist);*/
    	 return inflater.inflate(R.layout.programlist, container, false);
    }

}
