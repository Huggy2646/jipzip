import { localAxios } from "@/utils/http-common";
const axios = localAxios();

const baseURL = "/boardapi";

// =======================
// ==== QnA 게시판 관련 ===
// =======================
// QnA 게시판의 모든 게시글 얻어오기
export const getQnAPosts = (success, fail) => {
  axios
    .get(baseURL + "/qna/posts")
    .then(success)
    .catch(fail);
};

// QnA 게시판의 id에 해당하는 게시글 얻어오기
export const getQnaPost = (success, fail, id) => {
  axios
    .get(baseURL + `/qna/posts/${id}`)
    .then(success)
    .catch(fail);
};

// QnA 게시판에 게시글 쓰기
export const postQnaPost = (success, fail, post) => {
  axios
    .post(baseURL + "/qna/posts", JSON.stringify(post))
    .then(success)
    .catch(fail);
};

// QnA id에 해당하는 게시글 수정하기
export const putQnaPost = (success, fail, board) => {
  axios
    .put(baseURL + "/qna/posts", JSON.stringify(board))
    .then(success)
    .catch(fail);
};

// QnA 게시판의 id에 해당하는 게시글 삭제하기
export const deleteQnaPost = (success, fail, id) => {
  axios
    .delete(baseURL + `/qna/posts/${id}`)
    .then(success)
    .catch(fail);
};

// QnA 게시판의 id에 해당하는 게시글의 조회수 1 올리기
export const upQnaPostHit = (success, fail, id) => {
  axios
    .get(baseURL + `/qna/posts/${id}/hit`)
    .then(success)
    .catch(fail);
};

// id에 해당하는 게시글의 최신 댓글 가져오기
export const getLatestComment = (success, fail, id) => {
  axios
    .get(baseURL + `/posts/${id}/comments`)
    .then(success)
    .catch(fail);
};

// 게시글에 댓글 작성하기
export const postComment = (success, fail, comment) => {
  axios
    .post(baseURL + "/comments", JSON.stringify(comment))
    .then(success)
    .catch(fail);
};

// commentId가 id인 댓글의 좋아요 수 변경
export const changeCommentHeart = (success, fail, id, heart) => {
  axios
    .put(baseURL + `/comments/${id}/heart`, JSON.stringify({ heart: heart }))
    .then(success)
    .catch(fail);
};

// commentId가 id인 댓글 삭제
export const deleteComment = (success, fail, id) => {
  axios
    .delete(baseURL + `/comments/${id}`)
    .then(success)
    .catch(fail);
};

// 댓글 수정하기
export const putComment = (success, fail, comment) => {
  axios
    .put(baseURL + "/comments", JSON.stringify(comment))
    .then(success)
    .catch(fail);
};

// =======================
// ==== 공지사항 게시판 관련 ===
// =======================
// 공지사항 게시판의 모든 게시글 얻어오기
export const getNoticePosts = (success, fail) => {
  axios
    .get(baseURL + "/notice/posts")
    .then(success)
    .catch(fail);
};

// 공지사항 게시판의 id에 해당하는 게시글 얻어오기
export const getNoticePost = (success, fail, id) => {
  axios
    .get(baseURL + `/notice/posts/${id}`)
    .then(success)
    .catch(fail);
};

// 공지사항 게시판에 게시글 쓰기
export const postNoticePost = (success, fail, post) => {
  axios
    .post(baseURL + "/notice/posts", JSON.stringify(post))
    .then(success)
    .catch(fail);
};

// 공지사항 id에 해당하는 게시글 수정하기
export const putNoticePost = (success, fail, board) => {
  axios
    .put(baseURL + "/notice/posts", JSON.stringify(board))
    .then(success)
    .catch(fail);
};

// 공지사항 게시판의 id에 해당하는 게시글 삭제하기
export const deleteNoticePost = (success, fail, id) => {
  axios
    .delete(baseURL + `/notice/posts/${id}`)
    .then(success)
    .catch(fail);
};

// 공지사항 게시판의 id에 해당하는 게시글의 조회수 1 올리기
export const upNoticePostHit = (success, fail, id) => {
  axios
    .get(baseURL + `/notice/posts/${id}/hit`)
    .then(success)
    .catch(fail);
};
