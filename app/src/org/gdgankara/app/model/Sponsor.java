package org.gdgankara.app.model;

import java.io.Serializable;
import java.util.List;

public class Sponsor implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String KIND = "sponsor";
	public static final String LANG_TR = "tr";
	public static final String LANG_EN = "en";

	private long id;
	private String category;
	private String logo; // Sponsorun logosu.Duruma g�re bunun t�r� int(drawable
							// id) olabilir.
	private String link;

	public Sponsor() {
		super();
	}

	public Sponsor(long id, String category, String link,
			String logo) {
		super();
		this.id = id;
		this.category = category;
		this.link = link;
		this.logo = logo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String photo) {
		this.logo = photo;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
