package org.gdgankara.app.model;

import java.io.Serializable;
import java.util.List;

public class Speaker implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String biography;
	private String blog;
	private String facebook;
	private String gplus;
	private String name;
	private String photo;
	private String twitter;
	private List<Long> sessionIDList;

	public Speaker() {
		super();
	}

	public Speaker(long id, String biography, String blog, String facebook,
			String gplus, String name, String photo, String twitter, List<Long> sessionIDList) {
		super();
		this.id = id;
		this.biography = biography;
		this.blog = blog;
		this.facebook = facebook;
		this.gplus = gplus;
		this.name = name;
		this.photo = photo;
		this.twitter = twitter;
		this.sessionIDList = sessionIDList;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getGplus() {
		return gplus;
	}

	public void setGplus(String gplus) {
		this.gplus = gplus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public List<Long> getSessionIDList() {
		return sessionIDList;
	}

	public void setSessionIDList(List<Long> sessionIDList) {
		this.sessionIDList = sessionIDList;
	}

}
