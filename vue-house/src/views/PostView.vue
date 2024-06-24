<script setup>
import { reactive } from "vue";
import { useRoute } from "vue-router";
import { getQnaPost, getNoticePost } from "@/api/board";

// setting
const props = defineProps({
  boardType: String,
});
const route = useRoute();

// data
const post = reactive({
  postId: 0,
  userId: "",
  title: "",
  content: "",
  createdAt: "",
  lastModifiedAt: "",
  hit: 0,
});

// methods
// 게시글 정보를 가져옴
const getPostDetail = (id) => {
  const success = (response) => {
    if (response.status == 200) {
      const data = response.data;
      post.postId = data.postId;
      post.userId = data.userId;
      post.title = data.title;
      post.content = data.content;
      post.createdAt = data.createdAt;
      post.lastModifiedAt = data.lastModifiedAt;
      post.hit = data.hit;
      if (props.boardType === "qna") {
        post.state = data.state;
        post.comments = data.comments;
      }
    } else {
      alert("게시글 정보를 받아올 수 없어요😥");
    }
  };
  const fail = (error) => {
    alert("문제가 발생했습니다. " + error);
  };

  if (props.boardType === "qna") {
    getQnaPost(success, fail, id);
  } else if (props.boardType === "notice") {
    getNoticePost(success, fail, id);
  }
};

const doPostModifyComplete = () => {
  getPostDetail(route.params.postId);
};

// 댓글 삭제
const deleteCommentFromCommentViewCallback = (id) => {
  post.comments = post.comments.filter((value) => {
    if (value.commentId !== id) return true;
  });
};

// life cycle: created
getPostDetail(route.params.postId);
</script>

<template>
  <div>
    <RouterView
      :boardType="boardType"
      :post="post"
      v-if="post.postId !== 0"
      @do-post-modify-complete="doPostModifyComplete"
      @delete-comment-from-comment-view="
        deleteCommentFromCommentViewCallback
      " />
  </div>
</template>

<style scoped></style>
