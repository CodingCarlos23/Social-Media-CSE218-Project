package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reply implements Serializable {
	//sepreate data class from posts used to keep things simple for managing data and not get it mixed with other parts
	private String userName;
	private String comment;
	private String date;

	public Reply(String userName, String comment) {
		super();
		this.userName = userName;
		this.comment = comment;

		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
		date = myDateObj.format(myFormatObj);
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "Reply [userName=" + userName + ", comment=" + comment + ", date=" + date + "]";
	}

}
