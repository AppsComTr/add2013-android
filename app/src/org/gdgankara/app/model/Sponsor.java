package org.gdgankara.app.model;

import java.io.Serializable;
import java.util.List;

public class Sponsor implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private long id;
	private String logo; //Sponsorun logosu.Duruma göre bunun türü int(drawable id) olabilir.
	private String description; //Sponsorun tanýtým yazýsý
	private List<String> linkList; //Sponsorun baðlantý linki veya linkleri
	
	
	public Sponsor(long id, String logo, String description,
			List<String> linkList) {
		super();
		this.id = id;
		this.logo = logo;
		this.description = description;
		this.linkList = linkList;
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


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public List<String> getLinkList() {
		return linkList;
	}


	public void setLinkList(List<String> linkList) {
		this.linkList = linkList;
	}	
	

}
