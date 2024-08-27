<script setup>
import { useRouter } from 'vue-router';
const router = useRouter();
// 루트 경로로 이동 시켜주는 함수
const logout = (e) => {
  router.push('/');
};
</script>
<template>
  <!-- prevent 수식어로 href로 이동하는 기본 동작을 막아두고 logout 함수만 호출 -->
  <a href="#" class="nav-link" @click.prevent="logout">
    <i class="fa-solid fa-right-from-bracket"></i>
    로그아웃
  </a>
</template>

<style></style>
