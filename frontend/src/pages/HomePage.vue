<script setup>
import { ref } from "vue";

const memo = ref("");
const memos = ref([]);

const addMemo = () => {
  if (memo.value) {
    memos.value.push(memo.value);
    memo.value = ""; // 메모 추가 후 입력 필드 초기화
  }
};

const gotoMemoBoard = () => {};
</script>

<template>
  <h1>방명록</h1>
  <input v-model="memo" placeholder="방문 인사를 남겨주세요" />
  <button @click="addMemo">추가</button>
  <button @click="gotoMemoBoard">방명록으로</button>
  <ul style="list-style: none">
    <li
      v-for="(item, index) in memos"
      :key="index"
      style="border: 1px solid black; padding: 10px; margin: 5px"
    >
      {{ item }}
    </li>
  </ul>
</template>
