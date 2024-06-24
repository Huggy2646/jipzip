//package com.jipzip.boardtest;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.server.Cookie;
//
//import com.jipzip.AbstractTest;
//import com.jipzip.board.mapper.BoardMapper;
//import com.jipzip.board.model.dto.Comment;
//import com.jipzip.board.model.dto.Post;
//
//public class BoardMapperTest extends AbstractTest {
//	
//	private static final Logger logger = LoggerFactory.getLogger(BoardMapperTest.class);
//	
//	// 픽스처(fixture): 테스트를 수행하는데 필요한 정보나 객체
//	@Autowired
//	private BoardMapper boardMapper;
//	
//	private Post post1;
//	private Post post2;
//	private Post post3;
//	
//	private Comment comment1;
//	private Comment comment2;
//	private Comment comment3;
//	
//	@Before
//	public void setup() {
//		post1 = new Post("ssafy", "게시글1", "안녕하세요. 게시글 1 내용입니다.");
//		post2 = new Post("ssafy", "게시글2", "안녕하세요. 게시글 2 내용입니다.");
//		post3 = new Post("admin", "게시글3", "안녕하세요. 게시글 3 내용입니다.");
//		
//		comment1 = new Comment("댓글 1 입니다.", "ssafy");
//		comment2 = new Comment("댓글 2 입니다.", "admin");
//		comment3 = new Comment("댓글 3 입니다.", "admin");
//	}
//	
//	@Test
//	public void postDeleteAndPostTest() { // 게시글 삽입, 삭제 테스트
//		// 1. 삭제
//		boardMapper.deletePostAll();
//		
//		assertEquals(0, boardMapper.getCount());
//		
//		// 2. 데이터 삽입
//		boardMapper.postPost(post1);
//		boardMapper.postPost(post2);
//		boardMapper.postPost(post3);
//		
//		assertEquals(3, boardMapper.getCount());
//	}
//	
//	@Test
//	public void getPostsTest() { // 모든 게시글 가져오기 테스트
//		// 1. 삭제
//		boardMapper.deletePostAll();
//		
//		assertEquals(0, boardMapper.getCount());
//		
//		// 2. 데이터 삽입
//		boardMapper.postPost(post1);
//		boardMapper.postPost(post2);
//		boardMapper.postPost(post3);
//		
//		// 3. 게시판 글 목록 가져오기
//		ArrayList<Post> list = boardMapper.getPosts();
//		assertEquals(3, list.size());
//	}
//	
//	@Test
//	public void getPostTest() { // postId에 해당하는 게시글 가져오기 테스트
//		// 1. 삭제
//		boardMapper.deletePostAll();
//		
//		assertEquals(0, boardMapper.getCount());
//		
//		// 2. 데이터 삽입
//		boardMapper.postPost(post1);
//		boardMapper.postPost(post2);
//		boardMapper.postPost(post3);
//		
//		// 3. 게시판 상세 정보 가져오기
//		
//		// 모든 글 가져오기
//		ArrayList<Post> list = boardMapper.getPosts();
//		
//		// 대표적으로 title로 비교
//		assertEquals("게시글1", boardMapper.getPost(list.get(0).getPostId()).getTitle());
//		assertEquals("게시글2", boardMapper.getPost(list.get(1).getPostId()).getTitle());
//		assertEquals("게시글3", boardMapper.getPost(list.get(2).getPostId()).getTitle());
//	}
//	
//	
//	@Test
//	public void putPostTest() { // postId에 해당하는 게시글 수정하기 테스트
//		// 1. 삭제
//		boardMapper.deletePostAll();
//		
//		assertEquals(0, boardMapper.getCount());
//		
//		// 2. 데이터 삽입
//		boardMapper.postPost(post1);
//		boardMapper.postPost(post2);
//		boardMapper.postPost(post3);
//		
//		// 3. 게시글 수정하기
//		
//		// 모든 글 가져오기
//		ArrayList<Post> list = boardMapper.getPosts();
//		
//		post1.setTitle("수정된 게시글1");
//		post1.setPostId(list.get(0).getPostId());
//		boardMapper.putPost(post1);
//		assertEquals("수정된 게시글1", boardMapper.getPost(list.get(0).getPostId()).getTitle());
//		
//		// titie 다시 되돌려 놓기
//		post1.setTitle("게시글1");
//		boardMapper.putPost(post1);
//		assertEquals("게시글1", boardMapper.getPost(list.get(0).getPostId()).getTitle());
//	}
//	
//	@Test
//	public void deletePostTest() { // postId에 해당하는 게시글 삭제 테스트
//		// 1. 삭제
//		boardMapper.deletePostAll();
//		
//		assertEquals(0, boardMapper.getCount());
//		
//		// 2. 데이터 삽입
//		boardMapper.postPost(post1);
//		boardMapper.postPost(post2);
//		boardMapper.postPost(post3);
//		
//		// 3. 게시글 삭제
//		
//		// 모든 글 가져오기
//		ArrayList<Post> list = boardMapper.getPosts();
//		
//		boardMapper.deletePost(list.get(0).getPostId());
//		
//		// 게시글 목록 가져오기
//		Post post = boardMapper.getPost(list.get(0).getPostId());
//		assertEquals(null, post);
//	}
//	
//	@Test
//	public void changePostStateTest() { // postId가 id인 게시글의 state(상태) 변경 테스트
//		// 1. 삭제
//		boardMapper.deletePostAll();
//		
//		assertEquals(0, boardMapper.getCount());
//		
//		// 2. 데이터 삽입
//		boardMapper.postPost(post1);
//		boardMapper.postPost(post2);
//		boardMapper.postPost(post3);
//		
//		// 3. 상태 변경
//		
//		// 모든 글 가져오기
//		ArrayList<Post> list = boardMapper.getPosts();
//		
//		HashMap<String, Object> params = new HashMap<>();
//		params.put("id", list.get(0).getPostId());
//		params.put("state", "답변 완료");
//		boardMapper.changePostState(params);
//		
//		assertEquals("답변 완료", boardMapper.getPost(list.get(0).getPostId()).getState());
//	}
//	
//	@Test
//	public void upPostHitTest() { // postId가 id인 게시글의 조회수 +1
//		// 1. 삭제
//		boardMapper.deletePostAll();
//		
//		assertEquals(0, boardMapper.getCount());
//		
//		// 2. 데이터 삽입
//		boardMapper.postPost(post1);
//		boardMapper.postPost(post2);
//		boardMapper.postPost(post3);
//		
//		// 3. 조회수 1 높이기
//		
//		// 모든 글 가져오기
//		ArrayList<Post> list = boardMapper.getPosts();
//		
//		boardMapper.upPostHit(list.get(0).getPostId());
//		assertEquals(1, boardMapper.getPost(list.get(0).getPostId()).getHit());
//	}
//
//	@Test
//	public void commentDeleteAndPostTest() { // 댓글 삭제, 삽입 테스트
//		// 1. 삭제
//		boardMapper.deleteCommentAll();
//		
//		assertEquals(0, boardMapper.getCommentAllCount());
//		
//		// 2. 데이터 삽입
//		// 모든 글 가져오기
//		ArrayList<Post> list = boardMapper.getPosts();
//		
//		comment1.setPostId(list.get(0).getPostId());
//		comment2.setPostId(list.get(1).getPostId());
//		comment3.setPostId(list.get(2).getPostId());
//		
//		boardMapper.postComment(comment1);
//		boardMapper.postComment(comment2);
//		boardMapper.postComment(comment3);
//		
//		assertEquals(3, boardMapper.getCommentAllCount());
//	}
//	
//	@Test
//	public void getCommentsTest() { // postId가 id인 게시글의 모든 댓글 가져오기 테스트
//		// 1. 삭제
//		boardMapper.deleteCommentAll();
//		
//		assertEquals(0, boardMapper.getCommentAllCount());
//		
//		// 2. 데이터 삽입
//		// 모든 글 가져오기
//		ArrayList<Post> list = boardMapper.getPosts();
//		
//		comment1.setPostId(list.get(0).getPostId());
//		comment2.setPostId(list.get(1).getPostId());
//		comment3.setPostId(list.get(1).getPostId());
//		
//		boardMapper.postComment(comment1);
//		boardMapper.postComment(comment2);
//		boardMapper.postComment(comment3);
//		
//		assertEquals(3, boardMapper.getCommentAllCount());
//		
//		// 3. 댓글 가져오기
//		ArrayList<Comment> comments = boardMapper.getComments(list.get(1).getPostId());
//		
//		assertEquals(2, comments.size());
//	}
//	
//	@Test
//	public void getCommentTest() { // commentId가 id인 댓글 가져오기 테스트
//		// 1. 삭제
//		boardMapper.deleteCommentAll();
//		
//		assertEquals(0, boardMapper.getCommentAllCount());
//		
//		// 2. 데이터 삽입
//		// 모든 글 가져오기
//		ArrayList<Post> list = boardMapper.getPosts();
//		
//		comment1.setPostId(list.get(0).getPostId());
//		comment2.setPostId(list.get(1).getPostId());
//		comment3.setPostId(list.get(2).getPostId());
//		
//		boardMapper.postComment(comment1);
//		boardMapper.postComment(comment2);
//		boardMapper.postComment(comment3);
//		
//		assertEquals(3, boardMapper.getCommentAllCount());
//		
//		// 3. 댓글 가져오기
//		// 모든 댓글 가져오기
//		ArrayList<Comment> comments = boardMapper.getCommentAll();
//		
//		// 대표적으로 content로 비교
//		assertEquals("댓글 1 입니다.", boardMapper.getComment(comments.get(0).getCommentId()).getCommentContent());
//	}
//	
//	@Test
//	public void deleteCommentTest() { // commentId가 id인 댓글 삭제 테스트
//		// 1. 삭제
//		boardMapper.deleteCommentAll();
//		
//		assertEquals(0, boardMapper.getCommentAllCount());
//		
//		// 2. 데이터 삽입
//		// 모든 글 가져오기
//		ArrayList<Post> list = boardMapper.getPosts();
//		
//		comment1.setPostId(list.get(0).getPostId());
//		comment2.setPostId(list.get(1).getPostId());
//		comment3.setPostId(list.get(2).getPostId());
//		
//		boardMapper.postComment(comment1);
//		boardMapper.postComment(comment2);
//		boardMapper.postComment(comment3);
//		
//		assertEquals(3, boardMapper.getCommentAllCount());
//		
//		// 3. 댓글 삭제하기
//		
//		// 모든 댓글 가져오기
//		ArrayList<Comment> comments = boardMapper.getCommentAll();
//		boardMapper.deleteComment(comments.get(0).getCommentId());
//		
//		assertEquals(2, boardMapper.getCommentAllCount());
//	}
//	
//	@Test
//	public void deleteCommentsTest() { // postId가 id인 게시글의 모든 댓글 삭제 테스트
//		// 1. 삭제
//		boardMapper.deleteCommentAll();
//		
//		assertEquals(0, boardMapper.getCommentAllCount());
//		
//		// 2. 데이터 삽입
//		// 모든 글 가져오기
//		ArrayList<Post> list = boardMapper.getPosts();
//		
//		comment1.setPostId(list.get(0).getPostId());
//		comment2.setPostId(list.get(1).getPostId());
//		comment3.setPostId(list.get(1).getPostId());
//		
//		boardMapper.postComment(comment1);
//		boardMapper.postComment(comment2);
//		boardMapper.postComment(comment3);
//		
//		assertEquals(3, boardMapper.getCommentAllCount());
//		
//		// 3. 댓글 삭제하기
//		boardMapper.deleteComments(list.get(1).getPostId());
//		
//		assertEquals(1, boardMapper.getCommentAllCount());
//	}
//	
//	@Test
//	public void putCommentTest() { // 댓글 수정 테스트
//		// 1. 삭제
//		boardMapper.deleteCommentAll();
//		
//		assertEquals(0, boardMapper.getCommentAllCount());
//		
//		// 2. 데이터 삽입
//		// 모든 글 가져오기
//		ArrayList<Post> list = boardMapper.getPosts();
//		
//		comment1.setPostId(list.get(0).getPostId());
//		comment2.setPostId(list.get(1).getPostId());
//		comment3.setPostId(list.get(2).getPostId());
//		
//		boardMapper.postComment(comment1);
//		boardMapper.postComment(comment2);
//		boardMapper.postComment(comment3);
//		
//		assertEquals(3, boardMapper.getCommentAllCount());
//		
//		// 3. 댓글 수정
//		
//		// 모든 댓글 가져오기
//		ArrayList<Comment> comments = boardMapper.getCommentAll();
//		
//		// 대표적으로 content로 비교
//		comment1.setCommentContent("수정된 댓글 1 입니다.");
//		comment1.setCommentId(comments.get(0).getCommentId());
//		boardMapper.putComment(comment1);
//		
//		
//		assertEquals("수정된 댓글 1 입니다.", boardMapper.getComment(comments.get(0).getCommentId()).getCommentContent());
//	}
//	
//	@Test
//	public void getCommentCountTest() { // postId가 id인 게시글의 모든 댓글 갯수 가져오기 테스트
//		// 1. 삭제
//		boardMapper.deleteCommentAll();
//		
//		assertEquals(0, boardMapper.getCommentAllCount());
//		
//		// 2. 데이터 삽입
//		// 모든 글 가져오기
//		ArrayList<Post> list = boardMapper.getPosts();
//		
//		comment1.setPostId(list.get(0).getPostId());
//		comment2.setPostId(list.get(1).getPostId());
//		comment3.setPostId(list.get(1).getPostId());
//		
//		boardMapper.postComment(comment1);
//		boardMapper.postComment(comment2);
//		boardMapper.postComment(comment3);
//		
//		assertEquals(3, boardMapper.getCommentAllCount());
//		
//		// 3. 댓글 갯수 가져오기
//		assertEquals(2, boardMapper.getCommentCount(list.get(1).getPostId()));
//	}
//}
