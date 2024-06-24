<template>
  <main class="d-flex">
    <!-- main left side start -->

    <aside
      id="menu"
      :class="{ open: isOpen }"
      class="z-3 position-absolute p-4 rounded-3 bg-light-subtle shadow mx-3 mt-4">
      <div class="left-side">
        <label class="fs-6 form-label" style="white-space: nowrap">
          🏠 원하는 <span class="text-success">지역</span>을 검색해보세요.
        </label>
        <!-- 아파트 매매 정보 검색 start -->
        <BForm @submit.prevent="searchApt">
          <BRow>
            <BCol>
              <BFormGroup>
                <BFormSelect v-model="data.searchSido" @change="onSidoChange">
                  <option value="no">시도선택</option>
                  <option
                    v-for="sido in data.sidos"
                    :key="sido.code"
                    :value="sido.code">
                    <a>{{ sido.name }}</a>
                  </option>
                </BFormSelect>
              </BFormGroup>
            </BCol>
          </BRow>

          <BRow>
            <BCol>
              <BFormGroup>
                <BFormSelect v-model="data.searchGugun" @change="onGugunChange">
                  <option value="no">구군선택</option>
                  <!-- sido와 같은 이름을 가진 것도 출력이 되버려서 filter를 적용함 -->
                  <option
                    v-for="gugun in data.guguns.filter(
                      (gugun) =>
                        gugun.name !==
                        data.sidos.find((sido) => sido.code === data.searchSido)
                          .name
                    )"
                    :key="gugun.code"
                    :value="gugun.code">
                    <a>{{ gugun.name }}</a>
                  </option>
                </BFormSelect>
              </BFormGroup>
            </BCol>
          </BRow>
          <BRow>
            <BCol>
              <BFormGroup>
                <BFormSelect v-model="data.searchDong" @change="onDongChange">
                  <option value="no">동선택</option>
                  <!-- gugun와 같은 이름을 가진 것도 출력이 되버려서 filter를 적용함 -->
                  <option
                    v-for="dong in data.dongs.filter(
                      (dong) =>
                        dong.name !==
                        data.guguns.find(
                          (gugun) => gugun.code === data.searchGugun
                        ).name
                    )"
                    :key="dong.code"
                    :value="dong.code">
                    {{ dong.name }}
                  </option>
                </BFormSelect>
              </BFormGroup>
            </BCol>
          </BRow>
          <BRow>
            <BCol>
              <BFormGroup>
                <BFormSelect v-model="data.searchYear" @change="onYearChange">
                  <option value="no">매매년도선택</option>
                  <option v-for="year in data.years" :key="year" :value="year">
                    {{ year }}년
                  </option>
                </BFormSelect>
              </BFormGroup>
            </BCol>
          </BRow>
          <BRow>
            <BCol>
              <BFormGroup>
                <BFormSelect v-model="data.searchMonth">
                  <option value="no">매매월선택</option>
                  <option
                    v-for="month in data.months"
                    :key="month"
                    :value="month">
                    {{ month }}월
                  </option>
                </BFormSelect>
              </BFormGroup>
            </BCol>
          </BRow>
          <BRow>
            <BCol class="mt-3">
              <BButton type="submit" variant="outline-success"
                >아파트매매정보</BButton
              >
            </BCol>
          </BRow>
        </BForm>
        <!-- 검색 결과 start -->
        <div v-if="resultData.length > 0" class="mt-3">
          <div id="scrollable-area">
            <BTable
              sticky-header
              table
              table-hover
              text-center
              mt-2
              :items="resultData"
              :fields="sortFields" />
          </div>
          <div>
            <span class="me-2">📢</span>
            <span class="fw-bold" style="color: darkgreen"
              >{{ resultData[0].dong }}&nbsp;</span
            >
            <span class="fw-bold">평균 매물 가격은&nbsp;</span>
            <span class="fw-bold" style="color: darkgreen"
              >{{ avgDealAmount }}만원</span
            >
            <span class="fw-bold">입니다</span>
          </div>
        </div>
        <!-- 검색 결과 end -->
        <!-- 아파트 매매 정보 검색 end -->
        <!-- aside leftsize end -->
      </div>

      <!-- aside rightsize end -->
      <div class="right-side">
        <button @click="toggleSidebar" class="sidebar-toggle">
          <span v-if="!isOpen"><i class="bi bi-caret-right-fill"></i></span>
          <span v-else><i class="bi bi-caret-left-fill"></i></span>
        </button>
      </div>
      <!-- aside rightsize end -->
    </aside>
    <!-- main left side end -->

    <!-- 운세 알려주는 위젯 띄우기 -->
    <FortuneTellingWidget />

    <!-- 건물 디테일 창 띄우기 -->
    <BuildingDetail
      :code="apartmentcode"
      :isDetailClose="isDetailClose"
      :data="resultData[clickedBuildingIdx]"
      @detail-window-close="detailWindowClose" />

    <!-- main side start (카카오 맵) -->
    <KakaoMap
      class="z-0"
      id="map"
      :width="innerWidth"
      :height="innerHeight"
      :lat="location.lat"
      :lng="location.lng"
      :draggable="true"
      @onLoadKakaoMap="onLoadKakaoMap">
      <div v-if="resultData.length == 0">
        <KakaoMapMarker :lat="location.lat" :lng="location.lng">
          <template v-slot:infoWindow
            ><div style="padding-left: 35px">현재 위치</div></template
          >
        </KakaoMapMarker>
      </div>
      <div v-for="(result, idx) in resultData">
        <KakaoMapMarker
          :lat="result.lat"
          :lng="result.lng"
          :clickable="true"
          @mouseOverKakaoMapMarker="mouseOverKakaoMapMarker(idx)"
          @mouseOutKakaoMapMarker="mouseOutKakaoMapMarker(idx)"
          @onClickKakaoMapMarker="onClickKakaoMapMarker(idx)"
          :image="{
            imageSrc: '/src/assets/markerImg.png',
            imageWidth: 64,
            imageHeight: 64,
            imageOption: {},
          }" />
        <KakaoMapCustomOverlay
          :lat="result.lat"
          :lng="result.lng"
          :yAnchor="1.4"
          @onLoadKakaoMapCustomOverlay="onLoadKakaoMapCustomOverlay"
          :visible="result.visible">
          <div
            style="
              padding: 10px;
              background-color: white;
              border: 1px solid #ccc;
              border-radius: 5px;
              min-width: 120px;
            ">
            <div style="font-weight: bold; margin-bottom: 5px">
              {{ result.apartmentName }}
            </div>
            <div style="display: flex">
              <div style="flex-grow: 1">
                <div
                  style="
                    overflow: hidden;
                    text-overflow: ellipsis;
                    white-space: nowrap;
                  ">
                  <span class="badge text-bg-warning">{{ result.stat }}</span>
                </div>
                <div
                  style="
                    overflow: hidden;
                    text-overflow: ellipsis;
                    white-space: nowrap;
                  ">
                  {{ result.dealAmount }}(만)원 / {{ result.area }}평
                </div>
              </div>
            </div>
          </div>
        </KakaoMapCustomOverlay>
      </div>
    </KakaoMap>
    <!-- main side end -->
  </main>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import {
  BForm,
  BRow,
  BCol,
  BFormGroup,
  BFormSelect,
  BButton,
  BTable,
} from "bootstrap-vue-next";
import {
  KakaoMap,
  KakaoMapMarker,
  KakaoMapCustomOverlay,
} from "vue3-kakao-maps";
import axios from "axios";
import { getSearch, aptCode } from "@/api/building";
import { useRouter } from "vue-router";

import FortuneTellingWidget from "@/components/bot/FortuneTellingWidget.vue";
import BuildingDetail from "@/components/main/BuildingDetail.vue";

// === setting ===
const router = useRouter();

// == data ==
const kakaomap = ref(); // 카카오 지도
const overlay = ref(); // 카카오맵 커스텀 오버레이

// 지도 width, height를 뷰포트만큼으로 설정
const innerWidth = window.innerWidth;
const innerHeight = window.innerHeight - 83;

// 아파트 매매 정보 검색 후, 마커 클릭 시 아파트 코드를 찾는 request detail 정보
const detailCondition = ref({
  dongCode: "", //data.value.searchDong,
  apartmentName: "",
});
const isDetailClose = ref(true); // 아파트 상세정보창 on/off
const apartmentcode = ref(""); // 클릭한 마커(아파트)의 아파트 코드
const clickedBuildingIdx = ref(0); // 마커가 클릭된 아파트의 인덱스 번호

// 아파트 매매 정보 검색 결과 정렬 필드
const sortFields = [
  { key: "apartmentName", sortable: true, label: "아파트 이름" },
  { key: "floor", sortable: true, label: "층" },
  { key: "area", sortable: true, label: "면적" },
  { key: "dong", sortable: true, label: "법정동" },
  { key: "dealAmount", sortable: true, label: "거래금액" },
];

// 아파트 매매 정보 검색 결과(DB의 housedeal 테이블에서 가져옴)
const resultData = ref([]);

const isOpen = ref(false);

const toggleSidebar = () => {
  isOpen.value = !isOpen.value;
};

// Reactive data properties
const location = ref({
  lat: 0,
  lng: 0,
});
const data = ref({
  searchSido: "no",
  searchGugun: "no",
  searchDong: "no",
  searchYear: "no",
  searchMonth: "no",
  sidos: [],
  guguns: [],
  dongs: [],
  years: [],
  months: [],
  houseList: [],
  sorter: "no",
  order: "no",
});

// 아파트 매매 정보 검색 시, 해당 법정동 매물들의 평균 매매가를 계산한다.
const avgDealAmount = computed(() => {
  let sum = 0;
  for (let result in resultData.value) {
    sum += resultData.value[result].dealAmount.replace(/,/g, "") * 1;
  }
  return (Math.ceil((sum / resultData.value.length) * 10) / 10)
    .toString()
    .replace(/\B(?=(\d{3})+(?!\d))/g, ","); // 반올림 후 3자리마다 쉼표 찍기
});

// === methods ===
// 카카오맵 커스텀 오버레이 start ==
const onLoadKakaoMapCustomOverlay = (newCustomOverlay) => {
  overlay.value = newCustomOverlay;
};
const mouseOverKakaoMapMarker = (idx) => {
  resultData.value[idx].visible = true;
};
const mouseOutKakaoMapMarker = (idx) => {
  resultData.value[idx].visible = false;
};
// 카카오맵 커스텀 오버레이 end ==
// 카카오맵 마커 클릭 & 건물 디테일 창 start ===
const onClickKakaoMapMarker = (idx) => {
  detailCondition.value = {
    apartmentName: resultData.value[idx].apartmentName,
    dongCode: data.value.searchDong,
  };
  const success = (response) => {
    if (response.status === 200) {
      if (
        response.data === "" ||
        response.data === null ||
        response.data === undefined
      ) {
        // 아파트 코드를 불러올 수 없을 때
        alert("아파트 정보를 불러올 수 없어요😥");
        isDetailClose.value = true;
        clickedBuildingIdx.value = 0;
      } else {
        apartmentcode.value = response.data;
        isDetailClose.value = false;
        clickedBuildingIdx.value = idx;
      }
    } else {
      alert("아파트 정보를 불러올 수 없어요😥");
      isDetailClose.value = true;
      clickedBuildingIdx.value = 0;
    }
  };
  const fail = (error) => {
    alert("문제가 발생했습니다. ", error);
    isDetailClose.value = true;
    clickedBuildingIdx.value = 0;
  };
  aptCode(success, fail, detailCondition.value);
};
const detailWindowClose = () => {
  isDetailClose.value = true;
};
// 카카오맵 마커 클릭 & 건물 디테일 창 end ====

const onLoadKakaoMap = (mapRef) => {
  kakaomap.value = mapRef;
  kakaomap.value.setLevel(5);
};
// Initialize options
const initYears = () => {
  const currentYear = new Date().getFullYear();
  for (let i = currentYear; i > currentYear - 20; i--) {
    data.value.years.push(i);
  }
};
const initSido = () => {
  sendRequest("sido", "*00000000");
};

//현재 좌표 구하기
const getCurrentPosition = () => {
  return new Promise((resolve, reject) => {
    if (!navigator.geolocation) {
      reject("Geolocation is not supported by this browser.");
    } else {
      navigator.geolocation.getCurrentPosition(resolve, reject);
    }
  });
};

// Async 함수에서 사용
const fetchLocation = async () => {
  try {
    const position = await getCurrentPosition();
    location.value.lat = position.coords.latitude;
    location.value.lng = position.coords.longitude;
  } catch (error) {
    console.error(error);
  }
};

const onSidoChange = () => {
  if (data.value.searchSido !== "no") {
    const regcode = `${data.value.searchSido.substr(0, 2)}*00000`;
    sendRequest("gugun", regcode);
  } else {
    resetOptions(["gugun", "dong", "year", "month"]);
  }
};
const onGugunChange = () => {
  if (data.value.searchGugun !== "no") {
    const regcode = `${data.value.searchGugun.substr(0, 5)}*`;
    sendRequest("dong", regcode);
  } else {
    resetOptions(["dong", "year", "month"]);
  }
};
const onDongChange = () => {
  if (data.value.searchDong !== "no") {
    updateMonths();
  } else {
    resetOptions(["year", "month"]);
  }
};
const onYearChange = () => {
  if (data.value.searchYear !== "no") {
    updateMonths();
  } else {
    resetOptions(["month"]);
  }
};

const updateMonths = () => {
  const currentYear = new Date().getFullYear();
  const currentMonth = new Date().getMonth() + 1;
  data.value.months = [];
  const maxMonth = data.value.searchYear == currentYear ? currentMonth : 12;
  for (let i = 1; i <= maxMonth; i++) {
    data.value.months.push(i < 10 ? `0${i}` : `${i}`);
  }
};

const resetOptions = (optionIds) => {
  optionIds.forEach((id) => {
    data.value[id] = "no";
  });
  if (optionIds.includes("gugun")) data.value.guguns = [];
  if (optionIds.includes("dong")) data.value.dongs = [];
  if (optionIds.includes("month")) data.value.months = [];
};

// 시도 구군 동 정보 얻기
const sendRequest = async (div, regcode) => {
  const url = `https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes`;
  try {
    const response = await axios.get(url, {
      params: { regcode_pattern: regcode },
    });
    // console.log(response.data.regcodes[0].code);
    const items = [];
    for (let i in response.data.regcodes) {
      items.push({
        code: response.data.regcodes[i].code,
        name: response.data.regcodes[i].name,
      });
    }
    // const items = response.data.regcodes.map((item) => ({
    //   code: item.code,
    //   name:
    //     div === "gugun" ? item.name.split(" ").slice(1).join(" ") : item.name,
    // }));
    if (div === "sido") data.value.sidos = items;
    if (div === "gugun") data.value.guguns = processGuguns(items);
    if (div === "dong") data.value.dongs = processDongs(items);
  } catch (error) {
    console.error(error);
  }
  //   console.log(data.value);
};
// 얻어온 구군 정보 헨들링 (이거 안하면 동에 부산광역시 사하구으로 표시됨)
const processGuguns = (items) => {
  return items.map((item) => {
    const nameParts = item.name.split(" ");
    let gugunName = nameParts.pop();
    return { code: item.code, name: gugunName };
  });
};
// 얻어온 동 정보 헨들링 (이거 안하면 동에 부산광역시 사하구 하단동으로 표시됨)
const processDongs = (items) => {
  return items.map((item) => {
    const nameParts = item.name.split(" ");
    let dongName = nameParts.pop();
    return { code: item.code, name: dongName };
  });
};

// 거래 정보 얻기
// 동 코드로 detail 다 얻어오기
const searchApt = () => {
  if (data.value.searchYear == "no" || data.value.searchMonth == "no") {
    alert("매매년도 또는 매매 월을 선택해주세요");
  } else {
    // console.log("searchApt");
    // console.log(data.value);
    let searchCondition = {
      sido: data.value.searchSido,
      gugun: data.value.searchGugun,
      dong: data.value.searchDong,
      year: data.value.searchYear,
      month: data.value.searchMonth,
      sorter: data.value.sorter,
      order: data.value.order,
    };
    const success = (response) => {
      resultData.value = response.data;
      for (let i = 0; i < resultData.value.length; i++) {
        // 각 데이터별 카카오맵 오버레이 visible을 위한 변수 저장
        resultData.value[i].visible = ref(false);
      }
      // 결과가 없으면 알려주고 메인으로 ㄱㄱ
      if (resultData.value.length == 0) {
        alert("검색 결과 없습니다!");
        getCurrentPosition();
        router.push({
          name: "main",
        });
      } else {
        // console.log(response.data);
        let viewPoint_lat = 0;
        let viewPoint_lng = 0;
        // 검색 결과 평균 좌표 구하기
        for (let i in resultData.value) {
          viewPoint_lat += Number(resultData.value[i].lat);
          viewPoint_lng += Number(resultData.value[i].lng);
        }
        viewPoint_lat /= resultData.value.length;
        viewPoint_lng /= resultData.value.length;
        // console.log(viewPoint_lat, viewPoint_lng);
        // 평균 좌표값으로 화면 이동
        movePosition(viewPoint_lat, viewPoint_lng);
        kakaomap.value.setLevel(5);
        // console.log(resultData.value);
      }
    };
    const fail = (error) => {
      alert(error);
    };
    getSearch(success, fail, searchCondition);
  } //else end
};

const movePosition = (lat, lng) => {
  location.value.lat = lat;
  location.value.lng = lng;
};

// === life cycle hook ===
//화면이 보여질때 실행
onMounted(() => {
  initYears();
  initSido();
  fetchLocation();
});
</script>

<style scoped>
#scrollable-area {
  height: 500px;
  overflow-y: scroll;
}
#menu {
  width: 600px;
  height: 700px;
  transform: translate(-550px, 0); /* 초기 상태: 숨겨짐 */
  transition: 0.5s;
}
#menu.open {
  transform: translate(calc(var(--side-bar-width) * -0.8), 0); /* 열린 상태 */
}
.sidebar-toggle {
  /* 버튼 스타일링 */
  position: absolute;
  top: 350px;
  margin-left: 550px;
  top: 50%; /* 화면의 중앙에 위치하도록 조정 */
  left: 0;
  transform: translateY(-50%);
  cursor: pointer;
  background-color: transparent;
  border: none;
  padding: 6px;
  border-radius: 20%;
  font-size: 1.3em;
}
.sidebar-toggle:hover {
  background-color: rgb(235, 235, 235);
}
#scrollable-area {
  width: 100%;
  height: 350px;
  overflow: auto;
}
#findPasswordLink:hover {
  text-decoration: underline;
}

aside {
  display: flex; /* Flexbox를 사용하여 콘텐츠를 양쪽으로 정렬 */
}

.left-side {
  flex: 5; /* 두 div가 동일한 너비를 가지도록 설정 */
}
.right-side {
  flex: 0.5;
}

/* 필요에 따라 추가 스타일링 */
.left-side {
  margin-right: 0px; /* 오른쪽 여백 설정 */
}

.right-side {
  margin-left: 0px; /* 왼쪽 여백 설정 */
}

.custom-table th,
.custom-table td {
  border-right: 1px solid #ddd; /* 세로선 추가 */
}
.custom-table th:last-child,
.custom-table td:last-child {
  border-right: none; /* 마지막 열의 세로선 제거 */
}
</style>

<!-- #menu {
  width: 600px;
  height: 700px;
  transform: translate(
    -600px,
    0
  ); /* 둥근 모서리의 너비만큼 X축 이동, Y축 고정 */

  transition: 0.5s;
} 
 #menu:hover {
  transform: translate(
    calc(var(--side-bar-width) * -0.8),
    0
  ); /* X축 이동, Y축 고정 */
}  -->
