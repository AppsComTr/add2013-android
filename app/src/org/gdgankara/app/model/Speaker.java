package org.gdgankara.app.model;

import java.io.Serializable;
import java.util.List;

public class Speaker implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String biography;
	private String language;
	private String name;
	private String photo;
	private List<Long> sessionIDList;

	public Speaker() {
		super();
	}

	public Speaker(long id, String biography, String name, String photo, List<Long> sessionIDList) {
		super();
		this.id = id;
		this.biography = biography;
		this.name = name;
		this.photo = photo;
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

	public List<Long> getSessionIDList() {
		return sessionIDList;
	}

	public void setSessionIDList(List<Long> sessionIDList) {
		this.sessionIDList = sessionIDList;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
