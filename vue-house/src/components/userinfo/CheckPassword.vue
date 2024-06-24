<script setup>
import { ref, reactive } from "vue";
import { postLogin } from "@/api/user.js";
import { useRouter, useRoute } from "vue-router";
const router = useRouter();
const route = useRoute();
const user = reactive({
  id: sessionStorage.getItem("id"),
  pw: "",
});

const msg = ref(false);
const onSubmit = () => {
  const success = (response) => {
    console.log(response.data);
    if (response.data === "") {
      msg.value = true;
    } else {
      // 비밀번호 인증 여부를 위한 localStorage
      localStorage.setItem("isAuthenticated", true);
      router.push({
        name: "viewUserInfo",
      });
      console.log(response.data);
    }
  };
  const fail = (error) => {
    console.log(error);
  };
  postLogin(success, fail, user);
};
</script>

<template>
  <hr style="margin-bottom: 30px" />
  <div class="d-flex justify-content-center">
    <div class="content" style="width: 80%">
      <div class="tit-area b-line">
        <h3 class="title" s>비밀번호 확인</h3>
      </div>
      <div class="input-form">
        <p class="sub-top-desc">
          개인 정보 조회를 위해서는 인증이 필요합니다.<br />비밀번호 입력 후
          확인 버튼을 클릭해 주세요.
        </p>

        <BForm @submit="onSubmit">
          <BFormGroup id="input-group-1" label="비밀번호" label-for="input-1">
            <BFormInput
              id="input-1"
              v-model="user.pw"
              type="password"
              placeholder="Enter PassWord"
              required />
          </BFormGroup>
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
              >비밀번호를 다시 확인해주세요</a
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
</style>
