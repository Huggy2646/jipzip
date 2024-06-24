<template>
  <PostForm
    type="create"
    :post="post"
    :boardType="boardType"
    @do-regist="doRegist"
    @go-board="goBoard" />
</template>

<script setup>
import { ref } from "vue";
import { reactive } from "vue";
import { useRouter } from "vue-router";
import { postQnaPost, postNoticePost } from "@/api/board";
import PostForm from "@/components/form/PostForm.vue";

// setting
const router = useRouter();

const props = defineProps({
  boardType: String,
});

// data
const loginuser = ref(sessionStorage.getItem("id"));

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
const doRegist = (post) => {
  post.userId = loginuser.value;
  const success = (response) => {
    if (response.status === 200 && response.data === 1) {
      alert("정상적으로 글이 등록되었습니다.");
      goBoard();
    } else {
      alert("글을 등록하는데 실패했어요😥");
    }
  };

  const fail = (error) => {
    alert("문제가 발생했습니다.", error);
  };

  if (props.boardType === "qna") {
    postQnaPost(success, fail, post);
  } else if (props.boardType === "notice") {
    postNoticePost(success, fail, post);
  }
};

const goBoard = () => {
  if (props.boardType === "qna") {
    router.push({
      name: "qnaTable",
    });
  } else if (props.boardType === "notice") {
    router.push({
      name: "noticeTable",
    });
  }
};

// life cycle: created
if (props.boardType === "qna") {
  post.state = "";
}
</script>
