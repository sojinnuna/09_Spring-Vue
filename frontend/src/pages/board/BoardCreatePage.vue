<script setup>
import boardApi from '@/api/boardApi';
import { ref, reactive, computed } from 'vue';
import { useAuthStore } from '@/stores/auth';
import { useRoute, useRouter } from 'vue-router';

const auth = useAuthStore();
const cr = useRoute();
const router = useRouter();

const files = ref(null);
const loading = ref(false);
const errorMessage = ref('');

// 게시글 데이터를 관리하는 반응형 객체
const article = reactive({
  writer: auth.username,
  title: '',
  content: '',
  files: null,
});

// 글 제목과 내용이 없으면 제출 버튼 비활성화
const disableSubmit = computed(() => !article.title || !article.content);

const submit = async () => {
  // 팝업 떴을 때 취소 누르면 함수 종료
  if (!confirm('등록할까요?')) return;

  // 파일이 선택된 경우, 게시글 객체에 파일 추가
  if (files.value.files.length > 0) {
    article.files = files.value.files;
  }

  loading.value = true;
  errorMessage.value = '';

  try {
    await boardApi.create(article);
    // 게시글 생성후 전체 게시글 목록으로 이동
    router.push('/board/list');
  } catch (error) {
    errorMessage.value = '게시글 등록에 실패했습니다. 다시 시도해주세요.';
  } finally {
    loading.value = false;
  }
};
</script>

<template>
  <h1><i class="fa-regular fa-pen-to-square"></i> 글 작성</h1>

  <form @submit.prevent="submit">
    <div class="mb-3 mt-3">
      <label for="title" class="form-label"> 제목 </label>
      <input
        type="text"
        class="form-control"
        placeholder="제목"
        id="title"
        v-model="article.title"
      />
    </div>
    <div class="mb-3 mt-3">
      <label for="files" class="form-label"> 첨부파일 </label>
      <input
        type="file"
        class="form-control"
        placeholder="첨부파일"
        id="files"
        ref="files"
        multiple
      />
    </div>
    <div class="mb-3 mt-3">
      <label for="content" class="form-label"> 내용 </label>
      <textarea
        class="form-control"
        placeholder="내용"
        id="content"
        v-model="article.content"
        rows="10"
      ></textarea>
    </div>

    <div v-if="errorMessage" class="alert alert-danger">
      {{ errorMessage }}
    </div>

    <div class="my-5 text-center">
      <button
        type="submit"
        class="btn btn-primary me-3"
        :disabled="disableSubmit || loading"
      >
        <i class="fa-solid fa-check"></i>
        {{ loading ? '처리 중...' : '확인' }}
      </button>
      <router-link class="btn btn-primary" :to="{ name: 'board/list' }">
        <i class="fa-solid fa-list"></i>
        목록
      </router-link>
    </div>
  </form>
</template>
