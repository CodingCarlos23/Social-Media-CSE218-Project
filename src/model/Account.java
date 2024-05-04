package model;

import java.io.Serializable;
import java.util.TreeSet;

public class Account implements Serializable{
	//Acount class is the foundation of how we will keep track of the users data
	private String userName;
	private String passWord;
	private String email;
	private TreeSet<String> subscribed = new TreeSet<String>();

	public Account(String userName, String passWord, String email) {
		super();
		this.userName = userName;
		this.passWord = passWord;
		this.email = email;
	}
	
	public void addSubscribe(String accountName) {
		subscribed.add(accountName);
	}
	
	public boolean isSubscribed(String accountName) {
		return subscribed.contains(accountName);
	}

	public TreeSet<String> getSubscribed() {
		return subscribed;
	}

	public void setSubscribed(TreeSet<String> subscribed) {
		this.subscribed = subscribed;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Account [userName=" + userName + ", passWord=" + passWord + ", email=" + email + ", subscribed="
				+ subscribed + "]";
	}

}
