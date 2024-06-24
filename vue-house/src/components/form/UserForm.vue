<script setup>
import { ref, onMounted } from "vue";
import { getUserInfo, putUser } from "@/api/user";

const id = ref(sessionStorage.getItem("id"));
const userinfo = ref({}); // `reactive` 대신 `ref`를 사용하여 반응형 객체를 생성

onMounted(async () => {
  // `async` 키워드를 추가
  try {
    const response = await getUserInfo(id.value); // API 호출을 await로 처리
    userinfo.value = response.data; // 데이터를 userinfo에 할당
    console.log(userinfo.value);
  } catch (error) {
    console.log(error);
  }
});

const modifyUserInfo = () => {
  const success = (response) => {
    if (response.status == 200) {
      alert("수정이 완료되었습니다.");
    } else {
      alert("오류 발생");
    }
  };
  const fail = (error) => {
    alert("fail 발생");
  };
  putUser(success, fail, userinfo.value);
};
</script>
<template>
  <div class="form">
    <div class="table-container">
      <BTableSimple hover caption-top responsive>
        <caption>
          회원정보입니다.<br />
          수정하고 싶은 부분을 수정하여 밑의 수정 버튼을 눌러주세요!
        </caption>
        <BTbody>
          <BTr class="Btr">
            <BTh colspan="3" style="border-left">사진</BTh>

            <img
              class="no-focus-outline"
              src="/src/assets/testpoto.png"
              style="width: 120px; height: 120px" />
          </BTr>
          <BTr class="Btr">
            <BTh colspan="3" style="border-left">ID</BTh>
            <BFormInput
              class="rounded-0 no-focus-outline input-size"
              v-model="userinfo.id"
              disabled="true" />
          </BTr>
          <BTr class="Btr">
            <BTh colspan="3" style="border-left">이름</BTh>
            <BFormInput
              class="rounded-0 no-focus-outline input-size"
              v-model="userinfo.name" />
          </BTr>
          <BTr class="Btr">
            <BTh colspan="3" style="border-left">등급</BTh>
            <BFormInput
              class="rounded-0 no-focus-outline input-size"
              v-model="userinfo.role"
              disabled="true" />
          </BTr>
          <BTr class="Btr">
            <BTh colspan="3" style="border-left">이메일</BTh>
            <BFormInput
              class="rounded-0 no-focus-outline input-size"
              v-model="userinfo.email" />
          </BTr>
          <BTr class="Btr">
            <BTh colspan="3" style="border-left">주소</BTh>
            <BFormInput
              class="rounded-0 no-focus-outline input-size"
              v-model="userinfo.adress" />
          </BTr>
          <BTr class="Btr">
            <BTh colspan="3" style="border-left">전화번호</BTh>
            <BFormInput
              class="rounded-0 no-focus-outline input-size"
              v-model="userinfo.mobile_number" />
          </BTr>
        </BTbody>
        <BTfoot> </BTfoot>
      </BTableSimple>
      <BButton variant="success" @click="modifyUserInfo()">수정</BButton>
    </div>
  </div>
</template>

<style scoped>
.form {
  margin-top: 10px;
  display: flex; /* Flexbox 레이아웃 사용 */
  justify-content: center; /* 가로 방향으로 중앙 정렬 */
  align-items: center; /* 세로 방향으로 중앙 정렬 */
  width: 100%; /* div의 너비를 부모 요소의 100%로 설정 */
  max-width: 100%; /* div의 최대 너비 설정, 필요에 따라 조정 */
}

.table-container {
  width: 100%;
  max-width: 1000px; /* 테이블이 너무 넓어지지 않도록 최대 너비 설정 */
  margin: 0 auto; /* 테이블을 가운데 정렬 */
  height: 800px;
}

.Btr {
  padding: 0%;
}
.Btr > * {
  height: 50px; /* 또는 원하는 높이 */
}
.no-focus-outline {
  margin-left: 10px;
}
.no-focus-outline:focus {
  outline: none !important;
  box-shadow: none !important; /* Bootstrap에서 추가하는 그림자도 제거 */
}

.input-size {
  width: 100%; /* 또는 원하는 너비 */
  max-width: 700px; /* 최대 너비를 설정하여 넘치지 않도록 함 */
}
</style>
