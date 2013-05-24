package org.gdgankara.app.utils;

import java.util.ArrayList;

import org.gdgankara.app.model.Announcement;
import org.gdgankara.app.model.Speaker;
import org.gdgankara.app.model.Sponsor;

public class Util2 {
	
	public static ArrayList<Sponsor> SponsorList ;
	public static ArrayList<Speaker> SpeakerList;
	public static ArrayList<Announcement> AnnouncementList;
	
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
				"http://www.androiddeveloperdays.com/", "Android Developer Days"));
	}
	
	

}
