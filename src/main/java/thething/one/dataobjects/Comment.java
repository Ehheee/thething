package thething.one.dataobjects;

import java.util.Date;

public class Comment {

	Long id;
	Long thingId;
	String text;
	Date date;
	String author;
	Long userId;
	
	
	
	public Comment(Long id, Long thingId, String text, Date date, String author, Long userId) {
		this.id = id;
		this.thingId = thingId;
		this.text = text;
		this.date = date;
		this.author = author;
		this.userId = userId;
	}
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getThingId() {
		return thingId;
	}
	public void setThingId(Long thingId) {
		this.thingId = thingId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}




	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
