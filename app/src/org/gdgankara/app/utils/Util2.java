package org.gdgankara.app.utils;

import java.util.ArrayList;

import org.gdgankara.app.model.Announcement;
import org.gdgankara.app.model.Session;
import org.gdgankara.app.model.Speaker;
import org.gdgankara.app.model.Sponsor;

public class Util2 {
	
	public static ArrayList<Sponsor> SponsorList ;
	public static ArrayList<Speaker> SpeakerList;
	public static ArrayList<Announcement> AnnouncementList;
	public static ArrayList<Session> SessionList;
	
	public static void arrayDoldur(){
		/*SponsorList = new ArrayList<Sponsor>();
		SponsorList.add(new Sponsor(12341241,"Platin Sponsor","http://www.qualcomm.com/","http://www.androiddeveloperdays.com/wp-content/uploads/2013/04/qualcomm.png"));
		SponsorList.add(new Sponsor(12341242,"Gümüþ Sponsor","http://tr.blackberry.com/","http://www.androiddeveloperdays.com/wp-content/uploads/2013/04/BlackBerry_Logo_Preferred_Black_R.jpg"));
		SponsorList.add(new Sponsor(12341243,"Banka ve Finans Sponsoru","http://www.isbank.com.tr/","http://www.androiddeveloperdays.com/wp-content/uploads/2013/04/turkiye-is-bankas%C4%B1-logo_D-300x110.jpg"));
		SponsorList.add(new Sponsor(12341246,"Sponsorlar","http://www.pozitron.com/","http://www.androiddeveloperdays.com/wp-content/uploads/2013/01/pozitron_logo_e_print-300x58.png"));
		SponsorList.add(new Sponsor(12341247,"Sponsorlar","www.google.com","http://www.androiddeveloperdays.com/wp-content/uploads/2013/01/STM_logo-238x300.jpg"));
		SponsorList.add(new Sponsor(12341248,"Sponsorlar","www.google.com","http://www.androiddeveloperdays.com/wp-content/uploads/2013/01/ti_logo-300x82.jpg"));
		SponsorList.add(new Sponsor(12341244,"Basýn Yayýn Sponsoru","www.google.com","http://www.androiddeveloperdays.com/wp-content/uploads/2013/01/shiftdelete_logo-300x107.png"));
		SponsorList.add(new Sponsor(12341245,"Basýn Yayýn Sponsoru","www.google.com","http://www.androiddeveloperdays.com/wp-content/uploads/2013/01/Btnet-300x163.jpg"));
		
		SpeakerList=new ArrayList<Speaker>();
		SpeakerList.add(new Speaker(1245253,"blablabla","Duygu Kahraman Elibol Ahmet Mehmet YeþilDüve","http://www.androiddeveloperdays.com/wp-content/uploads/2013/05/ankarafoto-e1368867450159-150x150.jpg",null));
		SpeakerList.add(new Speaker(1245253,"blablabla","Handan Elbirlik","http://www.androiddeveloperdays.com/wp-content/uploads/2013/05/handan1.jpg",null));
		SpeakerList.add(new Speaker(1245253,"blablabla","Olcay Ay","http://www.androiddeveloperdays.com/wp-content/uploads/2013/05/olcay_vesika-e1368705311429.png",null));
		SpeakerList.add(new Speaker(1245253,"blablabla","Chris Tuttle","http://www.androiddeveloperdays.com/wp-content/uploads/2013/05/chris-e1369030091628.jpeg",null));
		SpeakerList.add(new Speaker(1245253,"blablabla","Selin Güler","http://www.gdgankara.org/wp-content/themes/alltuts2/images/ico_author.png",null));
		SpeakerList.add(new Speaker(1245253,"blablabla","Naci Dai","http://www.gdgankara.org/wp-content/themes/alltuts2/images/ico_author.png",null));
		
		AnnouncementList=new ArrayList<Announcement>();
		AnnouncementList.add(new Announcement(1235234,"TR_Cupcake ipsum dolor sit amet sweet chupa chups cupcake. Oat cake cookie I love sweet roll tart cookie wypas. Marshmallow I love fruitcake pudding apple pie. Croissant danish lemon drops fruitcake croissant. Chocolate cake marshmallow faworki powder apple pie bear claw oat cake. Gummi bears jujubes I love faworki I love I love fruitcake sweet roll pudding. Powder gingerbread soufflÃ© donut liquorice powder sugar plum. Croissant gingerbread apple pie danish topping dragÃ©e gingerbread wafer. Chupa chups I love muffin. I love danish wypas candy cookie gingerbread pudding. Marshmallow marzipan powder wafer topping chocolate cake danish I love sweet roll. Brownie sweet roll macaroon cookie jelly-o. Gingerbread danish tootsie roll wypas dragÃ©e. Dessert halvah cheesecake gummies I love tiramisu I love.", "http://www.androiddeveloperdays.com/wp-content/themes/gtugtheme2/images/header.png", false, "tr",
				"http://www.androiddeveloperdays.com/", "Android Developer Days Baþlamak Üzere Israrla"));
		AnnouncementList.add(new Announcement(1235235,"TR_Cupcake ipsum dolor sit amet sweet chupa chups cupcake. Oat cake cookie I love sweet roll tart cookie wypas. Marshmallow I love fruitcake pudding apple pie. Croissant danish lemon drops fruitcake croissant. Chocolate cake marshmallow faworki powder apple pie bear claw oat cake. Gummi bears jujubes I love faworki I love I love fruitcake sweet roll pudding. Powder gingerbread soufflÃ© donut liquorice powder sugar plum. Croissant gingerbread apple pie danish topping dragÃ©e gingerbread wafer. Chupa chups I love muffin. I love danish wypas candy cookie gingerbread pudding. Marshmallow marzipan powder wafer topping chocolate cake danish I love sweet roll. Brownie sweet roll macaroon cookie jelly-o. Gingerbread danish tootsie roll wypas dragÃ©e. Dessert halvah cheesecake gummies I love tiramisu I love.", "http://www.androiddeveloperdays.com/wp-content/themes/gtugtheme2/images/header.png", false, "tr",
				"http://www.androiddeveloperdays.com/", "Android Developer Days Baþlamak Üzere Israrla Bayinizden istemeyi unutmayýnýz"));
		AnnouncementList.add(new Announcement(1235236,"TR_Cupcake ipsum dolor sit amet sweet chupa chups cupcake. Oat cake cookie I love sweet roll tart cookie wypas. Marshmallow I love fruitcake pudding apple pie. Croissant danish lemon drops fruitcake croissant. Chocolate cake marshmallow faworki powder apple pie bear claw oat cake. Gummi bears jujubes I love faworki I love I love fruitcake sweet roll pudding. Powder gingerbread soufflÃ© donut liquorice powder sugar plum. Croissant gingerbread apple pie danish topping dragÃ©e gingerbread wafer. Chupa chups I love muffin. I love danish wypas candy cookie gingerbread pudding. Marshmallow marzipan powder wafer topping chocolate cake danish I love sweet roll. Brownie sweet roll macaroon cookie jelly-o. Gingerbread danish tootsie roll wypas dragÃ©e. Dessert halvah cheesecake gummies I love tiramisu I love.", "http://www.androiddeveloperdays.com/wp-content/themes/gtugtheme2/images/header.png", false, "tr",
				"http://www.androiddeveloperdays.com/", "Android Developer Days"));
		AnnouncementList.add(new Announcement(1235237,"TR_Cupcake ipsum dolor sit amet sweet chupa chups cupcake. Oat cake cookie I love sweet roll tart cookie wypas. Marshmallow I love fruitcake pudding apple pie. Croissant danish lemon drops fruitcake croissant. Chocolate cake marshmallow faworki powder apple pie bear claw oat cake. Gummi bears jujubes I love faworki I love I love fruitcake sweet roll pudding. Powder gingerbread soufflÃ© donut liquorice powder sugar plum. Croissant gingerbread apple pie danish topping dragÃ©e gingerbread wafer. Chupa chups I love muffin. I love danish wypas candy cookie gingerbread pudding. Marshmallow marzipan powder wafer topping chocolate cake danish I love sweet roll. Brownie sweet roll macaroon cookie jelly-o. Gingerbread danish tootsie roll wypas dragÃ©e. Dessert halvah cheesecake gummies I love tiramisu I love.", "http://www.androiddeveloperdays.com/wp-content/themes/gtugtheme2/images/header.png", false, "tr",
				"http://www.androiddeveloperdays.com/", "Android Developer Days"));
		AnnouncementList.add(new Announcement(1235238,"TR_Cupcake ipsum dolor sit amet sweet chupa chups cupcake. Oat cake cookie I love sweet roll tart cookie wypas. Marshmallow I love fruitcake pudding apple pie. Croissant danish lemon drops fruitcake croissant. Chocolate cake marshmallow faworki powder apple pie bear claw oat cake. Gummi bears jujubes I love faworki I love I love fruitcake sweet roll pudding. Powder gingerbread soufflÃ© donut liquorice powder sugar plum. Croissant gingerbread apple pie danish topping dragÃ©e gingerbread wafer. Chupa chups I love muffin. I love danish wypas candy cookie gingerbread pudding. Marshmallow marzipan powder wafer topping chocolate cake danish I love sweet roll. Brownie sweet roll macaroon cookie jelly-o. Gingerbread danish tootsie roll wypas dragÃ©e. Dessert halvah cheesecake gummies I love tiramisu I love.", "http://www.androiddeveloperdays.com/wp-content/themes/gtugtheme2/images/header.png", false, "tr",
				"http://www.androiddeveloperdays.com/", "Android Developer Days"));*/
		
		SessionList=new ArrayList<Session>();
		SessionList.add(new Session(14, "tr", 1029194, false, "Friday, June 14, 2013", "sss", "09:00am", "09:30am", "C", "Android ile Sohbetler", null, "sss"));
		SessionList.add(new Session(14, "tr", 1029194, false, "Friday, June 14, 2013", "sss", "09:45am", "10:00am", "C", "Android ile Sohbetler 2", null, "sss"));
		SessionList.add(new Session(14, "tr", 1029194, false, "Friday, June 14, 2013", "sss", "10:15am", "10:30am", "C", "Android ile Sohbetler 3", null, "sss"));
		SessionList.add(new Session(14, "tr", 1029194, false, "Friday, June 14, 2013", "sss", "10:30am", "11:00am", "C", "Android ile Sohbetler 4", null, "sss"));
		SessionList.add(new Session(14, "tr", 1029194, false, "Friday, June 14, 2013", "sss", "10:45am", "11:30am", "C", "Android ile Sohbetler 5", null, "sss"));
		SessionList.add(new Session(14, "tr", 1029194, false, "Friday, June 14, 2013", "sss", "11:00am", "12:00am", "C", "Android ile Sohbetler 6", null, "sss"));
		SessionList.add(new Session(14, "tr", 1029194, false, "Friday, June 14, 2013", "sss", "11:00am", "11:30am", "C", "Android ile Sohbetler 7", null, "sss"));
		SessionList.add(new Session(14, "tr", 1029194, false, "Friday, June 14, 2013", "sss", "12:30pm", "1:00pm", "C", "Android ile Sohbetler 8" , null, "sss"));
		SessionList.add(new Session(14, "tr", 1029194, false, "Friday, June 14, 2013", "sss", "12:30pm", "1:30pm", "C", "Android ile Sohbetler 9" , null, "sss"));
		SessionList.add(new Session(14, "tr", 1029194, false, "Friday, June 14, 2013", "sss", "1:00pm", "1:30pm", "C", "Android ile Sohbetler 10", null, "sss"));
		SessionList.add(new Session(14, "tr", 1029194, false, "Friday, June 14, 2013", "sss", "1:45pm", "2:30pm", "C", "Android ile Sohbetler 11", null, "sss"));
		SessionList.add(new Session(14, "tr", 1029194, false, "Friday, June 14, 2013", "sss", "2:00pm", "3:30pm", "C", "Android ile Sohbetler 12", null, "sss"));
		SessionList.add(new Session(14, "tr", 1029194, false, "Friday, June 14, 2013", "sss", "2:15pm", "3:00pm", "C", "Android ile Sohbetler 13", null, "sss"));
		SessionList.add(new Session(14, "tr", 1029194, false, "Friday, June 14, 2013", "sss", "2:45pm", "3:45pm", "C", "Android ile Sohbetler 14", null, "sss"));
		SessionList.add(new Session(14, "tr", 1029194, false, "Friday, June 14, 2013", "sss", "3:15pm", "3:30pm", "C", "Android ile Sohbetler 15", null, "sss"));
		SessionList.add(new Session(14, "tr", 1029194, false, "Friday, June 14, 2013", "sss", "3:30pm", "4:00pm", "C", "Android ile Sohbetler 16", null, "sss"));
		SessionList.add(new Session(14, "tr", 1029194, false, "Friday, June 14, 2013", "sss", "4:00pm", "4:30pm", "C", "Android ile Sohbetler 17", null, "sss"));
		SessionList.add(new Session(14, "tr", 1029194, false, "Friday, June 14, 2013", "sss", "4:45pm", "5:00pm", "C", "Android ile Sohbetler 18", null, "sss"));
		SessionList.add(new Session(14, "tr", 1029194, false, "Friday, June 14, 2013", "sss", "5:15pm", "5:30pm", "C", "Android ile Sohbetler 19", null, "sss"));
		SessionList.add(new Session(14, "tr", 1029194, false, "Friday, June 14, 2013", "sss", "5:45pm", "6:00pm", "C", "Android ile Sohbetler 20", null, "sss"));
		SessionList.add(new Session(14, "tr", 1029194, false, "Friday, June 14, 2013", "sss", "6:15pm", "6300pm", "C", "Android ile Sohbetler 21", null, "sss"));
		
	}
	
	

}
