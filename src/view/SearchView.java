package view;

import java.io.File;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Account;
import model.AccountBag;
import model.Post;
import model.PostBag;
import util.Backup;

public class SearchView {
	private VBox searchView = new VBox(80);
	//Searchs posts for a key word or username
	public SearchView(AccountBag accountList, PostBag postList, BorderPane root, Account userAccount) {
		searchView.setAlignment(Pos.CENTER);
 
		HBox welcomeUser = new HBox(80);
		welcomeUser.setAlignment(Pos.CENTER);

		Text welcomeMessage = new Text();
		welcomeMessage.setText("Hi " + userAccount.getUserName() + "\n Searching for something?");
	    Font font = Font.font("System", 40);
	    welcomeMessage.setFont(font);
	    welcomeMessage.setFill(Color.rgb(45, 45, 45));
	    welcomeMessage.setStrokeWidth(.1);
	    welcomeMessage.setStroke(Color.WHITE);
		
		welcomeUser.getChildren().addAll(welcomeMessage);
		searchView.getChildren().addAll(welcomeUser);

		HBox createPost = new HBox(60);
		createPost.setAlignment(Pos.CENTER);
		VBox buttonHolder = new VBox(10);
		buttonHolder.setAlignment(Pos.CENTER);
		Button signOut = new Button("sign out");
		signOut.setMinSize(70, 30);
		Button homePost = new Button("Home");
		homePost.setMinSize(70, 30);
		buttonHolder.getChildren().addAll(signOut, homePost);

		TextField commentInput = new TextField();
		commentInput.setPromptText("Enter word or user you want to search");
		commentInput.setMinWidth(400);
		Button postButton = new Button("Search");
		postButton.setPrefSize(70, 30);
		createPost.getChildren().addAll(buttonHolder, commentInput, postButton);

		signOut.setOnAction(so -> {
			SignInView signInView = new SignInView(accountList, postList, root);
			root.setCenter(signInView.getRoot());
		});

		homePost.setOnAction(hp -> {
			PostView postView = new PostView(accountList, postList, root, userAccount);
			root.setCenter(postView.getRoot());
		});

		searchView.getChildren().addAll(createPost);

		postButton.setOnAction(e -> {
			String Search = commentInput.getText();

			for (int i = postList.size() - 1; i >= 0; i--) {
				boolean isSub = false;
				String userName = postList.get(i).getUserName();
				String userComment = postList.get(i).getComment();
				Post userPost;
				userPost = postList.get(i);

				isSub = userAccount.isSubscribed(userName);
				
				//Checks the postlist for the search
				if (postList.get(i).getUserName().contains(Search) || postList.get(i).getComment().contains(Search)) {
					VBox postBox = new VBox(20);
					postBox.setAlignment(Pos.CENTER);
					postBox.setMinHeight(Control.USE_PREF_SIZE);

					Button commentButton = new Button();

					if (userPost.getImageLocation() != null && new File(userPost.getImageLocation()).exists()) {
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
					}
					

					Button subscribeButton = new Button();
					subscribeButton.setText("Subcribe");
					subscribeButton.setMinSize(100, 25);
					subscribeButton.setOnAction(y -> {
						if (userAccount.isSubscribed(userName)) {
							userAccount.getSubscribed().remove(userName);
							Backup.backupAccountBag(accountList);
							SearchView searchView = new SearchView(accountList, postList, root, userAccount);
							root.setCenter(searchView.getRoot());
						} else {
							userAccount.addSubscribe(userName);
							Backup.backupAccountBag(accountList);
							SearchView searchView = new SearchView(accountList, postList, root, userAccount);
							root.setCenter(searchView.getRoot());
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
							SearchView searchView = new SearchView(accountList, postList, root, userAccount);
							root.setCenter(searchView.getRoot());
						} else {
							userPost.setLikes(userPost.getLikes() + 1);
							userPost.getPeopleWhoLiked().add(userAccount.getUserName());
							Backup.backupPostBag(postList);
							SearchView searchView = new SearchView(accountList, postList, root, userAccount);
							root.setCenter(searchView.getRoot());
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

					searchView.getChildren().addAll(postBox);
				}

			}

		});
	}

	public VBox getRoot() {
		return searchView;
	}
}
