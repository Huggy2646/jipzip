import { localAxios } from "@/utils/http-common";
const axios = localAxios();

const baseURL = "/newsapi";

export const postNews = (success, fail, news) => {
  axios
    .post(baseURL + "/news", JSON.stringify(news))
    .then(success)
    .catch(fail);
};
