<script setup>
import { computed, ref } from "vue";
import { useRouter } from "vue-router";
import { deleteQnaPost, deleteNoticePost } from "@/api/board.js";

// setting
const router = useRouter();

const props = defineProps({
  post: Object,
  boardType: String,
});

// data
const loginUser = ref(sessionStorage.getItem("id"));

const htmlContent = computed(() => {
  // 게시글 내용의 줄바꿈을 <br>로 처리하기 위함
  return props.post.content.replace(/(\n|\r\n)/g, "<br>");
});

// methods
const goPostModify = () => {
  if (props.boardType === "qna") {
    router.push({
      name: "qnaPostModify",
      params: { postId: props.post.postId },
    });
  }
  if (props.boardType === "notice") {
    router.push({
      name: "noticePostModify",
      params: { postId: props.post.postId },
    });
  }
};

const delPost = () => {
  const check = confirm("정말 삭제하시겠습니까?");

  if (check === true) {
    const success = (response) => {
      if (response.status === 200) {
        alert("정상적으로 삭제가 완료되었습니다.");
        goBoard();
      } else {
        alert("문제가 발생했습니다.");
      }
    };
    const fail = (error) => {
      alert(error);
    };

    if (props.boardType === "qna") {
      deleteQnaPost(success, fail, props.post.postId);
    } else if (props.boardType === "notice") {
      deleteNoticePost(success, fail, props.post.postId);
    }
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
</script>

<template>
  <div id="post-box" class="border rounded">
    <div class="pt-3 ps-3 pe-3 pb-1">
      <h2 class="fw-bold pb-2 pt-2">{{ post.title }}</h2>
      <div
        id="subtitle-box"
        class="hstack gap-1 text-body-tertiary d-flex align-items-center">
        <div>{{ post.userId }}</div>
        <div class="ms-1 me-1 vr"></div>
        <div>조회수 {{ post.hit }}</div>
        <div class="ms-1 me-1 vr"></div>
        <div>등록일 {{ post.createdAt }}</div>
        <div class="ms-1 me-1 vr"></div>
        <div>수정일 {{ post.lastModifiedAt }}</div>
        <div
          class="dropdown ms-auto"
          id="dropdown-box"
          v-if="loginUser === post.userId || loginUser === 'admin'">
          <!-- TODO: role이 관리자인 유저는 가능하도록 설정 -->
          <a
            class="dropdown-toggle my-color"
            type="button"
            data-bs-toggle="dropdown"
            aria-expanded="false"
            title="더보기">
            <i class="bi bi-three-dots-vertical" style="font-size: 18px"></i>
          </a>
          <ul class="dropdown-menu">
            <li>
              <a class="dropdown-item" href="#" @click.prevent="goPostModify"
                >수정</a
              >
            </li>
            <li>
              <a class="dropdown-item" href="#" @click.prevent="delPost"
                >삭제</a
              >
            </li>
          </ul>
        </div>
      </div>
      <hr />
    </div>
    <div id="post-content-box">
      <p v-html="htmlContent"></p>
    </div>
  </div>
</template>

<style scoped>
hr {
  margin-left: auto;
  margin-right: auto;
  border-color: #5c667b;
}

#post-content-box {
  margin: 5%;
  font-size: 16px;
}

#subtitle-box {
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

.my-color {
  color: #5c667b;
}
</style>
