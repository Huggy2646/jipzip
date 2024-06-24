<template>
  <div id="view-box" class="d-flex flex-column justify-content-center">
    <!-- 버튼 창 -->
    <div class="mb-4">
      <button class="mybtn" @click="goBoard">
        <i class="bi bi-list-task"></i> 글 목록
      </button>
    </div>
    <!-- 게시글 창 -->
    <Post :post="post" :boardType="boardType" />
    <!-- 댓글 창 -->
    <div class="mt-4" v-if="boardType === 'qna'">
      <!-- 댓글 작성 창 -->
      <div id="comments-input-box" class="card mt-5 mb-5">
        <ul class="list-group list-group-flush">
          <li class="list-group-item">
            <form>
              <textarea
                class="form-control"
                placeholder="회원만 등록 가능합니다."
                v-model="newCommentContent"
                v-bind:readonly="!loginUser"></textarea>
            </form>
          </li>
        </ul>
        <div class="card-footer d-flex justify-content-end">
          <button
            type="submit"
            class="btn mybtn"
            @submit.prevent
            @click="doCreateComment">
            <i class="bi bi-chat-text"></i>
            답변하기
          </button>
        </div>
      </div>
      <!-- 댓글 나타나는 창 -->
      <div id="comments-view-box">
        <div>
          <p class="fw-bold">답변 ({{ post.comments.length }})</p>
        </div>
        <template
          v-for="(comment, idx) in post.comments"
          :key="comment.commentId">
          <hr v-if="idx === 0" />
          <PostComment
            :comment="comment"
            @delete-comment-from-comment-view="
              deleteCommentFromCommentViewCallback
            " />
          <hr />
        </template>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from "vue";
import { useRouter } from "vue-router";
import { postComment, getLatestComment } from "@/api/board";
import PostComment from "@/components/board/PostComment.vue";
import Post from "@/components/board/Post.vue";

// setting
const router = useRouter();
const props = defineProps({
  boardType: String,
  post: Object,
});
const emit = defineEmits(["deleteCommentFromCommentView"]);

// data
const loginUser = ref(sessionStorage.getItem("id"));

const postCopy = reactive({
  postId: props.post.postId,
  userId: props.post.userId,
  title: props.post.title,
  content: props.post.content,
  createdAt: props.post.createdAt,
  lastModifiedAt: props.post.lastModifiedAt,
  hit: props.post.hit,
});

let newCommentContent = ref("");

let newComment = {
  commentId: 0,
  postId: 0,
  commentContent: "",
  commenter: loginUser.value, // TODO: 관리자만 승인
  createdAt: "",
  lastModifiedAt: "",
  heart: 0,
  state: "",
  comments: [],
};

// methods
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

const deleteCommentFromCommentViewCallback = (id) => {
  emit("deleteCommentFromCommentView", id);
};

// 새로 작성한 댓글을 화면에 보이게 함
const viewComment = async (id) => {
  return new Promise((resolve, reject) => {
    const success = (response) => {
      if (response.status === 200) {
        newComment = response.data;
        /**
         * 답변 배열에서 최신 댓글이 가장 앞에 오게 하려면 두 가지 방법이 있음.
         * 1. post.comments 배열의 복사본을 만들어 reverse()시킨다
         * 2. unshift를 사용한다
         * => unshift는 모든 요소를 움직이게 해서 O(N)의 시간복잡도를 가짐. 댓글의 수가 적을 것으로 예상되어 그냥 unshift를 사용해 최신 댓글을 배열의 제일 앞으로 오게 함.
         */
        postCopy.comments.unshift(newComment);
        resolve(); // 성공 시 Promise를 완료시킴
      } else {
        reject("Unexpected status code: " + response.status);
      }
    };

    const fail = (error) => {
      alert("문제가 발생했습니다.", error);
      reject(error); // 실패 시 Promise를 거절함
    };

    getLatestComment(success, fail, id);
  });
};

// 댓글 유효성 검사
const isValidate = () => {
  if (
    newCommentContent.value === null ||
    newCommentContent.value.trim() === ""
  ) {
    return false;
  } else {
    return true;
  }
};

// 댓글 작성
const doCreateComment = () => {
  const success = async (response) => {
    if (response.status === 200 && response.data === 1) {
      alert("답변이 등록되었습니다.");
      await viewComment(postCopy.postId);
      newCommentContent.value = "";
    }
  };

  const fail = (error) => {
    alert("에러 발생: " + error);
    newCommentContent.value = "";
  };

  // 댓글 유효성 검사
  const check = isValidate();

  if (check === true) {
    newComment.postId = postCopy.postId;
    newComment.commentContent = newCommentContent.value.trim();
    postComment(success, fail, newComment);
  } else {
    alert("댓글을 작성해주세요.");
  }
};

// life cycle: created
if (props.boardType === "qna") {
  postCopy.state = props.post.state;
  postCopy.comments = props.post.comments;
}
</script>

<style scoped>
hr {
  margin-left: auto;
  margin-right: auto;
  border-color: #5c667b;
}

textarea {
  resize: none;
  height: 140px;
  font-size: 14px;
  border: none;
}

textarea:focus {
  box-shadow: none;
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
#view-box {
  width: 150vh;
}
.dropdown-item:active {
  background-color: rgb(242, 242, 242);
  color: black;
}

.dropdown-item:hover {
  background-color: rgb(242, 242, 242);
  color: black;
}

#comments-view-box {
  margin-top: 50px;
}

#comments-input-box {
  width: 100%;
}

.mybtn {
  background-color: #fff;
  border: 1px solid #b6b8bd;
  border-radius: 5px;
  padding: 4px;
}

.mybtn:hover {
  background-color: rgb(247, 247, 247);
}

.my-color {
  color: #5c667b;
}
</style>
