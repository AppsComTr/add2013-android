package org.gdgankara.app.activities;

import java.util.ArrayList;
import org.gdgankara.app.R;
import org.gdgankara.app.listeners.TabListener;
import org.gdgankara.app.utils.Util;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ParticipantIdActivity extends Activity{
	
	
	private int height;
	private TabListener tabListener;
	private String message;
	private String[] temp;
	private TextView text;
	private ImageView imageview;
	private ArrayList<String> ParticipantList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		height=Util.device_height;
		ParticipantList=Util.ParticipantList;
		setActivityTheme();
		setContentView(R.layout.idpage);
		message=this.getIntent().getExtras().getString("SCAN_RESULT");
		temp=message.split("<#>");
		setUpViews();
		tabAktif();
		int size=ParticipantList.size();
		int i;
		for(i=0;i<size;i++){
			if(ParticipantList.get(i).equals(message)){
				break;
			}
		}
		if(i==size){
			Util.addParticipantToList(this,message);
		}
	}
	
	public void tabAktif(){
		tabListener=new TabListener(this);
		((ImageView)findViewById(R.id.search_button)).setOnClickListener(tabListener);	
		((ImageView)findViewById(R.id.update_button)).setOnClickListener(tabListener);
		((ImageView)findViewById(R.id.qr_decoder_button)).setOnClickListener(tabListener);	
	}
	
	private void setUpViews() {
		text=(TextView)findViewById(R.id.idpage_name);
		text.setText(temp[0]+" "+temp[1]);
		text=(TextView)findViewById(R.id.idpage_email);
		text.setText(temp[2]);
		text=(TextView)findViewById(R.id.idpage_website);
		text.setText(Html.fromHtml("<a href=\""+temp[3]+"\">" +temp[3]+ "</a>"));
		text.setMovementMethod(LinkMovementMethod.getInstance());
		text=(TextView)findViewById(R.id.idpage_telephone);
		text.setText(temp[4]);
		text=(TextView)findViewById(R.id.idpage_company);
		text.setText(temp[5]);
		text=(TextView)findViewById(R.id.idpage_jobtitle);
		text.setText(temp[6]);
		text=(TextView)findViewById(R.id.idpage_interested);
		text.setText(temp[7]);
		imageview=(ImageView)findViewById(R.id.add_contact_button);
		imageview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					addContact();
			}
		});
		if(Util.getDefaultLanguage().equals("tr")){
			imageview.setImageDrawable(getResources().getDrawable(R.drawable.kisi_ekle));
		}
		else{
			imageview.setImageDrawable(getResources().getDrawable(R.drawable.add_contact));
		}
	}
	
	private void addContact() {
		String DisplayName = temp[0]+" "+temp[1]+"(ADD)";
		 String MobileNumber = temp[4].replace(" ","");
		 String HomeNumber = "";
		 String WorkNumber = "";
		 String emailID = temp[2];
		 String company = temp[5];
		 String jobTitle = "";

		 ArrayList < ContentProviderOperation > ops = new ArrayList < ContentProviderOperation > ();

		 ops.add(ContentProviderOperation.newInsert(
		 ContactsContract.RawContacts.CONTENT_URI)
		     .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
		     .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
		     .build());

		 //------------------------------------------------------ Names
		 if (DisplayName != null) {
		     ops.add(ContentProviderOperation.newInsert(
		     ContactsContract.Data.CONTENT_URI)
		         .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		         .withValue(ContactsContract.Data.MIMETYPE,
		     ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
		         .withValue(
		     ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
		     DisplayName).build());
		 }

		 //------------------------------------------------------ Mobile Number                     
		 if (MobileNumber != null) {
		     ops.add(ContentProviderOperation.
		     newInsert(ContactsContract.Data.CONTENT_URI)
		         .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		         .withValue(ContactsContract.Data.MIMETYPE,
		     ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
		         .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MobileNumber)
		         .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
		     ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
		         .build());
		 }

		 //------------------------------------------------------ Home Numbers
		 if (HomeNumber != null) {
		     ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		         .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		         .withValue(ContactsContract.Data.MIMETYPE,
		     ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
		         .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, HomeNumber)
		         .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
		     ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
		         .build());
		 }

		 //------------------------------------------------------ Work Numbers
		 if (WorkNumber != null) {
		     ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		         .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		         .withValue(ContactsContract.Data.MIMETYPE,
		     ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
		         .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, WorkNumber)
		         .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
		     ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
		         .build());
		 }

		 //------------------------------------------------------ Email
		 if (emailID != null) {
		     ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		         .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		         .withValue(ContactsContract.Data.MIMETYPE,
		     ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
		         .withValue(ContactsContract.CommonDataKinds.Email.DATA, emailID)
		         .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
		         .build());
		 }

		 //------------------------------------------------------ Organization
		 if (!company.equals("") && !jobTitle.equals("")) {
		     ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		         .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		         .withValue(ContactsContract.Data.MIMETYPE,
		     ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
		         .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, company)
		         .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
		         .withValue(ContactsContract.CommonDataKinds.Organization.TITLE, jobTitle)
		         .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
		         .build());
		 }

		 // Asking the Contact provider to create a new contact                 
		 try {
		     getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
		     Toast.makeText(this,temp[0]+" "+temp[1]+" "+R.string.message_of_save_contact, Toast.LENGTH_SHORT).show();
		 } catch (Exception e) {
		     Toast.makeText(this, R.string.sorry_cant_save_contact, Toast.LENGTH_SHORT).show();
		 } 
	}

	private void setActivityTheme(){
		

		if(height<=320){
			setTheme(R.style.tagList_low);
		}
		else if(height<=480){
			setTheme(R.style.tagList_Medium);
		}
		else if(height<=800){
			setTheme(R.style.tagList_High);
		}
		else{
			setTheme(R.style.tagList_XHigh);
		}
		
	}

}
