import { localAxios } from "@/utils/http-common";
const axios = localAxios();

const baseURL = "/botapi";

export const fortuneTell = (success, fail, question) => {
  axios
    .post(baseURL + "/fortuneTell", JSON.stringify({ question: question }))
    .then(success)
    .catch(fail);
};
