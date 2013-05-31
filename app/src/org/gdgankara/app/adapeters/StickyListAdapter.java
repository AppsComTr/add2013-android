package org.gdgankara.app.adapeters;

import java.util.ArrayList;

import org.gdgankara.app.R;
import org.gdgankara.app.model.Session;
import org.gdgankara.app.utils.Util;
import org.gdgankara.app.utils.Util2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.emilsjolander.components.stickylistheaders.StickyListHeadersAdapter;

public class StickyListAdapter extends BaseAdapter implements StickyListHeadersAdapter,SectionIndexer{

	private ArrayList<Session> sessions;
	private Integer[] sectionIndex;
	private Integer[] saatKesimleri;
	private String[] saatKesimStringleri;
	private LayoutInflater inflater ;
	private Context context;
	private int textSize;
	private int lang;
	
	public StickyListAdapter(Context context,ArrayList<Session> sessions,int height){
		this.context=context;
		this.sessions=sessions;
		
		saatKesimleriDoldur();
		sessionlariBolumle();
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		setFeaturesTextSize(height);
		lang=Util.getDefaultLanguage().equals("tr")?1:0;
	}
	
	private void saatKesimleriDoldur() {
		saatKesimleri=new Integer[8];
		saatKesimStringleri=new String[8];
		
		saatKesimleri[0]=900;
		saatKesimleri[1]=1000;
		saatKesimleri[2]=1100;
		saatKesimleri[3]=1230;
		saatKesimleri[4]=1330;
		saatKesimleri[5]=1500;
		saatKesimleri[6]=1600;
		saatKesimleri[7]=1700;
		
		saatKesimStringleri[0]="09:00am - 10:00am";
		saatKesimStringleri[1]="10:00am - 11:00am";
		saatKesimStringleri[2]="11:00am - 12:30pm";
		saatKesimStringleri[3]="12:30am - 13:30pm";
		saatKesimStringleri[4]="13:30pm - 15:00pm";
		saatKesimStringleri[5]="15:00pm - 16:00pm";
		saatKesimStringleri[6]="16:00pm - 17:00am";
		saatKesimStringleri[7]="17:00am - 18:30am";
	}

	private void sessionlariBolumle() {
		ArrayList<Integer> temp=new ArrayList<Integer>();
		int j=1;
		int startHour=saatKesimleri[j];
		temp.add(0);
		int size=sessions.size();
		for(int i=0;i<size;i++){
			if(startHour<=saatAyikla(sessions.get(i).getStart_hour())){
				temp.add(i);
				if(j==7){break;}
				startHour=saatKesimleri[++j];
			}
		}
		size=temp.size();
		sectionIndex=new Integer[size];
		for(int i=0;i<size;i++){
			Log.i("Sedattttt****", "index"+i+"="+temp.get(i));
			sectionIndex[i]=temp.get(i);
		}
	}
	
	private int saatAyikla(String temp){
		 temp=temp.replace(" ","");
		 int hour=Integer.parseInt(temp.substring(0,temp.length()-2).replace(":",""));
		 if(temp.substring(temp.length()-2,temp.length()).equals("pm")){
			 if(!temp.substring(0,2).equals("12")){
				 hour+=1200;
			 }
		 }
		 return hour;
	}

	@Override
	public int getCount() {
		return sessions.size();
	}

	@Override
	public Object getItem(int arg0) {
		return sessions.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		Session session=sessions.get(arg0);
		View view=inflater.inflate(R.layout.child_of_sessionlist, null, false);
		TextView text=(TextView)view.findViewById(R.id.session_name);
		text.setText(session.getTitle());
		text=(TextView)view.findViewById(R.id.session_features);
		String temp=session.getDay()==14?lang==1?"Cuma":"Friday":lang==1?"Cumartesi":"Saturday";
		temp+=" "+session.getStart_hour()+" - "+session.getEnd_hour()+" | ";
		temp+=lang==1?session.getHall()+" Salonu":"Hall "+ session.getHall();
		text.setText(temp);
		text.setTextSize(textSize);
		if(session.isFavorite()){
			view.findViewById(R.id.isfavorite).setVisibility(View.VISIBLE);
		}
		return view;
	}

	@Override
	public int getPositionForSection(int arg0) { //Bir bölümün baþlangýç indeksini ver
		if(arg0 >= sectionIndex.length) {
			arg0 = sectionIndex.length-1;
		}else if(arg0 < 0){
			arg0 = 0;
		}
		Log.i("///////sedat\\\\\\\\", "Bir bölgenin hangi indeksten baþladýðý method gelen veri="+arg0+" dönen veri="+sectionIndex[arg0]);
		return sectionIndex[arg0];
	}

	@Override
	public int getSectionForPosition(int arg0) { //Bir pozisyonun hangi section içinde olduðunu ver
		int size=sectionIndex.length;
		for(int i=0;i<size;i++){
			if(sectionIndex[i]>arg0){
				Log.i("///////sedat\\\\\\\\", "Bir pozisyonun hangi bölümde olduðunu gösteren method gelen veri="+arg0+" dönen veri="+(i-1));
				return i-1;
			}
		}
		Log.i("///////sedat\\\\\\\\", "Bir pozisyonun hangi bölümde olduðunu gösteren method gelen veri="+arg0+" dönen veri="+(size-1));
		return size-1;
	}

	@Override
	public Object[] getSections() {
		return saatKesimleri;
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		
		View view=inflater.inflate(R.layout.stickyheader, null, false);
		((TextView)view.findViewById(R.id.programlist_header)).setText(saatKesimStringleri[position]);
		return view;
	}

	@Override
	public long getHeaderId(int position) { //Bir pozisyondaki item'in header'ýný tanýmlayan id dön
		return saatKesimleri[position];
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
	
	
	public void restore(){
		sessions = Util2.SessionList;
		saatKesimleriDoldur();
		notifyDataSetChanged();
	}

}
