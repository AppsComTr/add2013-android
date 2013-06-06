package org.gdgankara.app.adapeters;

import java.util.ArrayList;
import org.gdgankara.app.R;
import org.gdgankara.app.model.Session;
import org.gdgankara.app.utils.Util;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import com.emilsjolander.components.stickylistheaders.StickyListHeadersAdapter;

public class StickyListAdapter extends BaseAdapter implements StickyListHeadersAdapter,SectionIndexer{

	private ArrayList<Session> sessions;
	private int[] sectionIndex;
	private Integer[] saatKesimleri;
	private String[] saatKesimStringleri;
	private LayoutInflater inflater ;
	private Context context;
	private int textSize;
	private int lang;
	private Session session;
	private View view;
	private TextView text;
	private ImageView image;
	
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
		saatKesimStringleri[3]="12:30pm - 13:30pm";
		saatKesimStringleri[4]="01:30pm - 03:00pm";
		saatKesimStringleri[5]="03:00pm - 04:00pm";
		saatKesimStringleri[6]="04:00pm - 05:00pm";
		saatKesimStringleri[7]="05:00pm - 06:30pm";
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
		sectionIndex=new int[size];
		for(int i=0;i<size;i++){
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
		session=sessions.get(arg0);
		view=inflater.inflate(R.layout.child_of_sessionlist, null, false);
		text=(TextView)view.findViewById(R.id.session_name);
		text.setText(session.getTitle());
		text=(TextView)view.findViewById(R.id.session_features);
		String temp=session.getDay()==14?lang==1?"Cuma":"Friday":lang==1?"Cumartesi":"Saturday";
		temp+=" "+session.getStart_hour()+" - "+session.getEnd_hour();
		
		if(session.isFavorite()){
			view.findViewById(R.id.isfavorite).setVisibility(View.VISIBLE);
		}
		if(session.isBreak()){
			if(session.getTitle().equals("Ara") || session.getTitle().equals("Break")){
				image=(ImageView)view.findViewById(R.id.breaktype);
				image.setImageDrawable(context.getResources().getDrawable(R.drawable.coffee_break));
				image.setVisibility(View.VISIBLE);
			}
			else if(session.getTitle().equals("Öðle Arasý") || session.getTitle().equals("Lunch Break")){
				image=(ImageView)view.findViewById(R.id.breaktype);
				image.setImageDrawable(context.getResources().getDrawable(R.drawable.lunch_break));
				image.setVisibility(View.VISIBLE);
			}
		}
		else{
			temp+=" | ";
			temp+=lang==1?session.getHall()+" Salonu":"Hall "+ session.getHall();
		}
		text.setText(temp);
		text.setTextSize(textSize);
		return view;
	}

	@Override
	public int getPositionForSection(int arg0) { //Bir bölümün baþlangýç indeksini ver
		if(arg0 >= sectionIndex.length) {
			arg0 = sectionIndex.length-1;
		}else if(arg0 < 0){
			arg0 = 0;
		}
		return sectionIndex[arg0];
	}

	@Override
	public int getSectionForPosition(int arg0) { //Bir pozisyonun hangi section içinde olduðunu ver
		int size=sectionIndex.length;
		for(int i=0;i<size;i++){
			if(sectionIndex[i]>arg0){
				return i-1;
			}
		}
		return size-1;
	}

	@Override
	public Object[] getSections() {
		return saatKesimleri;
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		
		View view=inflater.inflate(R.layout.stickyheader, null, false);
		((TextView)view.findViewById(R.id.programlist_header)).setText(saatKesimStringleri[getSectionForPosition(position)]);
		return view;
	}

	@Override
	public long getHeaderId(int position) { //Bir pozisyondaki item'in header'ýný tanýmlayan id dön
		return saatKesimleri[getSectionForPosition(position)];
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