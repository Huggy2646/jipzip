<script setup>
import { useRouter } from "vue-router";
import { ref, reactive, computed } from "vue";
import { postLogin } from "@/api/user.js";
import { findpw } from "@/api/mail.js";
// setting
const router = useRouter();

// data
const loginUser = reactive({
  id: "",
  pw: "",
});
const findId = ref({ id: "" });
//
const emit = defineEmits(["login"]);
const refLoginUserId = ref(null);

// 앞,뒤 공백 제거
const trimLoginUser = reactive({
  id: computed(() => {
    return loginUser.id.trim();
  }),
  pw: computed(() => {
    return loginUser.pw.trim();
  }),
});

const modalRef = ref(null);

//로그인 화면? 비밀번호 찾기 화면? 인지 확인 해주는 반응형 객체
const loginModalMode = ref("login");

const loginAlert = reactive({
  isVisible: false,
  msg: "",
});

// methods
// 로그인 시, 아이디와 비밀번호 유효성 검사
const isValidateLogin = () => {
  // 아이디 유효성 검사
  if (trimLoginUser.id == "" || trimLoginUser.id == null) {
    loginAlert.isVisible = true;
    loginAlert.msg = "아이디를 입력해주세요😃";
    return false;
  }
  // 비밀번호 유효성 검사
  else if (trimLoginUser.pw == "" || trimLoginUser.pw == null) {
    loginAlert.isVisible = true;
    loginAlert.msg = "비밀번호를 입력해주세요😃";
    return false;
  }

  return true;
};

// 로그인
const doLogin = () => {
  const isValidate = isValidateLogin();

  if (isValidate) {
    const success = (response) => {
      if (response.status === 200 && response.data !== "") {
        initForm();
        modalRef.value.hide();
        emit("login", { id: response.data.id });
      } else {
        loginAlert.isVisible = true;
        loginAlert.msg =
          "아이디 또는 비밀번호를 잘못 입력했습니다. <br> 입력하신 내용을 다시 확인해주세요.";
      }
    };

    const fail = (error) => {
      alert("문제가 발생했습니다. ", error);
    };

    postLogin(success, fail, trimLoginUser);
  }
};

const goFindPassword = () => {
  loginModalMode.value = "findPw";
};

const dofindPw = () => {
  const success = (response) => {
    console.log(response.data);
    if (response.data == "") alert("뭔가 잘못됨");
    if (response.data != "") {
      alert("등록된 메일로 임시 비밀번호를 전송하였습니다.");
      loginModalMode.value = "login";
    }
  };
  const fail = (error) => {
    console.log(error);
  };
  findpw(success, fail, findId.value);
};

const initForm = () => {
  loginUser.id = "";
  loginUser.pw = "";
  loginAlert.isVisible = false;
  loginAlert.msg = "";
  loginModalMode.value = "login";
};

// 포커스 활성화
const focusActive = () => {
  refLoginUserId.value.focus();
};
</script>

<template>
  <!-- Login modal-->
  <b-modal
    id="loginmodal"
    :title="loginModalMode === 'login' ? '로그인' : '비밀번호 찾기'"
    ref="modalRef"
    @close="initForm"
    @shown="focusActive"
    @hidden="initForm">
    <form @submit.prevent="loginModalMode === 'login' ? doLogin() : dofindPw()">
      <div v-if="loginModalMode === 'login'">
        <!-- Loginid -->
        <div class="mb-3">
          <label class="form-label">아이디 입력</label>
          <input
            type="text"
            class="form-control"
            v-model="loginUser.id"
            placeholder="아이디"
            ref="refLoginUserId" />
        </div>
        <!-- LoginPassword -->
        <div class="mb-3">
          <label class="form-label">비밀번호 입력</label>
          <input
            type="password"
            class="form-control"
            v-model="loginUser.pw"
            placeholder="비밀번호" />
        </div>
        <!-- 로그인 실패 여부 띄우기 -->
        <div class="alert-box">
          <BAlert :model-value="loginAlert.isVisible" variant="danger">
            <span v-html="loginAlert.msg"></span>
          </BAlert>
        </div>
        <div class="d-flex justify-content-between align-items-center">
          <button type="submit" class="btn btn-outline-success">로그인</button>
          <a
            class="fs-6 text-body-secondary"
            id="findPasswordLink"
            href="#"
            @click.prevent="goFindPassword"
            >🤔 비밀번호를 잊어버리셨나요?</a
          >
        </div>
      </div>

      <div v-else>
        <!-- 비밀번호 찾기 관련 내용 -->
        <!-- 예시입니다. 실제 내용은 프로젝트 요구사항에 맞게 조정해주세요. -->
        <div class="mb-3">
          <label class="form-label">아이디 입력</label>
          <input
            type="text"
            class="form-control"
            placeholder="id"
            v-model="findId.id" />
        </div>
        <div class="d-flex justify-content-between align-items-center">
          <button
            type="button"
            class="btn btn-outline-success"
            @click.prevent="dofindPw">
            비밀번호 찾기
          </button>
          <button
            type="button"
            class="btn btn-outline"
            @click="loginModalMode = 'login'">
            돌아가기
          </button>
        </div>
      </div>
    </form>
  </b-modal>
</template>

<style scoped>
.alert-box {
  font-size: 13px;
}
</style>
