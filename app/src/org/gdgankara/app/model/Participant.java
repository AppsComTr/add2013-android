package org.gdgankara.app.model;

public class Participant {
	
	private String name;
	private String email;
	private String telephone;
	private String company;
	private String website;
	private String title;
	private String interested;
	
	public Participant(String name, String email, String telephone,
			String company, String website, String title, String interested) {
		super();
		this.name = name;
		this.email = email;
		this.telephone = telephone;
		this.company = company;
		this.website = website;
		this.title = title;
		this.interested = interested;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getCompany() {
		return company;
	}

	public String getWebsite() {
		return website;
	}

	public String getTitle() {
		return title;
	}

	public String getInterested() {
		return interested;
	}
	
	
	
	

}
