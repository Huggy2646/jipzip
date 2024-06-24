<script setup>
import { ref } from "vue";
import { putComment } from "@/api/board.js";

// setting
const props = defineProps({
  comment: Object,
});

const emit = defineEmits(["goCommentModifyView", "doCommentModifyComplete"]);

// data
const refComment = ref({
  commentId: props.comment.commentId,
  postId: props.comment.postId,
  commentContent: props.comment.commentContent,
  commenter: props.comment.commenter,
  createdAt: props.comment.createdAt,
  lastModifiedAt: props.comment.lastModifiedAt,
  heart: props.comment.heart,
});

// methods
// 댓글 수정 취소
const goCommentModifyView = () => {
  emit("goCommentModifyView");
};

// 댓글 수정
const doCommentModify = () => {
  const success = (response) => {
    if (response.status === 200 && response.data !== "") {
      const data = response.data;
      refComment.value.commentContent = data.commentContent;
      refComment.value.lastModifiedAt = data.lastModifiedAt;
      alert("답변이 정상적으로 수정되었습니다.");
      emit("doCommentModifyComplete", refComment.value);
      goCommentModifyView();
    } else {
      alert("답변을 수정하는데 실패했습니다.");
    }
  };

  const fail = (error) => {
    alert("문제가 발생했습니다. " + error);
  };

  putComment(success, fail, refComment.value);
};
</script>

<template>
  <div class="m-4">
    <div class="hstack">
      <form class="hstack gap-2" id="content-box">
        <div>
          <textarea v-model="refComment.commentContent"></textarea>
        </div>
        <div class="vstack gap-1">
          <button
            type="button"
            class="btn btn-light btn-sm fs-5"
            @click.prevent="doCommentModify">
            <i class="bi bi-check-circle"></i>
          </button>
          <button
            type="button"
            class="btn btn-light btn-sm fs-5"
            @click.prevent="goCommentModifyView">
            <i class="bi bi-x-circle"></i>
          </button>
        </div>
      </form>
    </div>
    <div id="etc-box" class="hstack gap-3 mt-4">
      <div class="ms-auto item">{{ refComment.commenter }}</div>
      <div class="item">{{ refComment.lastModifiedAt }}</div>
    </div>
  </div>
</template>

<style scoped>
a {
  color: black;
  text-decoration: none;
  outline: none;
}

textarea {
  resize: none;
  width: 92vh;
  height: 100px;
  border-radius: 4.5px;
}

.item {
  color: #5c667b;
}

#content-box {
  font-size: 14px;
}

#etc-box {
  font-size: 13px;
}
</style>
