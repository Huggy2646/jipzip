package com.jipzip.board.model.service;

import java.util.ArrayList;

import com.jipzip.board.model.dto.Comment;
import com.jipzip.board.model.dto.Post;

public interface BoardService {

	// QnA 게시판의 게시글 관련
	public ArrayList<Post> getQnaPosts();
	public Post getQnaPost(int id);
	public int postQnaPost(Post post);
	public int putQnaPost(Post post);
	public int deleteQnaPost(int id);
	public int upQnaPostHit(int id);
	
	// 댓글 관련
	public int postComment(Comment comment);
	public int deleteComment(int id);
	public int putComment(Comment comment);
	public Comment getLatestComment(int id);
	public int changeCommentHeart(int id, int heart);
	public Comment getComment(int id);
	
	// 공지사항 게시판의 게시글 관련
	public Post getNoticePost(int id);
	public ArrayList<Post> getNoticePosts();
	public int postNoticePost(Post post);
	public int putNoticePost(Post post);
	public int deleteNoticePost(int id);
	public int upNoticePostHit(int id);
	
}