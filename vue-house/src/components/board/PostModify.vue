<template>
  <PostForm
    type="modify"
    :post="postCopy"
    :boardType="boardType"
    @do-post-modify="doPostModify"
    @go-post="goPost" />
</template>
<script setup>
import PostForm from "@/components/form/PostForm.vue";
import { reactive } from "vue";
import { useRouter } from "vue-router";
import { putQnaPost, putNoticePost } from "@/api/board";

// setting
const router = useRouter();
const emit = defineEmits(["doPostModifyComplete"]);
const props = defineProps({
  boardType: String,
  post: Object,
});

// data
const postCopy = reactive({
  postId: props.post.postId,
  title: props.post.title,
  content: props.post.content,
});

const doPostModify = (modifiedPost) => {
  const success = (response) => {
    if (response.status === 200 && response.data === 1) {
      alert("수정이 완료되었습니다.");
      emit("doPostModifyComplete"); // 부모(PostView.vue)에게 게시글이 수정됐음을 알림
      goPost();
    } else {
      alert("게시글을 수정할 수 없어요😥");
    }
  };
  const fail = (error) => {
    alert("문제가 발생했습니다. ", error);
  };

  postCopy.title = modifiedPost.title;
  postCopy.content = modifiedPost.content;
  if (props.boardType === "qna") {
    putQnaPost(success, fail, postCopy);
  } else if (props.boardType === "notice") {
    putNoticePost(success, fail, postCopy);
  }
};

const goPost = () => {
  if (props.boardType === "qna") {
    router.push({
      name: "qnaPostWithComments",
    });
  } else if (props.boardType === "notice") {
    router.push({
      name: "noticePostWithComments",
    });
  }
};

// life cycle: created
if (props.boardType === "qna") {
  postCopy.state = props.post.state;
}
</script>
<style scoped></style>
