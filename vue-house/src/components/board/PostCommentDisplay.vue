<script setup>
import { deleteComment, changeCommentHeart, putComment } from "@/api/board.js";
import { ref, computed } from "vue";
import { useRouter } from "vue-router";

// setting
const props = defineProps({
  comment: Object,
});

const router = useRouter();

const emit = defineEmits([
  "deleteCommentFromCommentView",
  "goCommentModify",
  "changeCommentHeartComplete",
]);

// data
const loginUser = ref(sessionStorage.getItem("id"));

const refComment = ref({
  // 좋아요 수를 추적하고 수정하기 위해 props로 받은 comment 대신 refComment로 복사해서 사용
  commentId: props.comment.commentId,
  postId: props.comment.postId,
  commentContent: props.comment.commentContent,
  commenter: props.comment.commenter,
  createdAt: props.comment.createdAt,
  lastModifiedAt: props.comment.lastModifiedAt,
  heart: props.comment.heart,
});

const htmlContent = computed(() => {
  // 댓글 내용의 줄바꿈을 <br>로 처리하기 위함
  const value = refComment.value.commentContent.replace(/(\n|\r\n)/g, "<br>");
  return value.replaceAll(" ", "&nbsp;"); // 띄워쓰기를 $nbsp;로 바꾸기 위함
});

const heartIsClicked = ref(false);

// methods
const doClickHeart = () => {
  if (!heartIsClicked.value) {
    // 좋아요를 활성화 했을 때는 좋아요 수 +1
    const success = (response) => {
      if (response.status === 200 && response.data === 1) {
        refComment.value.heart++;
        emit("changeCommentHeartComplete", refComment.value.heart); // 부모에게 알림
      }
    };

    const fail = (error) => {
      alert("문제가 발생했습니다. ", error);
    };

    changeCommentHeart(
      success,
      fail,
      refComment.value.commentId,
      refComment.value.heart + 1
    );
  } else {
    // 좋아요를 비활성화 했을 때는 좋아요 수 -1
    const success = (response) => {
      if (response.status === 200 && response.data === 1) {
        refComment.value.heart--;
        emit("changeCommentHeartComplete", refComment.value.heart); // 부모에게 알림
      }
    };

    const fail = (error) => {
      alert("문제가 발생했습니다. ", error);
    };

    changeCommentHeart(
      success,
      fail,
      refComment.value.commentId,
      refComment.value.heart - 1
    );
  }

  heartIsClicked.value = !heartIsClicked.value;
};

const doDeleteComment = () => {
  const check = confirm("정말 삭제하시겠습니까?");

  if (check === true) {
    const success = (response) => {
      if (response.status === 200 && response.data === 1) {
        // DB에서 뿐만 아니라 comments 배열에서도 삭제시켜 주어야 하므로 부모에게 알린다.
        emit("deleteCommentFromCommentView", refComment.value.commentId);
        alert("정상적으로 삭제가 완료되었습니다.");
      } else {
        alert("댓글을 삭제하는데 실패했습니다.");
      }
    };

    const fail = (error) => {
      alert("문제가 발생했습니다. ", error);
    };

    deleteComment(success, fail, refComment.value.commentId);
  }
};

// 댓글 수정하는 창으로 이동
const goModifyComment = () => {
  emit("goCommentModify");
};

// life cycle: created
</script>

<template>
  <div class="m-4">
    <div class="hstack">
      <div id="content-box">
        <p v-html="htmlContent"></p>
      </div>
      <div
        class="dropdown ms-auto"
        id="dropdown-box"
        v-if="loginUser === refComment.commenter || loginUser === 'admin'">
        <!-- TODO: role이 관리자인 유저는 가능하도록 설정 -->
        <a
          class="dropdown-toggle"
          type="button"
          data-bs-toggle="dropdown"
          aria-expanded="false"
          title="더보기">
          <i class="bi bi-three-dots-vertical" style="font-size: 15px"></i>
        </a>
        <ul class="dropdown-menu">
          <li>
            <a class="dropdown-item" href="#" @click.prevent="goModifyComment"
              >수정</a
            >
          </li>
          <li>
            <a class="dropdown-item" href="#" @click.prevent="doDeleteComment"
              >삭제</a
            >
          </li>
        </ul>
      </div>
    </div>
    <div id="etc-box" class="hstack gap-3 mt-4">
      <div class="heart-box">
        <label for="checkbox">
          <input
            type="checkbox"
            class="checkbox"
            hidden
            v-model="heartIsClicked" />
          <svg
            t="1689815540548"
            class="icon"
            viewBox="0 0 1024 1024"
            version="1.1"
            xmlns="http://www.w3.org/2000/svg"
            p-id="2271"
            @click="doClickHeart">
            <path
              d="M742.4 101.12A249.6 249.6 0 0 0 512 256a249.6 249.6 0 0 0-230.72-154.88C143.68 101.12 32 238.4 32 376.32c0 301.44 416 546.56 480 546.56s480-245.12 480-546.56c0-137.92-111.68-275.2-249.6-275.2z"
              fill="#231F20"
              p-id="2272"
              class="heart"></path>
          </svg>
          <span></span>
        </label>
        <div class="item">&nbsp;좋아요 {{ refComment.heart }}</div>
      </div>
      <div class="ms-auto item">{{ refComment.commenter }}</div>
      <div class="item">{{ refComment.lastModifiedAt }}</div>
    </div>
  </div>
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
