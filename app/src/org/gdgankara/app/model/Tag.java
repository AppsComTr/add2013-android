package org.gdgankara.app.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Tag implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String tag;
	private String lang;
	
	public Tag() {
		super();
	}
	
	public Tag(long id, String lang, String tag) {
		this.id = id;
		this.lang = lang;
		this.tag = tag;
	}
	
	public Tag(long id, String tag) {
		this.id = id;
		this.tag = tag;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
	
}
