package com.jipzip.board.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jipzip.board.model.dto.Comment;
import com.jipzip.board.model.dto.Post;
import com.jipzip.board.model.service.BoardService;

@RestController

@RequestMapping("/boardapi")
public class BoardRestController {

	private BoardService boardService;
	
	public BoardRestController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	/**
	 * QnA 게시판 관련
	 * */
	// QnA 게시판의 모든 게시글 얻어오기
	@GetMapping("/qna/posts")
	public ResponseEntity<?> getQnaPosts() {
		try {
			ArrayList<Post> result = boardService.getQnaPosts();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// QnA 게시판의 id에 해당하는 게시글 얻어오기
	@GetMapping("/qna/posts/{id}")
	public ResponseEntity<?> getQnaPost(@PathVariable String id) {
		try {
			Post result = boardService.getQnaPost(Integer.parseInt(id));
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// QnA 게시글 쓰기
	@PostMapping("/qna/posts")
	public ResponseEntity<?> postQnaPost(@RequestBody Post post) {
		try {
			System.out.println(post);
			int result = boardService.postQnaPost(post);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// QnA 게시판의 id에 해당하는 게시글 수정하기
	@PutMapping("/qna/posts")
	public ResponseEntity<?> putQnaPost(@RequestBody Post post) {
		try {
			int result = boardService.putQnaPost(post);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// QnA 게시판의 id에 해당하는 게시글의 조회수(hit) 수정하기
	@GetMapping("/qna/posts/{id}/hit")
	public ResponseEntity<?> upQnaPostHit(@PathVariable int id) {
		try {
			int result = boardService.upQnaPostHit(id);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// QnA 게시판의 id에 해당하는 게시글 삭제하기
	@DeleteMapping("/qna/posts/{id}")
	public ResponseEntity<?> deleteQnaPost(@PathVariable String id) {
		try {
			int result = boardService.deleteQnaPost(Integer.parseInt(id));
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	/**
	 * 댓글 관련
	 * */
	// postId가 id인 게시글의 가장 최신 댓글 가져오기
	@GetMapping("/posts/{id}/comments")
	public ResponseEntity<?> getLatestComment(@PathVariable String id) {
		try {
			Comment result = boardService.getLatestComment(Integer.parseInt(id));
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 댓글 쓰기
	@PostMapping("/comments")
	public ResponseEntity<?> postComment(@RequestBody Comment comment) {
		try {
			int result = boardService.postComment(comment);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 댓글 삭제하기
	@DeleteMapping("/comments/{id}")
	public ResponseEntity<?> deleteComment(@PathVariable int id) {
		try {
			int result = boardService.deleteComment(id);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 댓글 수정하기
	@PutMapping("/comments")
	public ResponseEntity<?> putComment(@RequestBody Comment comment) {
		try {
			int result = boardService.putComment(comment);
			Comment resultComment = null;
			if (result == 1) {
				resultComment = boardService.getComment(comment.getCommentId());
			}
			return ResponseEntity.ok(resultComment);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// id에 해당하는 댓글의 좋아요 수 변경
	@PutMapping("/comments/{id}/heart")
	public ResponseEntity<?> changeCommentHeart(@PathVariable int id , @RequestBody HashMap<String, String> map) {
		try {
			System.out.println("전송 완료: " + map.get("heart"));
			int result = boardService.changeCommentHeart(id, Integer.parseInt(map.get("heart")));
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	/**
	 * 공지사항 게시판 관련
	 * */
	// 공지사항 게시판의 모든 게시글 얻어오기
		@GetMapping("/notice/posts")
		public ResponseEntity<?> getNoticePosts() {
			try {
				ArrayList<Post> result = boardService.getNoticePosts();
				return ResponseEntity.ok(result);
			} catch (Exception e) {
				return exceptionHandling(e);
			}
		}
	
	// 공지사항 게시판의 id에 해당하는 게시글 얻어오기
		@GetMapping("/notice/posts/{id}")
		public ResponseEntity<?> getNoticePost(@PathVariable String id) {
			try {
				Post result = boardService.getNoticePost(Integer.parseInt(id));
				return ResponseEntity.ok(result);
			} catch (Exception e) {
				return exceptionHandling(e);
			}
		}
	
	// 공지사항 게시글 쓰기
	@PostMapping("/notice/posts")
	public ResponseEntity<?> postNoticePost(@RequestBody Post post) {
		try {
			System.out.println(post);
			int result = boardService.postNoticePost(post);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 공지사항 게시판의 id에 해당하는 게시글 수정하기
	@PutMapping("/notice/posts")
	public ResponseEntity<?> putNoticePost(@RequestBody Post post) {
		try {
			int result = boardService.putNoticePost(post);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 공지사항 게시판의 id에 해당하는 게시글의 조회수(hit) 수정하기
	@GetMapping("/notice/posts/{id}/hit")
	public ResponseEntity<?> upNoticePostHit(@PathVariable int id) {
		try {
			int result = boardService.upNoticePostHit(id);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 공지사항 게시판의 id에 해당하는 게시글 삭제하기
	@DeleteMapping("/notice/posts/{id}")
	public ResponseEntity<?> deleteNoticePost(@PathVariable String id) {
		try {
			int result = boardService.deleteNoticePost(Integer.parseInt(id));
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 에러 핸들링
	private ResponseEntity<?> exceptionHandling(Exception e) {
		return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
	}
}
