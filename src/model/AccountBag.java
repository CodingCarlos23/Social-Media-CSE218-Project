package model;

import java.io.Serializable;
import java.util.TreeMap;

public class AccountBag implements Serializable {
	//AccountBag our main data set I used treemap because it is very quick and good for growing datasets
	TreeMap<String, Account> accountList;

	public AccountBag() {
		accountList = new TreeMap<String, Account>();
	}

	public void insert(Account account) {
		accountList.put(account.getUserName(), account);
	}

	public Account get(String i) {
		return accountList.get(i);
	}

	public boolean contains(String i) {
		return accountList.containsKey(i);
	}
	public int size() {
		return accountList.size();
	}

	public void display() {
		System.out.println("Start:");
		System.out.println();
		System.out.println(accountList);
	}
}
