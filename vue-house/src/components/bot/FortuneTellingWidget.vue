<script setup>
import { ref } from "vue";
import { fortuneTell } from "@/api/bot.js";

// data
const modal = ref(false);
const isLoading = ref(false);
const calendar = ref("");
const time = ref("");
const calendarType = ref("solar"); // 기본값 양력
const question = ref("");
const answer = ref("");
const refQuestion = ref(null);
const refAnswer = ref(null);

// methods
const submitForm = () => {
  const success = (response) => {
    isLoading.value = false;
    if (response.status === 200) {
      answer.value = response.data;
      refAnswer.value.focus();
    } else {
      alert("답변을 받아올 수 없어요😥");
    }
  };

  const fail = (error) => {
    isLoading.value = false;
    alert("문제가 발생했어요. " + error);
  };

  let prompt = ref(
    `${calendarType.value}으로 태어난 날짜는 ${calendar.value}이고, 태어난 시각은 ${time.value}야. ${question.value}`
  );

  isLoading.value = true;
  fortuneTell(success, fail, prompt.value);
};

const close = () => {
  modal.value = false;
  initForm();
};

const initForm = () => {
  isLoading.value = false;
  calendar.value = "";
  time.value = "";
  calendarType.value = "solar";
  question.value = "";
  answer.value = "";
  refQuestion.value = null;
  refAnswer.value = null;
};

// 포커스 활성화
const focusActive = () => {
  refQuestion.value.focus();
};
</script>

<template>
  <div>
    <div
      id="fortune-teller-widget"
      class="z-3 d-flex justify-content-center align-items-center position-fixed"
      @click="modal = !modal">
      <img
        src="@/assets/fortune-teller-widget.png"
        alt="점성술사"
        class="fortune-teller-img" />
    </div>
    <b-modal
      v-model="modal"
      hide-footer
      hide-header
      :class="{ 'custom-modal': true, 'modal-loading': isLoading }"
      @shown="focusActive">
      <div class="modal-header">
        <h4 class="modal-title">🍀운세 보기🍀</h4>
        <button type="button" class="close" @click="close">
          <span><i class="bi bi-x-lg"></i></span>
        </button>
      </div>
      <div class="modal-body">
        <div class="modal-desc lh-base">
          <span>
            재미로 보는 내 집 마련 타이밍😆 <br />
            생년월일과 태어난 시각을 입력하고 질문을 던져보세요!! <br />
          </span>
        </div>
        <form @submit.prevent="submitForm">
          <div class="form-group">
            <label class="label-color" for="question">질문</label>
            <textarea
              v-model="question"
              required
              class="form-control no-resize"
              rows="3"
              ref="refQuestion"
              placeholder="질문을 입력해보세요"></textarea>
          </div>
          <div class="form-group">
            <label class="label-color" for="answer">답변</label>
            <textarea
              v-model="answer"
              required
              class="form-control no-resize"
              rows="8"
              readonly
              placeholder="점술사가 대답해드릴거에요!"
              ref="refAnswer"></textarea>
          </div>
          <div class="form-group">
            <label class="label-color" for="calendar">생년월일</label>
            <input
              type="date"
              id="calendar"
              v-model="calendar"
              class="form-control"
              required />
          </div>
          <div class="form-group">
            <label class="label-color" for="time">태어난 시각</label>
            <input
              type="time"
              id="time"
              v-model="time"
              class="form-control"
              required />
          </div>
          <div class="form-group">
            <label class="label-color">양력/음력</label>
            <div class="d-flex justify-content-start">
              <div class="custom-control custom-radio custom-control-inline">
                <input
                  type="radio"
                  id="solar"
                  v-model="calendarType"
                  value="solar"
                  class="custom-control-input"
                  checked />
                <label class="custom-control-label" for="solar">양력</label>
              </div>
              <div
                class="custom-control custom-radio custom-control-inline ms-5">
                <input
                  type="radio"
                  id="lunar"
                  v-model="calendarType"
                  value="lunar"
                  class="custom-control-input" />
                <label class="custom-control-label" for="lunar">음력</label>
              </div>
            </div>
          </div>
          <button type="submit" class="btn btn-success w-100">운세 보기</button>
        </form>
        <!-- 답변을 받을 때 까지 로딩하는 이미지를 보여주는 창 -->
        <div
          class="loading-overlay d-flex justify-content-center align-items-center flex-column"
          v-if="isLoading">
          <img src="@/assets/fortune-teller-loading.png" alt="로딩" />
          <span>으흐흠 어디 보자</span>
          <div class="preloader-container">
            <div class="preloader">
              <div class="preloader-dots">
                <div class="dot"></div>
                <div class="dot"></div>
                <div class="dot"></div>
                <div class="dot"></div>
                <div class="dot"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </b-modal>
  </div>
</template>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap");

#fortune-teller-widget {
  bottom: 20px;
  right: 20px;
  width: 70px;
  height: 70px;
  cursor: pointer;
  filter: drop-shadow(1px 6px 8px #707070);
}
.fortune-teller-img {
  width: 100%;
  height: auto;
}
.custom-modal {
  max-width: 500px;
}
.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #dee2e6;
}
.modal-title {
  margin: 0;
}
.modal-desc {
  margin-bottom: 30px;
}
.close {
  background: none;
  border: none;
  font-size: 1.5rem;
}
.form-group {
  margin-bottom: 1rem;
}
.custom-control-label {
  margin-left: 0.5rem;
}
.btn-primary {
  background-color: #007bff;
  border-color: #007bff;
  transition: background-color 0.3s ease;
}
.btn-primary:hover {
  background-color: #0056b3;
  border-color: #0056b3;
}
.label-color {
  color: gray;
}
.no-resize {
  resize: none;
}
.modal-loading {
  background-color: white;
  padding: 20px;
  border-radius: 5px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}
.loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}
.loading-overlay img {
  width: 300px;
}
.loading-overlay span {
  font-family: "Gowun Dodum", sans-serif;
  font-weight: 400;
  font-style: normal;
  font-size: 2em;
  color: white;
}
.preloader-dots {
  height: 50px;
  width: 80px;
}
.preloader-dots .dot {
  display: inline-block;
  background: rgb(255, 255, 255);
  height: 8px;
  width: 8px;
  opacity: 0.3;
  border-radius: 50%;
  animation: moveit 1.8s infinite;
}

.dot:nth-child(2) {
  animation-delay: 0.15s;
}

.dot:nth-child(3) {
  animation-delay: 0.3s;
}

.dot:nth-child(4) {
  animation-delay: 0.45s;
}

.dot:nth-child(5) {
  animation-delay: 0.6s;
}

@keyframes moveit {
  0% {
    transform: translateY(0px);
  }
  35% {
    transform: translateY(0px);
    opacity: 0.3;
  }
  50% {
    transform: translateY(-20px);
    opacity: 0.8;
  }
  70% {
    transform: translateY(3px);
    opacity: 0.8;
  }
  85% {
    transform: translateY(-3px);
  }
}
#fortune-teller-widget img {
  transition: all 0.1s linear;
  width: 80px;
}
#fortune-teller-widget:hover img {
  transform: scale(1.1);
}
</style>
