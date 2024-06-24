<script setup>
import { ref, watch } from "vue";
import { modifypw } from "@/api/user";
import { useRouter } from "vue-router";
const router = useRouter();
const currPw = ref("");
const newPw = ref("");
const newPwAgain = ref("");
const isSame = ref(false);

watch(newPwAgain, (newValue, oldValue) => {
  if (newValue === newPw.value) {
    isSame.value = false;
  } else {
    isSame.value = true;
  }
});

const changePw = () => {
  const modifyinfo = {
    // 나중에 세션에서 가져오기
    id: sessionStorage.getItem("id"),
    pw: currPw.value,
    modifypw: newPwAgain.value,
  };
  const success = (response) => {
    if (response.data === "") {
      alert("비밀번호가 잘못됨");
    } else {
      if (response.data == "1") {
        alert("비밀번호 변경이 완료되었습니다.");
        router.push({ name: "userInfo" });
      } else {
        alert("비밀번호 변경을 완료하지 못하였습니다.");
      }
    }
  };
  const fail = (error) => {
    alert("문제가 발생했어요. " + error);
  };
  modifypw(success, fail, modifyinfo);
};
</script>

<template>
  <div class="d-flex justify-content-center">
    <div class="content" style="width: 80%">
      <div class="tit-area b-line">
        <h3 class="title" style="margin-top: 20px">비밀번호 변경</h3>
      </div>
      <div class="input-form">
        <h3 style="margin-bottom: 30px">
          새로 사용할 비밀번호를 입력해주세요.
        </h3>
        <BForm @submit="changePw">
          <BFormGroup
            class="formgroup"
            id="input-group-1"
            label="현재 비밀번호"
            label-for="currentPw">
            <BFormInput
              id="currentPw"
              type="password"
              placeholder="Enter Current Password"
              v-model="currPw"
              required />
          </BFormGroup>
          <BFormGroup
            class="formgroup"
            id="input-group-2"
            label="새 비밀번호"
            label-for="newPw">
            <BFormInput
              id="newPw"
              type="password"
              placeholder="Enter New Password"
              v-model="newPw"
              required />
          </BFormGroup>
          <BFormGroup
            id="input-group-3"
            label="새 비밀번호 확인"
            label-for="newPwAgain">
            <BFormInput
              id="newPwAgain"
              type="password"
              placeholder="Enter New Password Again"
              v-model="newPwAgain"
              required />
          </BFormGroup>
          <a class="text-danger no-underline" v-if="isSame == true"
            >새로운 비밀번호가 다릅니다.</a
          >
          <div class="d-flex justify-content-center">
            <b-row>
              <b-col lg="50" class="pb-2">
                <BButton class="button" type="submit" variant="primary"
                  >확인</BButton
                >
              </b-col>
            </b-row>

            <br /><br /><br />
          </div>
          <center>
            <BBadge variant="danger" v-if="msg == true">Danger</BBadge><br />
            <a class="danger" v-if="msg == true"
              >현재 비밀번호를 다시 확인해주세요</a
            >
          </center>
        </BForm>
      </div>
    </div>
  </div>
</template>

<style scoped>
.tit-area.b-line {
  padding-bottom: 10px;
  border-bottom: 1px solid #24282b;
  margin-bottom: 20px;
}

.button {
  margin-top: 10px;
  height: fit-content;
}

.input-form {
  margin-left: 30%;
  margin-right: 30%;
  width: auto;
}
.sub-top-desc {
  margin-left: 20%;
  margin-top: 10%;
}
.formgroup {
  margin-bottom: 20px;
}
.no-underline {
  text-decoration: none;
}
</style>
