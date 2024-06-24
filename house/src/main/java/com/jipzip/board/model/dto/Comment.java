package com.jipzip.board.model.dto;

public class Comment {

	private int commentId;
	private int postId;
	private String lastModifiedAt;
	private String commentContent;
	private String commenter;
	private String createdAt;
	private int heart;
	
	// -- 생성자들 
	public Comment() {}
	
	public Comment(int commentId, int postId, String commentContent, String commenter, String createdAt, String lastModifiedAt, int heart) {
		super();
		this.commentId = commentId;
		this.postId = postId;
		this.commentContent = commentContent;
		this.commenter = commenter;
		this.createdAt = createdAt;
		this.lastModifiedAt = lastModifiedAt;
		this.heart = heart;
	}

	public Comment(String commentContent, String commenter) {
		this(0, 0, commentContent, commenter, "", "", 0);
	}

	// ---
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public String getCommenter() {
		return commenter;
	}
	public void setCommenter(String commenter) {
		this.commenter = commenter;
	}
	public String getLastModifiedAt() {
		return lastModifiedAt;
	}
	public void setLastModifiedAt(String lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}
	
	public int getHeart() {
		return heart;
	}

	public void setHeart(int heart) {
		this.heart = heart;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", postId=" + postId + ", lastModifiedAt=" + lastModifiedAt
				+ ", commentContent=" + commentContent + ", commenter=" + commenter + ", createdAt=" + createdAt
				+ ", heart=" + heart + "]";
	}
}
