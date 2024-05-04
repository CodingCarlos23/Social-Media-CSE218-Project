package view;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Account;
import model.AccountBag;
import model.Post;
import model.PostBag;
import util.Backup;
import util.Dictionary;

public class AllPostView {
	private VBox allPostView = new VBox(10);
	private String imageLocation = null;

	public AllPostView(AccountBag accountList, PostBag postList, BorderPane root, Account userAccount) {
		//Displays all posts used in the data
		allPostView.setAlignment(Pos.CENTER);

		HBox welcomeUser = new HBox(80);
		welcomeUser.setAlignment(Pos.CENTER);
		//Welcome message
		Text welcomeMessage = new Text();
		welcomeMessage.setText("Check out the latest posts " + userAccount.getUserName());
	    Font font = Font.font("System", 40);
	    welcomeMessage.setFont(font);
	    welcomeMessage.setFill(Color.rgb(45, 45, 45));
	    welcomeMessage.setStrokeWidth(.1);
	    welcomeMessage.setStroke(Color.WHITE);
		
		welcomeUser.getChildren().addAll(welcomeMessage);
		allPostView.getChildren().addAll(welcomeUser);

		// This will be used create posts
		HBox createPost = new HBox(80);
		createPost.setAlignment(Pos.CENTER);
		VBox buttonHolder = new VBox(10);
		buttonHolder.setAlignment(Pos.CENTER);
		Button signOut = new Button ("sign out");
		signOut.setMinSize(70, 30);
		Button homePost = new Button("Home");
		homePost.setMinSize(70, 30);
		buttonHolder.getChildren().addAll(signOut, homePost);
		
		TextArea commentInput = new TextArea();
		commentInput.setPromptText("Type post here");
		commentInput.setMinSize(400, 100);
		commentInput.setWrapText(true);
		VBox postButtonHolder = new VBox(20);
		postButtonHolder.setAlignment(Pos.CENTER);
		Button addImage = new Button("Want to add a image?");
		addImage.setPrefSize(140, 30);
		Button postButton = new Button("Post");
		postButton.setPrefSize(70, 30);
		postButtonHolder.getChildren().addAll(addImage, postButton);
		createPost.getChildren().addAll(buttonHolder, commentInput, postButtonHolder);

		signOut.setOnAction(so -> {
			SignInView signInView = new SignInView(accountList, postList, root);
			root.setCenter(signInView.getRoot());
		});
		
		homePost.setOnAction(hp -> {
			PostView postView = new PostView(accountList, postList, root, userAccount);
			root.setCenter(postView.getRoot());
		});
		
		addImage.setOnAction(i -> {
			ExtensionFilter filterJPG = new ExtensionFilter("Image jpg files", "*.jpg");
			ExtensionFilter filterPNG = new ExtensionFilter("Image png files", "*.png");
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(filterJPG, filterPNG);
			fileChooser.setInitialDirectory(new File(
					"C:\\My Java Workspace\\My Java Workspace 218\\Carlos Deleon Final Project 218\\imagesFolder"));
			fileChooser.setTitle("Choose a image");

			File selectedFile = fileChooser.showOpenDialog(null);
			if (selectedFile != null) {
				imageLocation = selectedFile.getPath();
			}
			addImage.setStyle("-fx-background-color: #abd7eb; ");
		});

		//Hash used to spell check
		HBox spellCheckBox = new HBox(80);
		spellCheckBox.setAlignment(Pos.CENTER);
		Text spellCheckText = new Text();
		Font spellCheckTextfont = Font.font("System", 40);
		spellCheckText.setFont(spellCheckTextfont);
		spellCheckText.setFill(Color.rgb(45, 45, 45));
		spellCheckText.setStrokeWidth(.2);
		spellCheckText.setStroke(Color.WHITE);
		
		postButton.setOnAction(e -> {
			
			LinkedList<String> spelledWrong = new LinkedList<String>();
			String comment = commentInput.getText();
			String commentTesting = comment.toLowerCase();
			String commentNoEnters = commentTesting.replace("\n", " ");
			commentNoEnters = commentNoEnters.replace("\r", " ");
			commentNoEnters = commentNoEnters.replace("\t", " ");
			String[] spellCheckComment = commentNoEnters.split(" ");
			List<String> finalTesting = new ArrayList<String>(Arrays.asList(spellCheckComment));

			boolean isAllSpelledRight = true;
			for (int i = 0;  i < finalTesting.size(); i++) {
				if (Dictionary.getDictionaryHash().contains(finalTesting.get(i)) || finalTesting.get(i).equals("")) {
				} else {
					spelledWrong.add(finalTesting.get(i));
					isAllSpelledRight= false;
				}
			}
			if(isAllSpelledRight) {
				Post userPost = new Post(userAccount.getUserName(), comment, imageLocation);

				postList.insert(userPost);
				Backup.backupPostBag(postList);
				imageLocation = null;

				AllPostView allPostView = new AllPostView(accountList, postList, root, userAccount);
				root.setCenter(allPostView.getRoot());
			} else {
				spellCheckText.setText("You miss spelled " + spelledWrong);
			}
		});
		
		allPostView.getChildren().addAll(createPost);
		

		spellCheckBox.getChildren().addAll(spellCheckText);
		allPostView.getChildren().addAll(spellCheckBox);
		
		//Displays all posts
		for (int i = postList.size() - 1; i >= 0; i--) {
			boolean isSub = false;
			String userName = postList.get(i).getUserName();
			String userComment = postList.get(i).getComment();
			String imageLocated = postList.get(i).getImageLocation();
			Post userPost;
			userPost = postList.get(i);

			isSub = userAccount.isSubscribed(userName);

				VBox postBox = new VBox(20);
				postBox.setAlignment(Pos.CENTER);
				postBox.setMinHeight(Control.USE_PREF_SIZE);


				
				
				Button commentButton = new Button();
				
				//image content
				if(imageLocated != null && new File(imageLocated).exists()) {
					Image image = new Image(postList.get(i).getImageLocation());
					ImageView imageView=new ImageView();
					imageView.setImage(image);
					imageView.setFitWidth(400);
					imageView.setPreserveRatio(true);
					imageView.setSmooth(true);
					imageView.setCache(true);
					
					commentButton.setMinWidth(400);
					commentButton.setAlignment(Pos.CENTER);
					commentButton.setGraphic(imageView);
					commentButton.setText(userName + " \n" + userComment + "\n -Date Posted: " + postList.get(i).getDate());
					commentButton.setOnAction(x -> {
						ReplyView replyView = new ReplyView(accountList, postList, root, userAccount, userPost,
								postList.getPlacement(userPost));
						root.setCenter(replyView.getRoot());
					});
					commentButton.setContentDisplay(ContentDisplay.TOP);
				} else {
					commentButton.setAlignment(Pos.TOP_LEFT);
					commentButton.setMinWidth(400);
					commentButton.setMinHeight(100);
					commentButton.setText(userName + " \n" + userComment + "\n -Date Posted: " + postList.get(i).getDate());
					commentButton.setOnAction(x -> {
						ReplyView replyView = new ReplyView(accountList, postList, root, userAccount, userPost,
								postList.getPlacement(userPost));
						root.setCenter(replyView.getRoot());
					});
				};

				
				//Subscriber button
				Button subscribeButton = new Button();
				subscribeButton.setText("Subcribe");
				subscribeButton.setMinSize(100, 25);
				subscribeButton.setOnAction(y -> {
					if (userAccount.isSubscribed(userName)) {
						subscribeButton.setStyle("-fx-background-color: #abd7eb; ");
						userAccount.getSubscribed().remove(userName);
						Backup.backupAccountBag(accountList);
						AllPostView allPostView = new AllPostView(accountList, postList, root, userAccount);
						root.setCenter(allPostView.getRoot());
					} else {
						userAccount.addSubscribe(userName);
						Backup.backupAccountBag(accountList);
						subscribeButton.setStyle("-fx-background-color: #00FF00; ");
						AllPostView allPostView = new AllPostView(accountList, postList, root, userAccount);
						root.setCenter(allPostView.getRoot());
					}

				});
				
				isSub = userAccount.isSubscribed(userName);
				if(isSub) {
					subscribeButton.setStyle("-fx-background-color: #abd7eb; ");
				} else {
					subscribeButton.setStyle("-fx-background-color: #808080; ");
				}

				//Like button
				Button likeButton = new Button();
				likeButton.setText("Likes " + userPost.getLikes());
				likeButton.setMinSize(100, 25);
				boolean isPostLiked = userPost.getPeopleWhoLiked().contains(userAccount.getUserName());
				likeButton.setOnAction(l -> {
					if (isPostLiked) {
						userPost.setLikes(userPost.getLikes() - 1);
						userPost.getPeopleWhoLiked().remove(userAccount.getUserName());
						Backup.backupPostBag(postList);
						AllPostView allPostView = new AllPostView(accountList, postList, root, userAccount);
						root.setCenter(allPostView.getRoot());
					} else {
						userPost.setLikes(userPost.getLikes() + 1);
						userPost.getPeopleWhoLiked().add(userAccount.getUserName());
						Backup.backupPostBag(postList);
						AllPostView allPostView = new AllPostView(accountList, postList, root, userAccount);
						root.setCenter(allPostView.getRoot());
					}
				});
				if (isPostLiked) {
					likeButton.setStyle("-fx-background-color: #abd7eb; ");
				} else {
					likeButton.setStyle("-fx-background-color: #808080; ");
				}
				
				HBox postInteractors = new HBox(20);
				postInteractors.setAlignment(Pos.CENTER);
				postInteractors.getChildren().addAll(likeButton, subscribeButton);
				
				postBox.getChildren().addAll(commentButton, postInteractors);

				allPostView.getChildren().addAll(postBox);
			
			
			
		}
		
	}

	public VBox getRoot() {
		return allPostView;
	}
}
