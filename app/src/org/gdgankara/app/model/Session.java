package org.gdgankara.app.model;

import java.io.Serializable;
import java.util.List;

public class Session implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String KIND = "session";
	public static final String LANG_TR = "tr";
	public static final String LANG_EN = "en";
	public static final int DAY_FRIDAY = 14;
	public static final int DAY_SATURDAY = 15;

	private long id;
	private String date;
	protected int day;
	private String description;
	private String end_hour;
	private String hall;
	private boolean isBreak;
	private boolean isFavorite;
	protected String language;
	private List<Long> speakerIDList;
	private String start_hour;
	private String tags;
	private String title; 

	public Session() {
		super();
	}

	public Session(int day, String language, long id, boolean isBreak,
			String date, String description, String start_hour,
			String end_hour, String hall, String title, List<Long> speakerIDList,
			String tags) {
		super();
		this.day = day;
		this.language = language;
		this.id = id;
		this.isBreak = isBreak;
		this.date = date;
		this.description = description;
		this.start_hour = start_hour;
		this.end_hour = end_hour;
		this.hall = hall;
		this.title = title;
		this.speakerIDList = speakerIDList;
		this.tags = tags;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isBreak() {
		return isBreak;
	}

	public void setBreak(boolean isBreak) {
		this.isBreak = isBreak;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Long> getSpeakerIDList() {
		return speakerIDList;
	}

	public void setSpeakerIDList(List<Long> speakerIDList) {
		this.speakerIDList = speakerIDList;
	}
	
	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public boolean isFavorite() {
		return isFavorite;
	}

	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}

}
