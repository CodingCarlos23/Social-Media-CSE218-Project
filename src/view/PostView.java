package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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

public class PostView {
	private VBox postView = new VBox(10);
	private String imageLocation = null;

	public PostView(AccountBag accountList, PostBag postList, BorderPane root, Account userAccount) {
		postView.setAlignment(Pos.CENTER);

		// Site banner first thing you see when you log in
		try {
			InputStream stream = new FileInputStream("imagesFolder/minecraft-banner-1.jpg");

			VBox imageHolder = new VBox(20);
			imageHolder.setAlignment(Pos.CENTER);
			Image image = new Image(stream);
			ImageView imageView = new ImageView();
			imageView.setImage(image);
			imageView.setFitWidth(1000);
			imageView.setPreserveRatio(true);
			imageView.setSmooth(true);
			imageView.setCache(true);
			Button imageButtonTesting = new Button();
			imageButtonTesting.setMinWidth(400);
			imageButtonTesting.setAlignment(Pos.CENTER);
			imageButtonTesting.setGraphic(imageView);

			imageButtonTesting.setContentDisplay(ContentDisplay.TOP);

			imageHolder.getChildren().addAll(imageView);
			postView.getChildren().addAll(imageButtonTesting);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		//Welcome text
		HBox welcomeUser = new HBox(80);
		welcomeUser.setAlignment(Pos.CENTER);
		Text welcomeMessage = new Text();
		welcomeMessage.setText("Welcome " + userAccount.getUserName() + " see what your fav people are posting");
		Font font = Font.font("System", 40);
		welcomeMessage.setFont(font);
		welcomeMessage.setFill(Color.rgb(45, 45, 45));
		welcomeMessage.setStrokeWidth(.1);
		welcomeMessage.setStroke(Color.WHITE);

		welcomeUser.getChildren().addAll(welcomeMessage);
		postView.getChildren().addAll(welcomeUser);

		// This will be used create posts
		HBox createPost = new HBox(80);
		createPost.setAlignment(Pos.CENTER);
		VBox buttonHolder = new VBox(10);
		buttonHolder.setAlignment(Pos.CENTER);
		Button signOut = new Button("sign out");
		signOut.setMinSize(70, 30);
		Button allPost = new Button("View all posts");
		allPost.setMinSize(70, 30);
		Button searchPost = new Button("Search for a post/user");
		searchPost.setMinSize(70, 30);
		buttonHolder.getChildren().addAll(signOut, allPost, searchPost);

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

		allPost.setOnAction(ap -> {
			AllPostView allPostView = new AllPostView(accountList, postList, root, userAccount);
			root.setCenter(allPostView.getRoot());
		});

		searchPost.setOnAction(sp -> {
			SearchView searchView = new SearchView(accountList, postList, root, userAccount);
			root.setCenter(searchView.getRoot());
		});

		postView.getChildren().addAll(createPost);

		//Uses hash to check spelling
		HBox spellCheckBox = new HBox(80);
		spellCheckBox.setAlignment(Pos.CENTER);
		Text spellCheckText = new Text();
		Font spellCheckTextfont = Font.font("System", 40);
		spellCheckText.setFont(spellCheckTextfont);
		spellCheckText.setFill(Color.rgb(45, 45, 45));
		spellCheckText.setStrokeWidth(.2);
		spellCheckText.setStroke(Color.WHITE);
		spellCheckBox.getChildren().addAll(spellCheckText);
		postView.getChildren().addAll(spellCheckBox);

		// Displays current posts but only subscribed ones
		for (int i = postList.size() - 1; i >= 0; i--) {
			boolean isSub = false;
			String userName = postList.get(i).getUserName();
			String userComment = postList.get(i).getComment();
			String imageLocated = postList.get(i).getImageLocation();
			Post userPost;
			userPost = postList.get(i);

			isSub = userAccount.isSubscribed(userName);

			if (isSub) {
				VBox postBox = new VBox(20);
				postBox.setAlignment(Pos.CENTER);
				postBox.setMinHeight(Control.USE_PREF_SIZE);

				Button commentButton = new Button();

				// image content
				if (imageLocated != null && new File(imageLocated).exists()) {
					Image image = new Image(postList.get(i).getImageLocation());
					ImageView imageView = new ImageView();
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
						ReplyView replyView = new ReplyView(accountList, postList, root, userAccount, userPost,postList.getPlacement(userPost));
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
				}

				Button subscribeButton = new Button();
				subscribeButton.setText("Subcribe");
				subscribeButton.setMinSize(100, 25);
				subscribeButton.setOnAction(y -> {
					if (userAccount.isSubscribed(userName)) {
						subscribeButton.setStyle("-fx-background-color: #abd7eb; ");
						userAccount.getSubscribed().remove(userName);
						Backup.backupAccountBag(accountList);
						PostView postView = new PostView(accountList, postList, root, userAccount);
						root.setCenter(postView.getRoot());
					} else {
						userAccount.addSubscribe(userName);
						Backup.backupAccountBag(accountList);
						subscribeButton.setStyle("-fx-background-color: #00FF00; ");
						PostView postView = new PostView(accountList, postList, root, userAccount);
						root.setCenter(postView.getRoot());
					}

				});

				isSub = userAccount.isSubscribed(userName);
				if (isSub) {
					subscribeButton.setStyle("-fx-background-color: #abd7eb; ");
				} else {
					subscribeButton.setStyle("-fx-background-color: #808080; ");
				}

				Button likeButton = new Button();
				likeButton.setText("Likes " + userPost.getLikes());
				likeButton.setMinSize(100, 25);
				boolean isPostLiked = userPost.getPeopleWhoLiked().contains(userAccount.getUserName());
				likeButton.setOnAction(l -> {
					if (isPostLiked) {
						userPost.setLikes(userPost.getLikes() - 1);
						userPost.getPeopleWhoLiked().remove(userAccount.getUserName());
						Backup.backupPostBag(postList);
						PostView postView = new PostView(accountList, postList, root, userAccount);
						root.setCenter(postView.getRoot());
					} else {
						userPost.setLikes(userPost.getLikes() + 1);
						userPost.getPeopleWhoLiked().add(userAccount.getUserName());
						Backup.backupPostBag(postList);
						PostView postView = new PostView(accountList, postList, root, userAccount);
						root.setCenter(postView.getRoot());
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
				postView.getChildren().addAll(postBox);
			}

		}
		
		//Adds images
		addImage.setOnAction(i -> {
			ExtensionFilter filterJPG = new ExtensionFilter("Image jpg files", "*.jpg");
			ExtensionFilter filterPNG = new ExtensionFilter("Image png files", "*.png");
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(filterJPG, filterPNG);
			if (new File("C:\\My Java Workspace\\My Java Workspace 218\\Carlos Deleon Final Project 218\\imagesFolder").exists()) {
				fileChooser.setInitialDirectory(new File("C:\\My Java Workspace\\My Java Workspace 218\\Carlos Deleon Final Project 218\\imagesFolder"));
			}

			fileChooser.setTitle("Choose a image");

			File selectedFile = fileChooser.showOpenDialog(null);
			if (selectedFile != null) {
				imageLocation = selectedFile.getPath();
			}
			
			addImage.setStyle("-fx-background-color: #abd7eb; ");
		});

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
			for (int i = 0; i < finalTesting.size(); i++) {
				if (Dictionary.getDictionaryHash().contains(finalTesting.get(i)) || finalTesting.get(i).equals("")) {
				} else {
					spelledWrong.add(finalTesting.get(i));
					isAllSpelledRight = false;
				}
			}
			if (isAllSpelledRight) {
				Post userPost = new Post(userAccount.getUserName(), comment, imageLocation);

				postList.insert(userPost);
				Backup.backupPostBag(postList);
				imageLocation = null;

				PostView postView = new PostView(accountList, postList, root, userAccount);
				root.setCenter(postView.getRoot());
			} else {
				spellCheckText.setText("You miss spelled " + spelledWrong);
			}

		});

	}

	public VBox getRoot() {
		return postView;
	}
}
