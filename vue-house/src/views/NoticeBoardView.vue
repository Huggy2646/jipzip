<template>
  <div class="container-fluid">
    <div
      class="d-flex align-items-end justify-content-center mb-5"
      style="height: 100px; text-decoration: underline">
      <h3 class="fw-bold">🚩공지사항🚩</h3>
    </div>
    <div class="d-flex justify-content-center mt-5">
      <RouterView boardType="notice" />
    </div>
  </div>
</template>

<script setup></script>

<style scoped></style>
