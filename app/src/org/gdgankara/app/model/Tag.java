package org.gdgankara.app.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Tag implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String tags;
	private String lang;
	private ArrayList<String> tagList;
	
	public Tag() {
		super();
	}
	
	public Tag(long id, String lang, String tags) {
		this.id = id;
		this.lang = lang;
		this.tags = tags;
	}
	
	public Tag(long id, String tags) {
		this.id = id;
		this.tags = tags;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public ArrayList<String> getTagList() {
		return tagList;
	}

	public void setTagList(ArrayList<String> tagList) {
		this.tagList = tagList;
	}
	
	
	
}
