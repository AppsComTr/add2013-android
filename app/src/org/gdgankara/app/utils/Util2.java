package org.gdgankara.app.utils;

import java.util.ArrayList;

import org.gdgankara.app.model.Speaker;
import org.gdgankara.app.model.Sponsor;

public class Util2 {
	
	public static ArrayList<Sponsor> SponsorList ;
	public static ArrayList<Speaker> SpeakerList;
	public static void arrayDoldur(){
		SponsorList = new ArrayList<Sponsor>();
		SponsorList.add(new Sponsor(12341241,"Platin Sponsor","http://www.qualcomm.com/","http://www.androiddeveloperdays.com/wp-content/uploads/2013/04/qualcomm.png"));
		SponsorList.add(new Sponsor(12341242,"Gümüþ Sponsor","http://tr.blackberry.com/","http://www.androiddeveloperdays.com/wp-content/uploads/2013/04/BlackBerry_Logo_Preferred_Black_R.jpg"));
		SponsorList.add(new Sponsor(12341243,"Banka ve Finans Sponsoru","http://www.isbank.com.tr/","http://www.androiddeveloperdays.com/wp-content/uploads/2013/04/turkiye-is-bankas%C4%B1-logo_D-300x110.jpg"));
		SponsorList.add(new Sponsor(12341246,"Sponsorlar","http://www.pozitron.com/","http://www.androiddeveloperdays.com/wp-content/uploads/2013/01/pozitron_logo_e_print-300x58.png"));
		SponsorList.add(new Sponsor(12341247,"Sponsorlar","www.google.com","http://www.androiddeveloperdays.com/wp-content/uploads/2013/01/STM_logo-238x300.jpg"));
		SponsorList.add(new Sponsor(12341248,"Sponsorlar","www.google.com","http://www.androiddeveloperdays.com/wp-content/uploads/2013/01/ti_logo-300x82.jpg"));
		SponsorList.add(new Sponsor(12341244,"Basýn Yayýn Sponsoru","www.google.com","http://www.androiddeveloperdays.com/wp-content/uploads/2013/01/shiftdelete_logo-300x107.png"));
		SponsorList.add(new Sponsor(12341245,"Basýn Yayýn Sponsoru","www.google.com","http://www.androiddeveloperdays.com/wp-content/uploads/2013/01/Btnet-300x163.jpg"));
		
		SpeakerList=new ArrayList<Speaker>();
		SpeakerList.add(new Speaker(1245253,"blablabla","Duygu Kahraman","http://www.androiddeveloperdays.com/wp-content/uploads/2013/05/ankarafoto-e1368867450159-150x150.jpg",null));
		SpeakerList.add(new Speaker(1245253,"blablabla","Handan Elbirlik","http://www.androiddeveloperdays.com/wp-content/uploads/2013/05/handan1.jpg",null));
		SpeakerList.add(new Speaker(1245253,"blablabla","Olcay Ay","http://www.androiddeveloperdays.com/wp-content/uploads/2013/05/olcay_vesika-e1368705311429.png",null));
		SpeakerList.add(new Speaker(1245253,"blablabla","Chris Tuttle","http://www.androiddeveloperdays.com/wp-content/uploads/2013/05/chris-e1369030091628.jpeg",null));
		SpeakerList.add(new Speaker(1245253,"blablabla","Selin Güler","http://www.gdgankara.org/wp-content/themes/alltuts2/images/ico_author.png",null));
		SpeakerList.add(new Speaker(1245253,"blablabla","Naci Dai","http://www.gdgankara.org/wp-content/themes/alltuts2/images/ico_author.png",null));
	}
	
	

}
