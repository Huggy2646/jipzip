<script setup>
import { ref } from "vue";

import PostCommentModify from "@/components/board/PostCommentModify.vue";
import PostCommentDisplay from "@/components/board/PostCommentDisplay.vue";

// setting
const props = defineProps({
  comment: Object,
});

const emit = defineEmits(["deleteCommentFromCommentView"]);

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

const goCommentModify = ref(false);

// methods
const deleteCommentFromCommentViewCallback = (id) => {
  emit("deleteCommentFromCommentView", id);
};

const goCommentModifyCallback = () => {
  goCommentModify.value = true;
};

const goCommentModifyView = () => {
  goCommentModify.value = false;
};

const doCommentModifyCompleteCallback = (comment) => {
  refComment.value.commentContent = comment.commentContent;
  refComment.value.lastModifiedAt = comment.lastModifiedAt;
  goCommentModifyView();
};

const changeCommentHeartCompleteCallback = (heart) => {
  refComment.value.heart = heart;
};
</script>

<template>
  <PostCommentDisplay
    :comment="refComment"
    @delete-comment-from-comment-view="deleteCommentFromCommentViewCallback"
    @go-comment-modify="goCommentModifyCallback"
    @change-comment-heart-complete="changeCommentHeartCompleteCallback"
    v-if="goCommentModify === false" />
  <PostCommentModify
    :comment="refComment"
    @go-comment-modify-view="goCommentModifyView"
    @do-comment-modify-complete="doCommentModifyCompleteCallback"
    v-else />
</template>

<style scoped>
/* -- 좋아요 버튼 start --- */
.heart-box {
  /*100% 창 높이*/
  height: 20px;
  /*배치 center*/
  display: flex;
  justify-content: center;
  align-items: center;
  /*사용자정의: var 함수로 핸들링*/
  --c: #ff6b81;
}

svg {
  /* width: 200px; */
  width: 20px;
  /*상대포지션*/
  position: relative;
  /*z-index: 10;*/
}

.heart {
  /*색 보충*/
  fill: #eee;

  /*stroke속성은 선, 문자, 원소등의 테두리에대해서 디자인 가능*/
  stroke: var(--c);
  /*선의 넓이*/
  stroke-width: 40px;
  /*선을 점선으로 설정, 점선의 길이 설정*/
  stroke-dasharray: 3000;
  /*displacement of line*/
  stroke-dashoffset: 3000;
  /*끝점은 둥글게*/
  stroke-linecap: round;
}

.checkbox:checked ~ span {
  display: block;
  width: 17px;
  height: 20px;
  background-color: transparent;
  border-radius: 100%;
  /*절대적 포지션: center*/
  /* position: absolute; */
  /* position: relative; */
  position: absolute;
  /* top: 50%; */
  /* left: 40%; */
  transform: translate(-50%, -50%) scale(0);
  /*각 방향의 쉐도우 설정*/
  /*var함수로 쉐도우 컬러 설정*/
  box-shadow: 0 -160px 0 var(--c), 0 160px 0 var(--c), -160px 0 0 var(--c),
    160px 0 0 var(--c), -120px -120px 0 var(--c), 120px -120px 0 var(--c),
    120px 120px 0 var(--c), -120px 120px 0 var(--c);
}

/*checkbox를 클릭할 경우 애니메이션 실행*/
.checkbox:checked + svg .heart {
  /*애니메이션실행: 애니메이션이름, 실행시간, 선형 마지막 정지한 프레임*/
  animation: drawHeart 0.3s linear forwards;
}

.checkbox:checked ~ span {
  /*애니메이션실행: 애니메이션이름, 실행시간, 선형 마지막 정지한 프레임*/
  animation: blink 0.4s ease-in-out forwards;
  /*애니메이션 딜레이*/
  animation-delay: 0.3s;
}

.checkbox:checked + svg {
  /*애니메이션실행: 애니메이션이름, 실행시간, 선형 마지막 정지한 프레임*/
  animation: beat 0.45s linear forwards;
}

label {
  /*마우스 heart로 이동시 마우스 커서변동*/
  cursor: pointer;
}

/*애니메이션 효과 설정*/
/*heart 애니메이션*/
@keyframes drawHeart {
  0% {
    stroke-dashoffset: 2600;
  }
  80% {
    fill: #eee;
    stroke-dashoffset: 0;
  }
  100% {
    fill: var(--c);
    stroke-dashoffset: 0;
  }
}
/*점 애니메이션*/
@keyframes blink {
  0% {
    /* transform: translate(-50%, -50%) scale(0.5); */
    transform: translate(-50%, -50%) scale(0.2);
    opacity: 0.6;
  }
  80% {
    /* transform: translate(-50%, -50%) scale(1); */
    transform: translate(-50%, -50%) scale(0.4);
    opacity: 0;
  }
  100% {
    /* transform: translate(-50%, -50%) scale(1.1); */
    transform: translate(-50%, -50%) scale(0.5);
    opacity: 0;
  }
}

/*두근두근 애니메이션*/
@keyframes beat {
  0% {
    transform: scale(1);
  }
  70% {
    transform: scale(1);
  }
  80% {
    transform: scale(1.2);
  }
  100% {
    transform: scale(1);
  }
}

/* ---- 좋아요 버튼 end ----- */

a {
  color: black;
  text-decoration: none;
  outline: none;
}

.item {
  color: #5c667b;
}

#etc-box {
  font-size: 13px;
}

#content-box {
  font-size: 14px;
}

#dropdown-box {
  font-size: 0px;
}

#dropdown-box:hover {
  background-color: rgb(247, 247, 247);
}

.dropdown-item:active {
  background-color: rgb(242, 242, 242);
  color: black;
}

.dropdown-item:hover {
  background-color: rgb(242, 242, 242);
  color: black;
}
</style>
