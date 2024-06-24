<script setup>
import { ref, watch } from "vue";
import axios from "axios";

// setting
const props = defineProps({
  code: String,
  isDetailClose: Boolean,
  data: Object,
});
const detailCode = ref(props.code);
const detailInfo = ref(null);
watch(
  () => props.code,
  (newCode) => {
    detailCode.value = newCode;

    const url =
      "http://apis.data.go.kr/1613000/AptBasisInfoService1/getAphusDtlInfo";
    const params = {
      kaptCode: detailCode.value,
      ServiceKey:
        "SNx2RBBbT2OfWCaXTrFB5BG8jLJ/EOlcO8wUs2N+8iQgZcdPUTc5Eghg0NN/xDHHqm2qM222Gji6oClo8Y4vCw==", // 여기에 실제 서비스 키를 입력하세요.
    };

    async function fetchData() {
      try {
        const response = await axios.get(url, { params });
        // console.log("response:", response.data.response.body.item);
        detailInfo.value = response.data.response.body.item;
        // console.log("detail: ", detailInfo.value);
      } catch (error) {
        alert("문제가 발생했습니다. ", error);
      }
    }
    fetchData();
    // console.log("New Code:", detailCode.value);
  },
  { immediate: true }
);

const emit = defineEmits(["detailWindowClose"]);

// data
const heartFill = ref("none");
const heartCnt = ref(0);

// methods
const close = () => {
  emit("detailWindowClose");
};

const like = () => {
  if (heartFill.value === "none") {
    heartFill.value = "red";
    heartCnt.value++;
  } else if (heartFill.value === "red") {
    heartFill.value = "none";
    heartCnt.value--;
  }
};
</script>

<template>
  <div
    class="detail-container z-3 position-fixed shadow-sm"
    v-if="!isDetailClose && detailInfo !== undefined">
    <div
      class="detail-container-close d-flex justify-content-end"
      @click.prevent="close">
      <i class="bi bi-x"></i>
    </div>
    <div class="detail-info">
      <div class="d-flex flex-column">
        <p class="detail-info-price">{{ data.apartmentName }}</p>
        <!-- <p class="detail-info-recent">최근 7일 매물 조회 수 14회</p> -->
      </div>
      <ul class="icon-box">
        <li class="d-flex">
          <div class="icon-left">
            <i class="bi bi-building"></i>
            <span>아파트</span>
            <!-- 건물 종류 -->
          </div>
          <div class="icon-right">
            <i class="bi bi-rulers"></i>
            <span>{{ data.area }}㎡</span>
            <!-- 면적 -->
          </div>
        </li>
        <li class="d-flex">
          <div class="icon-left">
            <i class="bi bi-cone-striped"></i>
            <span>{{ detailInfo.codeStr.replace("구조", "") }}</span>
            <!-- 건물 구조 -->
          </div>
          <div class="icon-right">
            <i class="bi bi-tags"></i>
            <span>{{ data.dealAmount }}만원</span>
            <!-- 매매 가격 -->
          </div>
        </li>
        <li class="detail-sub-info">
          <div class="d-flex justify-content-start">
            <p class="title">승강기/CCTV</p>
            <p class="content">
              {{ detailInfo.kaptdEcnt }}대/{{ detailInfo.kaptdCccnt }}대
            </p>
          </div>
          <div class="d-flex justify-content-start">
            <p class="title">버스정류장</p>
            <p class="content">{{ detailInfo.kaptdWtimebus }}</p>
          </div>
          <div class="d-flex justify-content-start">
            <p class="title">지하철역</p>
            <p class="content">{{ detailInfo.kaptdWtimesub }}</p>
          </div>
          <div class="d-flex justify-content-start">
            <p class="title">편의시설</p>
            <p class="content">
              {{
                detailInfo.convenientFacility
                  .replace(/\([^)]*\)/g, "")
                  .replace(/\s+/g, ",")
              }}
            </p>
          </div>
          <div class="d-flex justify-content-start">
            <p class="title">주차</p>
            <p class="content">
              {{ detailInfo.kaptdPcnt + detailInfo.kaptdPcntu }}대
            </p>
          </div>
          <div class="d-flex justify-content-start">
            <p class="title">법정동</p>
            <p class="content">{{ data.dong }}</p>
          </div>
        </li>
      </ul>
      <div class="etc-box">
        <div class="name">집집 사무소</div>
        <div class="greeting"><p>지금 바로 집집에서 집을 줍줍하세요!</p></div>
      </div>
      <div class="btn-box">
        <div class="button-box">
          <button type="button" class="custom-btn">카카오톡 공유</button>
        </div>
        <div class="like-box">
          <button class="like-btn" @click.prevent="like">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              height="24"
              viewBox="0 0 24 24">
              <g v-bind:fill="heartFill" fill-rule="evenodd">
                <g stroke="#222" stroke-width="1.5">
                  <g>
                    <g>
                      <g>
                        <path
                          d="M12.945 3.518c2.096-2.028 5.423-2.028 7.518 0l.117.117c1.945 2.01 1.892 5.215-.117 7.16h0l-3.768 3.646-4.699 4.548-3.752-3.631-.016-.015-4.699-4.548c-2.009-1.945-2.061-5.15-.116-7.16l.116-.117h0c2.096-2.028 5.423-2.028 7.518 0l.949.92z"
                          transform="translate(-250 -216) translate(250 121) translate(0 92) translate(0 3)"></path>
                      </g>
                    </g>
                  </g>
                </g>
              </g>
            </svg>
            <span class="cnt-text">{{ heartCnt }}</span>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
i {
  font-size: 24px;
}
ul {
  list-style: none;
}
p,
ul {
  margin: 0px;
  padding: 0px;
}
button {
  margin: 0px;
  padding: 0px;
  border-radius: 0px;
  background-color: rgb(255, 255, 255);
  cursor: pointer;
  outline: none;
  touch-action: manipulation;
  -webkit-tap-highlight-color: transparent;
}
button > span {
  position: relative;
}
.detail-container {
  top: 170px;
  right: 40px;
  width: 360px;
  height: 600px;
  padding: 32px;
  background-color: white;
  border-radius: 2px;
  border: 1px solid rgb(223, 223, 223);
}
.detail-info-price {
  font-size: 24px;
  font-weight: bold;
  line-height: 38px;
  letter-spacing: -0.5px;
}
.detail-info-recent {
  font-size: 14px;
  line-height: 24px;
  color: rgb(101, 101, 101);
}
.detail-container-close {
  margin-bottom: 14px;
  margin: 0px;
  padding: 0px;
  cursor: pointer;
}
.detail-sub-info {
  flex-direction: column;
  width: 100%;
  padding-top: 8px;
}
.detail-sub-info > div > p.title {
  margin-right: 8px;
  /* width: 46px; */
  width: 85px;
  font-weight: 700;
}
.detail-sub-info > div > p {
  font-size: 14px;
  line-height: 24px;
}
.icon-box {
  margin-top: 24px;
  padding-bottom: 24px;
  border-bottom: 1px solid rgb(245, 245, 245);
  font-size: 16px;
  line-height: 26px;
}
.icon-box li + li {
  margin-top: 20px;
}
.icon-box li {
  display: flex;
}
.icon-left {
  width: 162px;
}
.icon-left span {
  margin-left: 18px;
  white-space: nowrap;
  white-space-collapse: collapse;
  text-wrap: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
  overflow-x: hidden;
}
.icon-right {
  display: flex;
  -webkit-box-align: center;
  align-items: center;
}
.icon-right span {
  margin-left: 18px;
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
}
.etc-box {
  padding-top: 24px;
}
.name {
  display: flex;
  -webkit-box-align: center;
  align-items: center;
  font-size: 16px;
  line-height: 26px;
  color: rgb(34, 34, 34);
  font-weight: 700;
}
.greeting {
  margin-top: 12px;
}
.greeting > p {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  color: rgb(101, 101, 101);
  font-size: 14px;
  line-height: 24px;
  white-space: pre-wrap;
  text-overflow: ellipsis;
  overflow: hidden;
}
.btn-box {
  display: flex;
  -webkit-box-align: center;
  align-items: center;
  width: 100%;
  margin-top: 32px;
}
.btn-box div + div {
  width: 77px;
  margin-left: 8px;
}
.button-box {
  border: 0px;
}
.custom-btn {
  flex: 0 0 auto;
  display: inline-flex;
  -webkit-box-align: center;
  align-items: center;
  -webkit-box-pack: center;
  justify-content: center;
  width: 211px;
  padding: 0px 16px;
  color: rgb(255, 255, 255);
  border: 0px;
  border-radius: 2px;
  transition: all 150ms ease-out 0s;
  cursor: pointer;
  height: 56px;
  font-size: 14px;
  line-height: 24px;
  font-weight: 700;
  background-color: darkgreen;
}
.like-box {
  display: flex;
  -webkit-box-align: center;
  align-items: center;
  -webkit-box-pack: center;
  justify-content: center;
  width: 100%;
  height: 56px;
  border: 1px solid rgb(223, 223, 223);
  cursor: pointer;
}
.like-btn {
  display: flex;
  -webkit-box-align: center;
  align-items: center;
  -webkit-box-pack: center;
  justify-content: center;
  width: 100%;
  height: 100%;
  border: 0px;
  background-color: transparent;
}
.cnt-text {
  margin-left: 6px;
  font-size: 14px;
  line-height: 24px;
  color: rgb(34, 34, 34);
  font-weight: 700;
}
</style>
