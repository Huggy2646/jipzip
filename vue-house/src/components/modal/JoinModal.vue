<script setup>
import { ref, reactive, computed } from "vue";
import { getUser, postUser } from "@/api/user.js";

// data
const joinUserId = ref("");
const joinUser = reactive({
  user: {
    id: joinUserId.value,
    pw: "",
  },
  userInfo: {
    id: joinUserId.value,
    role: "일반회원",
    name: "",
    adress: "",
    email: "",
    mobile_number: "",
  },
});

// 회원가입 데이터의 앞, 뒤 공백을 제거한 데이터
const trimJoinUser = reactive({
  user: {
    id: computed(() => {
      return joinUserId.value.trim();
    }),
    pw: computed(() => {
      return joinUser.user.pw.trim();
    }),
  },
  userInfo: {
    id: computed(() => {
      return joinUserId.value.trim();
    }),
    role: joinUser.userInfo.role,
    name: computed(() => {
      return joinUser.userInfo.name.trim();
    }),
    adress: computed(() => {
      return joinUser.userInfo.adress.trim();
    }),
    email: computed(() => {
      return joinUser.userInfo.email.trim();
    }),
    mobile_number: computed(() => {
      return joinUser.userInfo.mobile_number.trim();
    }),
  },
});

// 회원가입 시 경고창
const joinAlert = reactive({
  isVisible: false,
  msg: "",
});

// 유효한 아이디인지 검사하는 경고창
const idValidateAlert = reactive({
  isVisible: false,
  msg: "",
  hasError: false,
  isValiate: false,
});

const modalRef = ref(null);
const refJoinUserId = ref(null);
// methods
// 회원가입 시, 유효성 검사
const isValidateJoin = () => {
  // 아이디 유효성 검사
  if (trimJoinUser.user.id == "" || trimJoinUser.user.id == null) {
    joinAlert.isVisible = true;
    joinAlert.msg = "아이디를 입력해주세요😃";
    return false;
  }

  // 비밀번호 유효성 검사
  if (trimJoinUser.user.pw == "" || trimJoinUser.user.pw == null) {
    joinAlert.isVisible = true;
    joinAlert.msg = "비밀번호를 입력해주세요😃";
    return false;
  }

  // 이메일 유효성 검사
  if (
    trimJoinUser.userInfo.email == "" ||
    trimJoinUser.userInfo.email == null
  ) {
    joinAlert.isVisible = true;
    joinAlert.msg = "이메일을 입력해주세요😃";
    return false;
  }

  // 이름 유효성 검사
  if (trimJoinUser.userInfo.name == "" || trimJoinUser.userInfo.name == null) {
    joinAlert.isVisible = true;
    joinAlert.msg = "이름을 입력해주세요😃";
    return false;
  }

  // 주소 유효성 검사
  if (
    trimJoinUser.userInfo.adress == "" ||
    trimJoinUser.userInfo.adress == null
  ) {
    joinAlert.isVisible = true;
    joinAlert.msg = "주소를 입력해주세요😃";
    return false;
  }

  // 휴대폰 번호 유효성 검사
  if (trimJoinUser.userInfo.mobile_number.length !== 13) {
    joinAlert.isVisible = true;
    joinAlert.msg = "휴대폰 번호를 다시 확인해주세요😃";
    return false;
  }

  joinAlert.isVisible = false;
  joinAlert.msg = "";
  return true;
};

// 회원가입 진행
const doJoin = () => {
  const isValidateJoinVal = isValidateJoin();

  if (isValidateJoinVal && idValidateAlert.isValiate) {
    const success = (response) => {
      if (response.status === 200 && response.data === 1) {
        alert("회원이 되신 것을 환영합니다!❤❤❤");
        initForm();
        modalRef.value.hide();
      } else {
        alert("회원 등록에 실패했습니다.");
      }
    };
    const fail = (error) => {
      alert("문제가 발생했습니다. ", error);
    };

    postUser(success, fail, trimJoinUser);
  }
};

// 아이디 중복 검사
const isValidateId = () => {
  const success = (response) => {
    if (response.status === 200) {
      const data = response.data;
      if (data !== "") {
        // 이미 같은 아이디로 가입한 사람이 있으면(중복이면)
        idValidateAlert.isVisible = true;
        idValidateAlert.isValiate = false;
        idValidateAlert.hasError = true;
        idValidateAlert.msg =
          "사용할 수 없는 아이디입니다. <br> 다른 아이디를 입력해 주세요.";
      } else {
        idValidateAlert.isVisible = true;
        idValidateAlert.isValiate = true;
        idValidateAlert.hasError = false;
        idValidateAlert.msg = "사용할 수 있는 아이디입니다.";
      }
    }
  };
  const fail = (error) => {
    alert("문제가 발생했습니다. ", error);
  };

  getUser(success, fail, trimJoinUser.user.id);
};

// 데이터 모두 초기화
const initForm = () => {
  joinUserId.value = "";
  joinUser.user.pw = "";
  joinUser.userInfo.name = "";
  joinUser.userInfo.adress = "";
  joinUser.userInfo.email = "";
  joinUser.userInfo.mobile_number = "";
  idValidateAlert.hasError = false;
  idValidateAlert.isValiate = false;
  idValidateAlert.isVisible = false;
  idValidateAlert.msg = "";
  joinAlert.isVisible = false;
  joinAlert.msg = "";
};

// 전화번호 정규표현식
const mobileNumberChange = () => {
  joinUser.userInfo.mobile_number = joinUser.userInfo.mobile_number
    .replace(/[^0-9]/, "")
    .replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);
};

// 포커스 활성화
const focusActive = () => {
  refJoinUserId.value.focus();
};
</script>

<template>
  <!-- Join modal start-->
  <b-modal
    id="joinmodal"
    title="회원가입"
    ref="modalRef"
    @close="initForm"
    @shown="focusActive">
    <form @submit.prevent="doJoin">
      <div class="mb-3">
        <label class="form-label">아이디 입력</label>
        <!-- joinid -->
        <input
          type="text"
          class="form-control"
          id="join-user-id"
          name="join-user-id"
          v-model="joinUserId"
          @change="isValidateId"
          placeholder="아이디"
          ref="refJoinUserId" />
      </div>
      <!-- 아이디 중복 검사 창 -->
      <div
        :class="{
          'mb-3': true,
          'alert-box': true,
          'invalidate-color': idValidateAlert.hasError,
          'validate-color': idValidateAlert.isValiate,
        }"
        v-show="idValidateAlert.isVisible">
        <span v-html="idValidateAlert.msg"></span>
      </div>
      <!-- joinpassword-->
      <div class="mb-3">
        <label class="form-label">비밀번호 입력</label>
        <input
          type="password"
          class="form-control"
          id="join-user-pwd"
          name="join-user-pwd"
          v-model="joinUser.user.pw"
          placeholder="비밀번호" />
      </div>
      <!-- joinemail-->
      <div class="mb-3">
        <label class="form-label">이메일 입력</label>
        <input
          type="email"
          class="form-control"
          id="join-user-email"
          name="join-user-email"
          aria-describedby="emailHelp"
          v-model="joinUser.userInfo.email"
          placeholder="이메일" />
      </div>
      <!-- joinname-->
      <div class="mb-3">
        <label class="form-label">이름 입력</label>
        <input
          type="text"
          class="form-control"
          id="join-user-name"
          name="join-user-name"
          v-model="joinUser.userInfo.name"
          placeholder="이름" />
      </div>
      <!-- joinadress-->
      <div class="mb-3">
        <label class="form-label">주소 입력</label>
        <input
          type="text"
          class="form-control"
          id="join-user-adress"
          name="join-user-adress"
          v-model="joinUser.userInfo.adress"
          placeholder="주소" />
      </div>
      <!-- joinadress-->
      <div class="mb-3">
        <label class="form-label">휴대폰 번호 입력</label>
        <input
          type="tel"
          class="form-control"
          id="join-user-mobile_number"
          name="join-user-mobile_number"
          placeholder="01012345678"
          v-model="joinUser.userInfo.mobile_number"
          maxlength="13"
          @input="mobileNumberChange" />
      </div>
      <!-- 회원가입 실패 여부 띄우기 -->
      <div class="alert-box">
        <BAlert :model-value="joinAlert.isVisible" variant="danger">
          <span v-html="joinAlert.msg"></span>
        </BAlert>
      </div>
      <div class="d-flex justify-content-center">
        <input type="submit" class="btn btn-outline-success" value="회원가입" />
      </div>
    </form>
  </b-modal>
</template>

<style scoped>
.alert-box {
  font-size: 13px;
}

.validate-color {
  color: green;
}

.invalidate-color {
  color: red;
}
</style>
