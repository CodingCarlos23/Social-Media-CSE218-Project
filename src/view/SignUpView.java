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
import util.Backup;

public class SignUpView {
	private VBox signUpView = new VBox(80);

	public SignUpView(AccountBag accountList, PostBag postBag, BorderPane root) {

		signUpView.setAlignment(Pos.CENTER);

		HBox SignUpInputArea = new HBox(80);
		SignUpInputArea.setAlignment(Pos.CENTER);

		TextField userNameCreate = new TextField();
		userNameCreate.setPromptText("Username");
		TextField passWordCreate = new TextField();
		passWordCreate.setPromptText("Password");
		TextField emailCreate = new TextField();
		emailCreate.setPromptText("Email");
		Button insertButtonCreate = new Button("Insert");
		insertButtonCreate.setPrefSize(70, 30);

		Text messageLblSignUp = new Text("Please sign in");
	    Font font = Font.font("System", 30);
	    messageLblSignUp.setFont(font);
	    messageLblSignUp.setFill(Color.rgb(45, 45, 45));
	    messageLblSignUp.setStrokeWidth(2);
	    
		HBox lblBoxSignUp = new HBox();
		lblBoxSignUp.setAlignment(Pos.CENTER);
		lblBoxSignUp.getChildren().add(messageLblSignUp);
		messageLblSignUp.setText("Please sign up");

		Button switchSignInView = new Button("Want to sign in? Click here");
		switchSignInView.setPrefSize(300, 50);
		
		insertButtonCreate.setOnAction(e -> {
			String username = userNameCreate.getText();
			String password = passWordCreate.getText();
			String email = emailCreate.getText();

			if(accountList.contains(username)) {
				messageLblSignUp.setText("Username already exist. Choose a new name");
			} else {
				Account temp = new Account(username, password, email);
				accountList.insert(temp);
				Backup.backupAccountBag(accountList);
				messageLblSignUp.setText("Account Created");
			}
		});
		
		switchSignInView.setOnAction(e -> {
			SignInView signInView = new SignInView(accountList, postBag, root);
			root.setCenter(signInView.getRoot());
		});
		
		SignUpInputArea.getChildren().addAll(userNameCreate, passWordCreate, emailCreate, insertButtonCreate);
		signUpView.getChildren().addAll(SignUpInputArea, lblBoxSignUp, switchSignInView);

	}

	public VBox getRoot() {
		return signUpView;
	}
}
