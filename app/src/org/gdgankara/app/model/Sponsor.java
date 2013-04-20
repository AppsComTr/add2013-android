package org.gdgankara.app.model;

import java.io.Serializable;
import java.util.List;

public class Sponsor implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String LANG_TR = "tr";
	public static final String LANG_EN = "en";
	
	private long id;
	private String name;
	private String logo; // Sponsorun logosu.Duruma g�re bunun t�r� int(drawable
							// id) olabilir.
	private String link;
	private String lang;
	private String description; // Sponsorun tan�t�m yaz�s�

	public Sponsor() {
		super();
	}

	public Sponsor(long id, String name, String logo, String description,
			String link, List<String> linkList) {
		super();
		this.id = id;
		this.name = name;
		this.logo = logo;
		this.description = description;
		this.link = link;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String photo) {
		this.logo = photo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

}
