<script setup>
import { useRouter } from 'vue-router';
const router = useRouter();
const logout = (e) => {
  // 로그아웃
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
