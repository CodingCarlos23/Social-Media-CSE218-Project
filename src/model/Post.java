package model;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.TreeSet;

public class Post implements Serializable {
	//How we store the data used in post
	private String userName;
	private String comment;
	private String date;
	private String imageLocation = null;
	private int likes = 0;
	private TreeSet<String> peopleWhoLiked = new TreeSet<String>();

	private LinkedList<Reply> replies = new LinkedList<Reply>();

	public Post(String userName, String comment, String imageLocation) {
		super();
		this.userName = userName;
		this.comment = comment;
		this.imageLocation = imageLocation;

		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
		date = myDateObj.format(myFormatObj);
	}

	public void insertReply(Reply reply) {
		replies.add(reply);
	}

	public LinkedList<Reply> getReplies() {
		return replies;
	}

	public void setReplies(LinkedList<Reply> replies) {
		this.replies = replies;
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

	public String getImageLocation() {
		return imageLocation;
	}

	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public TreeSet<String> getPeopleWhoLiked() {
		return peopleWhoLiked;
	}

	public void setPeopleWhoLiked(TreeSet<String> peopleWhoLiked) {
		this.peopleWhoLiked = peopleWhoLiked;
	}

	@Override
	public String toString() {
		return "Post [userName=" + userName + ", comment=" + comment + ", date=" + date + ", imageLocation="
				+ imageLocation + ", likes=" + likes + ", peopleWhoLiked=" + peopleWhoLiked + ", replies=" + replies
				+ "]";
	}

}
