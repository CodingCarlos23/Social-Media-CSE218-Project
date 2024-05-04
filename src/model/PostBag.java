package model;

import java.io.Serializable;
import java.util.LinkedList;

public class PostBag implements Serializable {
	//used to hold posts very easy to use
	private LinkedList<Post> postList;
	public PostBag() {
		postList = new LinkedList<Post>();
	}

	public void insert(Post post) {
		postList.add(post);
	}

	public Post get(int i) {
		return postList.get(i);
	}
	
	public int getPlacement(Post post) {
		for(int i = 0; i < postList.size(); i++) {
			if(postList.get(i).equals(post)) {
				return i;
			} 
		}
		return -1;
	}
	
	public int size() {
		return postList.size();
	}

	public void display() {
		System.out.println("Start:");
		System.out.println();
		for (int i = 0; i < postList.size(); i++) {
			System.out.println(postList.get(i));
		}
	}
}
