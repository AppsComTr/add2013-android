package org.gdgankara.app.model;

import java.util.ArrayList;

public class Session {

	private int id; // eg : 3006
	private String date; // eg : 15 Haziran 2013 Cuma
	private int day; // Eðer Cuma ise 1 , Cumartesi ise 2 set edilecek.
	private String description; // eg : Android geliþtirme ortamýnýn..........anlatýlacaktýr.
	private String start_hour; // eg : 15:30
	private String end_hour; // eg : 16:45
	private String hall; // eg : A
	private int language; // Eðer türkçe ise 1,ingilizde ise 2 set edilecek
	private String title; // eg : Android Uygulamaya Giriþ Çalýþtayý 4
	private ArrayList<Speaker> speakers;
	
	
	public Session(int id, String date, int day, String description,
			String start_hour, String end_hour, String hall, int language,
			String title, ArrayList<Speaker> speakers) {
		super();
		this.id = id;
		this.date = date;
		this.day = day;
		this.description = description;
		this.start_hour = start_hour;
		this.end_hour = end_hour;
		this.hall = hall;
		this.language = language;
		this.title = title;
		this.speakers = speakers;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStart_hour() {
		return start_hour;
	}
	public void setStart_hour(String start_hour) {
		this.start_hour = start_hour;
	}
	public String getEnd_hour() {
		return end_hour;
	}
	public void setEnd_hour(String end_hour) {
		this.end_hour = end_hour;
	}
	public String getHall() {
		return hall;
	}
	public void setHall(String hall) {
		this.hall = hall;
	}
	public int getLanguage() {
		return language;
	}
	public void setLanguage(int language) {
		this.language = language;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ArrayList<Speaker> getSpeakers() {
		return speakers;
	}
	public void setSpeakers(ArrayList<Speaker> speakers) {
		this.speakers = speakers;
	}
	
	
	
	
	
}
