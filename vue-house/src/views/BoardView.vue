<script setup>
import { ref, watch } from "vue";
import { useRouter } from "vue-router";

// data
const selected = ref("qna");

// setting
const router = useRouter();

// methods
const goMain = () => {
  router.push({ name: "main" });
};

watch(selected, (newVal) => {
  if (newVal === "qna") {
    router.push({
      name: "qnaTable",
    });
  } else if (newVal === "notice") {
    router.push({
      name: "noticeTable",
    });
  }
});
</script>

<template>
  <div>
    <div class="border-bottom hstack mt-2">
      <div>
        <button class="btn btn-light fs-5" @click="goMain">
          <i class="bi bi-house-door"></i>
        </button>
      </div>
      <div>
        <select class="form-select select-box" v-model="selected">
          <option selected value="qna">QnA 게시판</option>
          <option value="notice">공지사항</option>
        </select>
      </div>
    </div>
    <div class="row">
      <div class="col-1"></div>
      <div class="col-10">
        <RouterView />
      </div>
      <div class="col-1"></div>
    </div>
  </div>
</template>

<style scoped>
.select-box {
  height: 44px;
}

#board-title-box {
  height: 100px;
}
</style>
