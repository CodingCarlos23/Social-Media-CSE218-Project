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
import model.Account;
import model.AccountBag;
import model.Post;
import model.PostBag;
import model.Reply;
import util.Backup;
import util.Dictionary;

public class ReplyView {
	private VBox replyView = new VBox(10);
	//takes user to reply of the post 
	public ReplyView(AccountBag accountList, PostBag postList, BorderPane root, Account userAccount, Post post, int postPlacement) {
		replyView.setAlignment(Pos.CENTER);

		HBox welcomeUser = new HBox(80);
		welcomeUser.setAlignment(Pos.CENTER);
		
		Text welcomeMessage = new Text();
		welcomeMessage.setText("Check out the replies to this post " + userAccount.getUserName());
	    Font font = Font.font("System", 40);
	    welcomeMessage.setFont(font);
	    welcomeMessage.setFill(Color.rgb(45, 45, 45));
	    welcomeMessage.setStrokeWidth(.1);
	    welcomeMessage.setStroke(Color.WHITE);
		
		welcomeUser.getChildren().addAll(welcomeMessage);
		replyView.getChildren().addAll(welcomeUser);
		
		HBox createPost = new HBox(80);
		createPost.setAlignment(Pos.CENTER);
		Button backButton = new Button("Back");
		backButton.setMinSize(70, 30);
		TextArea commentInput = new TextArea();
		commentInput.setPromptText("Type comment here");
		commentInput.setMinSize(400, 100);
		Button postButton = new Button("Post");
		postButton.setPrefSize(70, 30);
		createPost.getChildren().addAll(backButton, commentInput, postButton);

		replyView.getChildren().addAll(createPost);

		HBox spellCheckBox = new HBox(80);
		spellCheckBox.setAlignment(Pos.CENTER);
		Text spellCheckText = new Text();
		Font spellCheckTextfont = Font.font("System", 40);
		spellCheckText.setFont(spellCheckTextfont);
		spellCheckText.setFill(Color.rgb(45, 45, 45));
		spellCheckText.setStrokeWidth(.2);
		spellCheckText.setStroke(Color.WHITE);
		spellCheckBox.getChildren().addAll(spellCheckText);
		replyView.getChildren().addAll(spellCheckBox);
		
		VBox postBox = new VBox(40);
		postBox.setAlignment(Pos.CENTER);
		postBox.setMinHeight(Control.USE_PREF_SIZE);

		Button commentButton = new Button();
		String commenterName = post.getUserName();
		if (post.getImageLocation() != null && new File(post.getImageLocation()).exists()) {
			Image image = new Image(post.getImageLocation());
			ImageView imageView = new ImageView();
			imageView.setImage(image);
			imageView.setFitWidth(400);
			imageView.setPreserveRatio(true);
			imageView.setSmooth(true);
			imageView.setCache(true);

			commentButton.setMinWidth(400);
			commentButton.setAlignment(Pos.CENTER);
			commentButton.setGraphic(imageView);
			commentButton.setText(post.getUserName() + " \n" + post.getComment() + "\n -Date Posted: " + post.getDate());
			commentButton.setContentDisplay(ContentDisplay.TOP);
		} else {
			commentButton.setAlignment(Pos.TOP_LEFT);
			commentButton.setMinWidth(400);
			commentButton.setMinHeight(100);
			commentButton.setText(post.getUserName() + " \n" + post.getComment() + "\n -Date Posted: " + post.getDate());
		}
		
		Button subscribeButton = new Button();
		subscribeButton.setText("Subcribe");
		subscribeButton.setMinSize(100, 25);
		subscribeButton.setOnAction(y -> {
			if (userAccount.isSubscribed(commenterName)) {
				subscribeButton.setStyle("-fx-background-color: #abd7eb; ");
				userAccount.getSubscribed().remove(commenterName);
				Backup.backupAccountBag(accountList);
				ReplyView replyView = new ReplyView(accountList, postList, root, userAccount, post, postList.getPlacement(post));
				root.setCenter(replyView.getRoot());
			} else {
				userAccount.addSubscribe(commenterName);
				Backup.backupAccountBag(accountList);
				subscribeButton.setStyle("-fx-background-color: #808080; ");
				ReplyView replyView = new ReplyView(accountList, postList, root, userAccount, post, postList.getPlacement(post));
				root.setCenter(replyView.getRoot());
			}

		});

		boolean isSub = userAccount.isSubscribed(commenterName);
		if (isSub) {
			subscribeButton.setStyle("-fx-background-color: #abd7eb; ");
		} else {
			subscribeButton.setStyle("-fx-background-color: #808080; ");
		}
		
		Button likeButton = new Button();
		likeButton.setText("Likes " + post.getLikes());
		likeButton.setMinSize(100, 25);
		boolean isPostLiked = post.getPeopleWhoLiked().contains(userAccount.getUserName());
		likeButton.setOnAction(l -> {
			if (isPostLiked) {
				post.setLikes(post.getLikes() - 1);
				post.getPeopleWhoLiked().remove(userAccount.getUserName());
				Backup.backupPostBag(postList);
				ReplyView replyView = new ReplyView(accountList, postList, root, userAccount, post, postList.getPlacement(post));
				root.setCenter(replyView.getRoot());
			} else {
				post.setLikes(post.getLikes() + 1);
				post.getPeopleWhoLiked().add(userAccount.getUserName());
				Backup.backupPostBag(postList);
				ReplyView replyView = new ReplyView(accountList, postList, root, userAccount, post, postList.getPlacement(post));
				root.setCenter(replyView.getRoot());
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
		replyView.getChildren().addAll(postBox);
		backButton.setOnAction(b -> {
			PostView postView = new PostView(accountList, postList, root, userAccount);
			root.setCenter(postView.getRoot());
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
			for (int i = 0;  i < finalTesting.size(); i++) {
				if (Dictionary.getDictionaryHash().contains(finalTesting.get(i)) || finalTesting.get(i).equals("")) {
				} else {
					spelledWrong.add(finalTesting.get(i));
					isAllSpelledRight= false;
				}
			}
			if(isAllSpelledRight) {
				Reply reply = new Reply(userAccount.getUserName(), comment);
				
				HBox userReplyBox = new HBox(80);
				userReplyBox.setAlignment(Pos.CENTER);
				userReplyBox.setMinHeight(Control.USE_PREF_SIZE);
				
				Button replyButton = new Button();
				replyButton.setMinSize(300, 100);
				replyButton.setText(reply.getUserName() + " \n" + reply.getComment() + "\n -Date Posted: " + reply.getDate());
				replyButton.setAlignment(null);
				userReplyBox.getChildren().addAll(replyButton);
				replyView.getChildren().addAll(userReplyBox);

				postList.get(postPlacement).insertReply(reply);// add reload scene here
				Backup.backupPostBag(postList);
				
				ReplyView replyView = new ReplyView(accountList, postList, root, userAccount, post, postList.getPlacement(post));
				root.setCenter(replyView.getRoot());
			} else {
				spellCheckText.setText("You miss spelled " + spelledWrong);
			}
		});

		// displays all comments
		if (post.getReplies() == null) {

		} else {
			for (int i = 0; i < post.getReplies().size(); i++) {
				String userName = post.getReplies().get(i).getUserName();
				String userComment = post.getReplies().get(i).getComment();
				String postDate = post.getReplies().get(i).getDate();

				HBox replyBox = new HBox(10);
				replyBox.setAlignment(Pos.CENTER);
				replyBox.setMinHeight(Control.USE_PREF_SIZE);
				
				Button replyButton = new Button();
				replyButton.setMinSize(300, 100);
				replyButton.setText(userName + " \n" + userComment + "\n -Date Posted: " + postDate);
				replyButton.setAlignment(null);

				replyBox.getChildren().addAll(replyButton);
				replyView.getChildren().addAll(replyBox);
			}
		}

	}

	public VBox getRoot() {
		return replyView;
	}
}
