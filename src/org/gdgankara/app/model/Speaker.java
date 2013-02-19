package org.gdgankara.app.model;

import org.gdgankara.app.R;

import android.graphics.drawable.Drawable;

public class Speaker {
	
	private int id;   
	private String name;
	private String biography;
	private String blog;
	private String gplus;
	private String facebook;
	private String twitter;
	private int photo_id; //eg: R.drawable.speaker_1
	
	
	public Speaker(int id, String name, String biography, String blog,
			String gplus, String facebook, String twitter, int photo_id) {
		super();
		this.id = id;
		this.name = name;
		this.biography = biography;
		this.blog = blog;
		this.gplus = gplus;
		this.facebook = facebook;
		this.twitter = twitter;
		this.photo_id = photo_id;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getBiography() {
		return biography;
	}


	public void setBiography(String biography) {
		this.biography = biography;
	}


	public String getBlog() {
		return blog;
	}


	public void setBlog(String blog) {
		this.blog = blog;
	}


	public String getGplus() {
		return gplus;
	}


	public void setGplus(String gplus) {
		this.gplus = gplus;
	}


	public String getFacebook() {
		return facebook;
	}


	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}


	public String getTwitter() {
		return twitter;
	}


	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}


	public int getPhoto_id() {
		return photo_id;
	}


	public void setPhoto_id(int photo_id) {
		this.photo_id = photo_id;
	}
	


}
