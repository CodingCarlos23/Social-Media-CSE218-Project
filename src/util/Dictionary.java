package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Scanner;

public class Dictionary {
	private static HashSet<String> dictionaryHash = new HashSet<>();
	//Hashset used to store all the words very fast and it is all contained in this class for easy use
	public static void dictionaryCreate() {
		File file = new File("backupFolder/dictionary.txt");

		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				dictionaryHash.add(line);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		dictionaryBackup();
	}

	public static void dictionaryBackup() {
		try {
			FileOutputStream fos = new FileOutputStream("backupFolder/dictionaryHash.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(dictionaryHash);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static HashSet<String> dictionaryRestore() {
		try {
			FileInputStream fis = new FileInputStream("backupFolder/dictionaryHash.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			dictionaryHash = (HashSet<String>) ois.readObject();
			ois.close();
			return dictionaryHash;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static HashSet<String> getDictionaryHash() {
		return dictionaryHash;
	}

	public static void setDictionaryHash(HashSet<String> dictionaryHash) {
		Dictionary.dictionaryHash = dictionaryHash;
	}

}
