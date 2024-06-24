<script setup>
import { reactive } from "vue";
import { useRouter } from "vue-router";

// setting
const props = defineProps({
  type: String,
  post: Object,
  boardType: String,
});

const emit = defineEmits(["doRegist", "doPostModify", "goBoard", "goPost"]);

// data
const postCopy = reactive({
  title: props.post.title,
  content: props.post.content,
});

// methods
const doRegist = () => {
  emit("doRegist", postCopy);
};

const doPostModify = () => {
  const check = confirm("정말 수정하시겠습니까?");
  if (check === true) {
    emit("doPostModify", postCopy);
  }
};

const goBoard = () => {
  emit("goBoard");
};

const goPost = () => {
  emit("goPost");
};
</script>

<template>
  <div class="board">
    <div class="form mb-4">
      <b-row class="title mb-5">
        <b-col>
          <h3><b-label for="title">제목</b-label></h3>
          <b-form-input id="title" v-model="postCopy.title" />
        </b-col>
      </b-row>

      <b-row class="content">
        <b-col>
          <h3><b-label for="content">내용</b-label></h3>
          <b-form-textarea id="content" v-model="postCopy.content" rows="12" />
        </b-col>
      </b-row>
    </div>
    <div style="display: flex; justify-content: center">
      <button
        class="btn btn-light"
        style="display: inline-block; margin: 20px 10px"
        @click.prevent="doRegist"
        v-if="type === 'create'">
        등록
      </button>
      <button
        class="btn btn-light"
        style="display: inline-block; margin: 20px 10px"
        @click.prevent="doPostModify"
        v-if="type === 'modify'">
        수정
      </button>

      <button
        class="btn btn-light"
        style="display: inline-block; margin: 20px 10px"
        @click="goBoard"
        v-if="type === 'create'">
        목록
      </button>
      <button
        class="btn btn-light"
        style="display: inline-block; margin: 20px 10px"
        @click="goPost"
        v-if="type === 'modify'">
        취소
      </button>
    </div>
  </div>
</template>

<style scoped>
.board {
  width: 100vh;
}
</style>
