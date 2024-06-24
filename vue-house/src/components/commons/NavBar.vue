<template>
  <header style="margin-bottom: 1px">
    <b-navbar
      toggleable="md"
      type="light"
      variant="light"
      class="bg-body-tertiary pt-3 pb-3 border-bottom fixed-top">
      <img
        class="logo"
        @click.prevent="$router.push({ name: 'main' })"
        src="/src/assets/jipzip_logo.png"
        style="width: 100px; height: 50px" />
      <!-- 
      <template #brand>
        <b-navbar-brand href="<%= root %>/">집ZIP</b-navbar-brand>
      </template> -->

      <!-- 부동산 HOT 이슈 start -->
      <b-nav class="ms-2 me-auto">
        <b-nav-item><b-badge variant="warning">HOT!</b-badge></b-nav-item>
        <b-nav-item>
          <div
            id="carouselExampleSlidesOnly"
            class="carousel slide"
            data-bs-ride="carousel"
            interval="1">
            <div class="carousel-inner">
              <div
                class="carousel-item"
                v-for="(item, index) in items"
                :key="index"
                :class="{ active: index === 0 }">
                <a :href="item.url" target="_blank">{{ item.title }}</a>
              </div>
            </div>
          </div>
        </b-nav-item>
      </b-nav>
      <!-- 부동산 HOT 이슈 end -->

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav class="ms-auto me-2">
          <b-nav-item @click.prevent="goBoard"> 게시판 </b-nav-item>
        </b-navbar-nav>

        <!-- 로그인 유저가 없을 때 활성화 -->
        <b-navbar-nav v-if="!loginuser" class="me-4">
          <span id="user_display"></span>
          <b-nav-item>
            <button class="custom-nav-btn" v-b-modal.loginmodal>로그인</button>
          </b-nav-item>
          <b-nav-item>
            <button class="custom-nav-btn" v-b-modal.joinmodal>회원가입</button>
          </b-nav-item>
        </b-navbar-nav>

        <!-- 로그인한 유저가 있을 때 활성화 -->
        <b-navbar-nav v-if="loginuser" class="me-4">
          <b-nav-item>
            <div ref="refDropdownContainer">
              <div
                class="custom-dropdown-bar"
                @click.prevent="isDropdownOpen = !isDropdownOpen">
                <span class="me-1"
                  ><img
                    class="profile-img"
                    src="@/assets/testpoto.png"
                    alt="프로필"
                /></span>
                <span class="custom-dropdown-icon" v-show="isDropdownOpen"
                  ><i class="bi bi-caret-up-fill"></i
                ></span>
                <span class="custom-dropdown-icon" v-show="!isDropdownOpen"
                  ><i class="bi bi-caret-down-fill"></i
                ></span>
              </div>
              <div
                class="custom-dropdown-content shadow-sm rounded-1"
                v-show="isDropdownOpen">
                <div class="d-flex flex-column p-3">
                  <div class="d-flex flex-row dropdown-content-top">
                    <div>
                      <img
                        class="dropdown-profile-img"
                        src="@/assets/testpoto.png"
                        alt="프로필" />
                    </div>
                    <div
                      class="d-flex flex-column justify-content-center ms-3 me-auto">
                      <div class="fw-bold text-center" style="font-size: 1.2em">
                        {{ loginuser }}
                      </div>
                      <div class="mt-2">
                        <button
                          class="btn btn-success btn-sm"
                          @click.prevent="goCheckPassword">
                          회원정보
                        </button>
                      </div>
                    </div>
                  </div>
                  <hr />
                  <div class="dropdown-content-main">
                    <a href="#" @click.prevent="doLogout"
                      ><i class="bi bi-power me-2"></i>로그아웃</a
                    >
                  </div>
                </div>
              </div>
            </div>
          </b-nav-item>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>

    <div style="height: 82px"></div>
    <!-- loginmodal -->
    <LoginModal :hideFooter="true" @login="saveSession" />
    <!-- JoinModal -->
    <JoinModal :hideFooter="true" />
  </header>
</template>

<script setup>
import { useRouter } from "vue-router";

import { ref, reactive, onMounted, onUnmounted } from "vue";

import LoginModal from "@/components/modal/LoginModal.vue";
import JoinModal from "@/components/modal/JoinModal.vue";

const loginuser = ref(sessionStorage.getItem("id"));

const router = useRouter();
const items = reactive([
  {
    url: "https://n.news.naver.com/article/015/0004986369",
    title: '"절대 이사 못 들어온다" 부글부글…대구 아파트서 무슨 일이',
  },
  {
    url: "https://n.news.naver.com/article/009/0005305452",
    title:
      "[단독] “당첨되면 차익 20억원”…반포 원펜타스, 후분양에도 싸게 나오는 이유",
  },
  {
    url: "https://n.news.naver.com/article/015/0004986372",
    title:
      "심각한 전세시장, 앞으로가 더 문제입니다 [더 머니이스트-심형석의 부동산정석]",
  },
  {
    url: "https://n.news.naver.com/article/020/0003565434",
    title: "전세 대신 월세살이 택하는 신혼부부…“목돈 아껴 향후 투자”",
  },
  {
    url: "https://n.news.naver.com/article/029/0002874452",
    title: "재출발하는 상계주공5 재건축…`분담금 5억`도 버거운데",
  },
]);
// data
const isDropdownOpen = ref(false);
const refDropdownContainer = ref(null);

// method
const goBoard = () => {
  router.push({
    name: "qnaTable",
  });
};
const saveSession = (logininfo) => {
  loginuser.value = logininfo.id;
  sessionStorage.setItem("id", logininfo.id);
};
const doLogout = () => {
  sessionStorage.removeItem("id");
  loginuser.value = "";
  router.push({ name: "main" });
  isDropdownOpen.value = false;
};
const goCheckPassword = () => {
  router.push({
    name: "checkPassword",
  });
  isDropdownOpen.value = false;
};
const closeDropdown = (event) => {
  if (
    refDropdownContainer.value &&
    !refDropdownContainer.value.contains(event.target)
  ) {
    isDropdownOpen.value = false;
  }
};

// life cycle hook
onMounted(() => {
  document.addEventListener("click", closeDropdown);
});

onUnmounted(() => {
  document.removeEventListener("click", closeDropdown);
});
</script>

<style scoped>
.logo {
  cursor: pointer;
}
.profile-img {
  width: 50px;
  border-radius: 100%;
}
.dropdown-profile-img {
  width: 80px;
  border-radius: 100%;
}
.custom-dropdown-icon {
  font-size: 0.8em;
}
.custom-dropdown-bar {
  cursor: pointer;
}
.custom-dropdown-content {
  position: absolute;
  right: 50px;
  top: 75px;
  z-index: 1000;
  width: 250px;
  height: 170px;
  background-color: white;
}
.btn-sm {
  height: 25px;
  font-size: 0.7em;
  text-align: center;
  vertical-align: center;
}
.custom-nav-btn {
  padding-top: 3px;
  padding-bottom: 3px;
  padding-left: 10px;
  padding-right: 10px;
  border: 1px solid rgb(223, 223, 223);
  background-color: transparent;
}
.dropdown-content-main a {
  text-align: left;
  padding-bottom: 0.4rem;
  padding-top: 0.4em;
  color: black;
  text-decoration: none;
  display: block;
  font-size: 1em;
}
.dropdown-content-main a:hover {
  color: darkgreen;
}
</style>
