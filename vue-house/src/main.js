import "./assets/main.css";

import { createApp } from "vue";
import { createBootstrap } from "bootstrap-vue-next";
import { useKakao } from "vue3-kakao-maps/@utils";

import App from "./App.vue";
import router from "./router";
import "bootstrap";
import "bootstrap-icons/font/bootstrap-icons.css";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-vue-next/dist/bootstrap-vue-next.css";

useKakao(import.meta.env.VITE_KAKAO_MAP_KEY);

const app = createApp(App);

// app.use(createPinia())

app.use(router);
// app.use(PrimeVue);
app.use(createBootstrap({ components: true, directives: true })); // Change this line
app.mount("#app");
