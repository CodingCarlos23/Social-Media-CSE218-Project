package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import model.AccountBag;
import model.PostBag;

public class Restore {
	public static AccountBag restoreAccountBag() {
		//used to bring back the data saved on drives
		try {
			FileInputStream fis = new FileInputStream("backupFolder/accountList.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			AccountBag accountList = (AccountBag) ois.readObject();
			ois.close();
			return accountList;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static PostBag restorePostBag() {
		try {
			FileInputStream fis = new FileInputStream("backupFolder/postList.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			PostBag postList = (PostBag) ois.readObject();
			ois.close();
			return postList;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
