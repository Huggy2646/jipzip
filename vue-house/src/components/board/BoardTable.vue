<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { getQnAPosts, getNoticePosts } from "@/api/board.js";

// setting
const router = useRouter();

const props = defineProps({
  boardType: String,
});

// data
const posts = ref([]);

// methods
const goPost = (postId) => {
  if (props.boardType === "qna") {
    router.push({
      name: "qnaPostWithComments",
      params: {
        postId: postId,
      },
    });
  } else if (props.boardType === "notice") {
    router.push({
      name: "noticePostWithComments",
      params: {
        postId: postId,
      },
    });
  }
};

const goRegist = () => {
  if (props.boardType === "qna") {
    router.push({
      name: "qnaPostRegist",
    });
  } else if (props.boardType === "notice") {
    router.push({
      name: "noticePostRegist",
    });
  }
};

const getBoard = () => {
  if (props.boardType === "qna") {
    const success = (response) => {
      if (response.status == 200) {
        for (let data of response.data) {
          data.createdAt = data.createdAt.split(" ")[0];
          posts.value.push(data);
        }
      } else {
        alert("문제 발생");
      }
    };
    const fail = (error) => {
      alert(error);
    };

    getQnAPosts(success, fail);
  } else if (props.boardType === "notice") {
    const success = (response) => {
      if (response.status == 200) {
        for (let data of response.data) {
          data.createdAt = data.createdAt.split(" ")[0];
          posts.value.push(data);
        }
      } else {
        alert("문제 발생");
      }
    };
    const fail = (error) => {
      alert(error);
    };

    getNoticePosts(success, fail);
  }
};

// life cycle : created
getBoard();
</script>

<template>
  <div>
    <!-- QnA 게시판 이용 규칙 -->
    <div class="d-flex justify-content-center mb-5" v-if="boardType === 'qna'">
      <img
        id="rule-img"
        src="@/assets/qnaboard_rule.jpg"
        alt="게시판 이용규칙" />
    </div>
    <p>
      전체 <span class="fw-bold text-success">{{ posts.length }}</span>
      건
    </p>
    <table class="table mb-4">
      <thead>
        <tr class="table-light table-head-row">
          <th class="align-middle" scope="col">번호</th>
          <th class="align-middle" scope="col">작성자</th>
          <th class="align-middle" scope="col">제목</th>
          <th class="align-middle" scope="col" v-if="boardType === 'qna'">
            상태
          </th>
          <th class="align-middle" scope="col">작성일</th>
          <th class="align-middle" scope="col">조회수</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="post in posts" :key="post.postId" class="table-item-row">
          <td class="align-middle">{{ post.postId }}</td>
          <td class="align-middle">{{ post.userId }}</td>
          <td class="align-middle">
            <a
              class="title-anchor"
              href="#"
              @click.prevent="goPost(post.postId)"
              >{{ post.title }}</a
            >
          </td>
          <td class="align-middle" v-if="boardType === 'qna'">
            <span class="badge text-bg-light p-2">{{ post.state }}</span>
          </td>
          <td class="align-middle">{{ post.createdAt }}</td>
          <td class="align-middle">{{ post.hit }}</td>
        </tr>
      </tbody>
    </table>
    <div class="d-flex justify-content-end">
      <button type="button" class="btn btn-light" @click="goRegist">
        등록
      </button>
    </div>
  </div>
</template>

<style scoped>
table {
  width: 150vh;
}

.table-item-row {
  height: 80px;
}

.table-head-row {
  height: 50px;
}

.title-anchor {
  color: black;
  text-decoration: none;
  outline: none;
}

.title-anchor:hover {
  text-decoration: underline;
}

#rule-img {
  width: 700px;
  height: 500px;
}
</style>
