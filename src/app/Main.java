package app;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.AccountBag;
import model.PostBag;
import util.Backup;
import util.Dictionary;
import util.Restore;
import view.SignInView;


public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//Here is the code to actually start up the views it sets the root and border as well as getting the data that was saved
		AccountBag accountList;
		if(new File("backupFolder/accountList.dat").exists()) {
			accountList = Restore.restoreAccountBag();
		} else {
			accountList = new AccountBag();
			Backup.backupAccountBag(accountList);
		}
		
		PostBag postList;
		
		if(new File("backupFolder/postList.dat").exists()) {
			postList = Restore.restorePostBag();
		} else {
			postList = new PostBag();
			Backup.backupPostBag(postList);
		}
		
		if(new File("backupFolder/dictionaryHash.dat").exists()) {
			Dictionary.dictionaryRestore();
		} else {
			Dictionary.dictionaryCreate();
		}
		BorderPane root = new BorderPane();
		ScrollPane scrollPane = new ScrollPane();
		
		scrollPane.setContent(root);
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
 
		SignInView signInView = new SignInView(accountList, postList, root);
		root.setCenter(signInView.getRoot());
		Scene scene = new Scene(scrollPane, 1500, 700); //800,600
		//Here is some style code used to make the site nicer
		root.setStyle("-fx-background-color: linear-gradient(to top right, #6a5acd, #1E5631)");
		primaryStage.setScene(scene);
		primaryStage.setTitle("Minecraft Social Media: Your home for the craft");
		primaryStage.show();
		
		//Important areas incase of need
		//C:\My Java Workspace\*\imagesFolder
		// --module-path "C:\*\javafx-sdk-18\lib"
		// --add-modules=javafx.controls,javafx.fxml
	}

}
