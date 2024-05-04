package util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import model.AccountBag;
import model.PostBag;

public class Backup {
	//Here is where I back up the bags to the drive
	public static void backupAccountBag(AccountBag accountList) {
		try {
			FileOutputStream fos = new FileOutputStream("backupFolder/accountList.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(accountList);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void backupPostBag(PostBag postList) {
		try {
			FileOutputStream fos = new FileOutputStream("backupFolder/postList.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(postList);
			oos.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
