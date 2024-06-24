import axios from "axios";

const { VITE_API_BASE_URL } = import.meta.env;

export const localAxios = () => {
  const instance = axios.create({
    baseURL: VITE_API_BASE_URL,
    timeout: 10000, // 10초
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });

  return instance;
};
