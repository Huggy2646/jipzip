package com.jipzip.board.model.dto;

import java.util.ArrayList;

public class Post {

	private int postId;
	private String userId;
	private String title;
	private String content;
	private String createdAt;
	private String lastModifiedAt;
	private int hit;
	private String state;
	private ArrayList<Comment> comments;

	// 생성자들
	public Post() {}
	
	public Post(int postId, String userId, String title, String content, String createdAt, int hit, String state, String lastModifiedAt, ArrayList<Comment> comments) {
		this.postId = postId;
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.createdAt = createdAt;
		this.hit = hit;
		this.state = state;
		this.lastModifiedAt = lastModifiedAt;
		this.comments = comments;
	}
	
	public Post(String userId, String title, String content) {
		this(0, userId, title, content, "", 0, "", "", null);
	}
	
	// ---

	@Override
	public String toString() {
		return "Post [postId=" + postId + ", userId=" + userId + ", title=" + title + ", content=" + content
				+ ", createAt=" + createdAt + ", lastModifiedAt=" + lastModifiedAt + ", hit=" + hit + ", state=" + state
				+ "]";
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getLastModifiedAt() {
		return lastModifiedAt;
	}

	public void setLastModifiedAt(String lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public ArrayList<Comment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}
	
}
