<script setup>
import { onMounted, ref } from "vue";
import userForm from "@/components/form/UserForm.vue";
import changePasswordForm from "@/components/form/ChangePasswordForm.vue";
// 활성화된 탭을 추적하는 ref 변수입니다.
const modify_active = ref("회원정보 수정");
const viewSelect = ref("회원정보 수정");
// 탭을 클릭했을 때 실행되는 함수입니다.
function setActive(tabName) {
  modify_active.value = tabName;
  viewSelect.value = tabName;
}
</script>

<template>
  <div class="center-inner">
    <div class="tab-nav n2">
      <ul>
        <li
          :class="{ 'is-active': modify_active === '회원정보 수정' }"
          @click="setActive('회원정보 수정')">
          <a>회원정보 수정</a>
        </li>
        <li
          :class="{ 'is-active': modify_active === '비밀번호 변경' }"
          @click="setActive('비밀번호 변경')">
          <a>비밀번호 변경</a>
        </li>
      </ul>
    </div>
  </div>
  <userForm v-if="viewSelect == '회원정보 수정'" />
  <changePasswordForm v-if="viewSelect == '비밀번호 변경'" />
</template>
<style scoped>
/* .container .content-header .center-inner {
    position: absolute;
    left: 0;
    bottom: 0;
    width: 100%;
    background: rgba(25, 42, 59, 0.8);
    border-bottom: 1px solid #3c90e2;
} */

/* .container .content-header .center-inner .tab-nav {
    display: flex;
    justify-content: center;
    max-width: 1200px;
    width: 100%;
    margin: 0 auto;} /* 전체 너비를 100%로 설정하여 부모 요소의 너비에 맞춤 */
.tab-nav {
  display: flex;
  background-color: #4b5766; /* 배경색 추가 */
  justify-content: center; /* 가운데 정렬 */
}
.tab-nav ul {
  display: flex;
  justify-content: center; /* 가운데 정렬 */
  list-style: none;
  padding: 0;
  margin: 0; /* 기본 마진 제거 */
  /*background-color: #4b5766; /* 배경색 추가 */
  width: 80%; /* 너비를 100%로 설정 */
}

.tab-nav li {
  flex: 1; /* 모든 li 요소가 균등한 너비를 가지도록 설정 */
  display: flex; /* 내부의 a 태그를 가운데 정렬하기 위해 추가 */
  justify-content: center; /* 가운데 정렬 */
  align-items: center; /* 수직 가운데 정렬 */
  margin: 0; /* li 요소 사이의 여백 제거 */
  border-left: 1px solid transparent; /* 선을 안보이게 설정 */
}

.tab-nav li a {
  flex: 1; /* a 태그가 li 요소의 전체 너비를 차지하도록 설정 */
  text-align: center; /* 텍스트 가운데 정렬 */
  font-weight: bold;
}

.tab-nav li:hover {
  background-color: #5a6d85; /* 원하는 배경색으로 변경 */
  color: #fff;
}

.tab-nav li.is-active {
  /* background: #3c90e2; */
  background: #feb23c;
  border-color: #feb23c;
  /* border-color: #3c90e2; */
  border-bottom: 2px solid silver; /* 활성 탭 하단에 표시되는 밑줄 추가 */
}

/* is-active 클래스가 적용된 li 요소에 대한 스타일 */
.tab-nav li.is-active a {
  color: #000; /* 글자색 검은색 */
}

/* is-active 클래스가 적용되지 않은 다른 li 요소에 대한 스타일 */
.tab-nav li:not(.is-active) a {
  color: #fff; /* 글자색 흰색 */
}

.tab-nav li.is-active + li {
  border-left-color: transparent; /* 선 안보이게 설정 */
}

.tab-nav li:last-child {
  border-right: 1px solid transparent; /* 선 안보이게 설정 */
}

.tab-nav a {
  display: block;
  font-size: 17px;
  padding: 16px;
  /* color: #fff; */
  color: #000;
  font-weight: 100;
  text-decoration: none; /* 밑줄 제거 */
}

.field-set .file-label,
.radio,
.security-page .btn_Download_webDrm,
.tab-nav li,
.tab-square li {
  cursor: pointer;
}
</style>
