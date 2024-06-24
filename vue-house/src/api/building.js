import { localAxios } from "@/utils/http-common";
const axios = localAxios();

const baseURL = "/buildingapi";

// 검색 조건에 맞는 건물 정보를 가져옵니다
export const getSearch = (success, fail, searchCondition) => {
  // console.log(searchCondition);
  axios
    .get(baseURL + "/search", { params: searchCondition })
    .then(success)
    .catch(fail);
};

export const aptCode = (success, fail, detailCondition) => {
  //   console.log(detailCondition);
  axios
    .get(baseURL + "/aptcode", { params: detailCondition })
    .then(success)
    .catch(fail);
};
