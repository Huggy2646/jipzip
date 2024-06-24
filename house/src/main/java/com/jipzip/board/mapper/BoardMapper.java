package com.jipzip.board.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.jipzip.board.model.dto.Comment;
import com.jipzip.board.model.dto.Post;

@Mapper
public interface BoardMapper {

	// QnA 게시판의 게시글 관련
	public ArrayList<Post> getQnaPosts(); // QnA 게시판의 모든 게시글 가져오기
	public Post getQnaPost(int id); // QnA 게시판의 postId가 id인 게시글 가져오기
	public int postQnaPost(Post post); // QnA 게시판의 게시글 작성
	public int putQnaPost(Post post); // 게시글 수정
	public int deleteQnaPost(int id); // QnA 게시판의 postId가 id인 게시글 삭제
	public int changeQnaPostState(HashMap<String, Object> params); // postId가 id인 게시글의 state(상태)를 변경(params - id: postId, state: {"답변 대기", "답변 완료"})
	public int upQnaPostHit(int id); // QnA 게시판의 postId가 id인 게시글의 조회수 +1

	// 댓글 관련
	public ArrayList<Comment> getComments(int id); // postId가 id인 게시글의 모든 댓글 가져오기
	public Comment getComment(int id); // commentId가 id인 댓글 가져오기
	public Comment getLatestComment(int id); // postId가 id인 댓글들 중 가장 최근 댓글 가져오기
	public int deleteComment(int id); // commentId가 id인 댓글 삭제
	public int deleteComments(int id); // postId가 id인 게시글의 모든 댓글 삭제
	public int postComment(Comment comment); // 댓글 작성
	public int putComment(Comment comment); // 댓글 수정
	public int getCommentCount(int id); // postId가 id인 게시글의 모든 댓글 갯수 가져오기
	public int changeCommentHeart(HashMap<String, Object> params); // commentId가 id인 댓글의 좋아요 수를 heart로 변경
	
	// 공지사항 게시판 관련
	public ArrayList<Post> getNoticePosts(); // 공지사항 게시판의 모든 게시글 가져오기
	public Post getNoticePost(int id); // 공지사항 게시판의 postId가 id인 게시글 가져오기
	public int postNoticePost(Post post); // 공지사항 게시판의 게시글 작성
	public int putNoticePost(Post post); // 공지사항 게시판의 게시글 수정
	public int deleteNoticePost(int id); // 공지사항 게시판의 postId가 id인 게시글 삭제
	public int upNoticePostHit(int id); // QnA 게시판의 postId가 id인 게시글의 조회수 +1

	// 테스트를 위함
	public ArrayList<Comment> getCommentAll(); // 모든 댓글 가져오기
	public int deletePostAll();
	public int getCount();
	public int deleteCommentAll();
	public int getCommentAllCount();
}
