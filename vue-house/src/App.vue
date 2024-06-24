<script setup>
import { RouterLink, RouterView } from "vue-router";
import NavBar from "@/components/commons/NavBar.vue";
</script>

<template>
  <div calss="app">
    <NavBar />
    <RouterView />
  </div>
</template>

<style scoped></style>
