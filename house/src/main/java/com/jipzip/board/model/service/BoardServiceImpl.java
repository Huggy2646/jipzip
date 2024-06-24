package com.jipzip.board.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jipzip.board.mapper.BoardMapper;
import com.jipzip.board.model.dto.Comment;
import com.jipzip.board.model.dto.Post;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	BoardMapper boardMapper;

	/**
	 * QnA 게시판 관련
	 * */
	@Override
	@Transactional
	public ArrayList<Post> getQnaPosts() {
		return boardMapper.getQnaPosts();
	}

	@Override
	@Transactional
	// id에 해당하는 게시글과 댓글을 함께 가져온다
	public Post getQnaPost(int id) {
		Post post = boardMapper.getQnaPost(id);
		ArrayList<Comment> comments = boardMapper.getComments(id);
		
		if (post != null) post.setComments(comments);

		return post;
	}
	
	@Override
	@Transactional
	public int postQnaPost(Post post) {
		return boardMapper.postQnaPost(post);
	}

	@Override
	@Transactional
	public int putQnaPost(Post post) {
		return boardMapper.putQnaPost(post);
	}

	@Override
	@Transactional
	public int deleteQnaPost(int id) {
		int res1 = boardMapper.deleteComments(id);
		int res2 = boardMapper.deleteQnaPost(id);
		return (res1 == 1 && res2 == 1) ? 1 : 0;
	}
	
	@Override
	@Transactional
	public int upQnaPostHit(int id) {
		return boardMapper.upQnaPostHit(id);
	}

	/**
	 * 댓글 관련
	 * */
	@Override
	@Transactional
	public int postComment(Comment comment) {
		int res1 = boardMapper.postComment(comment);
		
		// 댓글을 쓰면 댓글을 쓴 게시물의 state(상태)를 '답변 완료'로 바꿔준다.
		HashMap<String, Object> params = new HashMap<>();
		params.put("id", comment.getPostId());
		params.put("state", "답변 완료");
		int res2 = boardMapper.changeQnaPostState(params);
		
		return (res1 == 1 && res2 == 1) ? 1 : 0;
	}

	@Override
	@Transactional
	public int deleteComment(int id) {
		Comment comment = boardMapper.getComment(id);
		
		// id에 해당하는 댓글이 없으면 0 반환
		if (comment == null) return 0;
		
		int postId = comment.getPostId();
		int res1 = boardMapper.deleteComment(id);
		
		// 댓글을 삭제한 이후, 댓글을 삭제한 게시물에 댓글이 0개라면 state(상태)를 '답변 대기'로 바꿔준다.
		int count = boardMapper.getCommentCount(postId);
		
		int res2 = 1;
		if (count == 0) {			
			HashMap<String, Object> params = new HashMap<>();
			params.put("id", postId);
			params.put("state", "답변 대기");
			res2 = boardMapper.changeQnaPostState(params);
		}
		
		return (res1 == 1 && res2 == 1) ? 1 : 0;
	}
	
	@Override
	@Transactional
	public int putComment(Comment comment) {
		return boardMapper.putComment(comment);
	}


	@Override
	@Transactional
	public Comment getLatestComment(int id) {
		return boardMapper.getLatestComment(id);
	}

	@Override
	public Comment getComment(int id) {
		return boardMapper.getComment(id);
	}
	
	@Override
	public int changeCommentHeart(int id, int heart) {
		HashMap<String, Object> params = new HashMap<>();
		params.put("id", id);
		params.put("heart", heart);
		return boardMapper.changeCommentHeart(params);
	}

	/**
	 * 공지사항 게시판 관련
	 * */
	@Override
	@Transactional
	public ArrayList<Post> getNoticePosts() {
		return boardMapper.getNoticePosts();
	}
	
	@Override
	@Transactional
	public Post getNoticePost(int id) {
		return boardMapper.getNoticePost(id);
	}
	
	@Override
	@Transactional
	public int postNoticePost(Post post) {
		return boardMapper.postNoticePost(post);
	}

	@Override
	@Transactional
	public int putNoticePost(Post post) {
		return boardMapper.putNoticePost(post);
	}

	@Override
	@Transactional
	public int deleteNoticePost(int id) {
		int res = boardMapper.deleteNoticePost(id);
		return res;
	}
	
	@Override
	@Transactional
	public int upNoticePostHit(int id) {
		return boardMapper.upNoticePostHit(id);
	}
}
