package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Account;
import model.AccountBag;
import model.PostBag;

public class SignInView {
	private VBox signInView = new VBox(80);
	
	public SignInView(AccountBag accountList, PostBag postList, BorderPane root) {
		// Sign in start first thing when starting program
		signInView.setAlignment(Pos.CENTER);

		HBox inputArea = new HBox(80);
		inputArea.setAlignment(Pos.CENTER);

		TextField userName = new TextField();
		userName.setPromptText("Username");
		TextField passWord = new TextField();
		passWord.setPromptText("Password");
		Button insertButton = new Button("Insert");
		insertButton.setPrefSize(70, 30);

		Text messageLbl = new Text("Please sign in");
	    Font font = Font.font("System", 30);
	    messageLbl.setFont(font);
	    messageLbl.setFill(Color.rgb(45, 45, 45));
	    messageLbl.setStrokeWidth(2);


		HBox lblBox = new HBox();
		lblBox.setAlignment(Pos.CENTER);
		lblBox.getChildren().add(messageLbl);

		messageLbl.setText("Please sign in");


		Button switchSignUpView = new Button("Want to sign up? Click here");
		switchSignUpView.setPrefSize(300, 50);


		
		insertButton.setOnAction(e -> {
			String username = userName.getText();
			String password = passWord.getText();

			
			Account temp = accountList.get(username);
			
			if(temp == null) {
				messageLbl.setText("No username found");
			} else {
				if(temp.getPassWord().equals(password)) {
					messageLbl.setText("Logged in");
					PostView postView = new PostView(accountList, postList, root, temp);
					root.setCenter(postView.getRoot());
				} else {
					messageLbl.setText("Inncorrect password");
				}
			}
		});
		
		switchSignUpView.setOnAction(e -> {
			SignUpView signUpView = new SignUpView(accountList, postList, root);
			root.setCenter(signUpView.getRoot());
		});
		
		inputArea.getChildren().addAll(userName, passWord, insertButton);
		signInView.getChildren().addAll(inputArea, lblBox, switchSignUpView);

		
	}
	
	public VBox getRoot() {
		return signInView;
	}
}
