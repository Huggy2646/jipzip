import { localAxios } from "@/utils/http-common";
const axios = localAxios();

const baseURL = "/mail";

// 비밀번호 찾기
export const findpw = (success, fail, id) => {
  axios
    .put(baseURL + "/findpw", JSON.stringify(id))
    .then(success)
    .catch(fail);
};
