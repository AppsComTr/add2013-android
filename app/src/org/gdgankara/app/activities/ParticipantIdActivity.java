package org.gdgankara.app.activities;

import java.util.ArrayList;

import org.gdgankara.app.R;
import org.gdgankara.app.listeners.TabListener;
import org.gdgankara.app.utils.Util;

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

public class ParticipantIdActivity extends BaseActivity{
	
	
	private int height;
	private TabListener tabListener;
	private String message,name,organization,telephone,email,website,title;
	private String[] temp;
	private TextView text;
	private ImageView imageview;
	private ArrayList<String> ParticipantList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		overridePendingTransition(0, 0);
		height=Util.device_height;
		ParticipantList=Util.ParticipantList;
		setActivityTheme();
		setContentView(R.layout.idpage);
		message=this.getIntent().getExtras().getString("SCAN_RESULT");
		temp=message.split("\n");
		alanlaraAyikla();
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
	
	private void alanlaraAyikla() {
		String[] temp2,temp3;
		int size=temp.length;
		for(int i=0;i<size;i++){
			temp2=temp[i].split(":");
			temp3=temp2[0].split(";");
			if(temp3[0].equals("FN")){
				name="";
				for(int k=1;k<temp2.length;k++){
					name+=temp2[k];
					if(k+1<temp2.length){
						name+=":";
					}
				}
			}
			else if(temp3[0].equals("TEL")){
				telephone="";
				for(int k=1;k<temp2.length;k++){
					telephone+=temp2[k];
					if(k+1<temp2.length){
						telephone+=":";
					}
				}
				telephone=telephone.replace(" ","");
			}
			else if(temp3[0].equals("EMAIL")){
				email="";
				for(int k=1;k<temp2.length;k++){
					email+=temp2[k];
					if(k+1<temp2.length){
						email+=":";
					}
				}
			}
			else if(temp3[0].equals("URL")){
				website="";
				for(int k=1;k<temp2.length;k++){
					website+=temp2[k];
					if(k+1<temp2.length){
						website+=":";
					}
				}
				if(!website.equals("")&&!website.contains("http://")){
					website="http://"+website;
				}
			}
			else if(temp3[0].equals("ORG")){
				organization="";
				for(int k=1;k<temp2.length;k++){
					organization+=temp2[k];
					if(k+1<temp2.length){
						organization+=":";
					}
				}
			}
			
			else if(temp3[0].equals("TITLE")){
				title="";
				for(int k=1;k<temp2.length;k++){
					title+=temp2[k];
					if(k+1<temp2.length){
						title+=":";
					}
				}
			}
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
		text.setText(name);
		text=(TextView)findViewById(R.id.idpage_email);
		text.setText(email);
		text=(TextView)findViewById(R.id.idpage_website);
		text.setText(Html.fromHtml("<a href=\""+website+"\">" +website+ "</a>"));
		text.setMovementMethod(LinkMovementMethod.getInstance());
		text=(TextView)findViewById(R.id.idpage_telephone);
		text.setText(telephone);
		text=(TextView)findViewById(R.id.idpage_company);
		text.setText(organization);
		text=(TextView)findViewById(R.id.idpage_jobtitle);
		text.setText(title);
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
		String DisplayName = name+"(ADD)";
		 String MobileNumber = telephone;
		 String emailID = email;
		 String company = organization;
		 String jobTitle = title;
		 String Website = website;
		 String toast_message = null;

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
		 
		//------------------------------------------------------ Names
		 if (website != null) {
		     ops.add(ContentProviderOperation.newInsert(
		     ContactsContract.Data.CONTENT_URI)
		         .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		         .withValue(ContactsContract.Data.MIMETYPE,
		     ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE)
		         .withValue(
		     ContactsContract.CommonDataKinds.Website.URL,
		     Website).build());
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
			 toast_message=Util.getDefaultLanguage().equals("tr")?"Sorry,can\'t add new contact":"Sorry,can't add new contact";
		     getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
		     toast_message=Util.getDefaultLanguage().equals("tr")?"rehberinize eklendi":"added your phonebook";
		     Toast.makeText(this,name+" "+toast_message, Toast.LENGTH_LONG).show();
		 } catch (Exception e) {
		     Toast.makeText(this, toast_message, Toast.LENGTH_LONG).show();
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
