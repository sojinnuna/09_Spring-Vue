// 현재 폴더의 index.js를 불러옴
import api from '@/api';
// import api from 'axios';

const BASE_URL = '/api/member'; // 기본 URL 설정
const headers = { 'Content-Type': 'multipart/form-data' }; // 헤더 설정

export default {
  // username 중복 체크, true: 중복(사용 불가), false: 사용 가능
  async checkUsername(username) {
    // 주어진 username 사용해서 중복 여부 체크하는 API 호출
    const { data } = await api.get(`${BASE_URL}/checkusername/${username}`);
    console.log('AUTH GET CHECKUSERNAME', data);
    return data;
  },
  async create(member) {
    const formData = new FormData();
    formData.append('username', member.username);
    formData.append('email', member.email);
    formData.append('password', member.password);

    if (member.avatar) {
      //  아바타 이미지가 존재할 경우 (null이 아니면 true로 return 됨)
      formData.append('avatar', member.avatar);
    }

    // 회원 가입 요청을 API에 보내고 응답 데이터를 반환
    const { data } = await api.post(BASE_URL, formData, headers);
    console.log('AUTH POST: ', data);
    return data;
  },

  async update(member) {
    const formData = new FormData();
    formData.append('username', member.username);
    formData.append('password', member.password);
    formData.append('email', member.email);

    if (member.avatar) {
      formData.append('avatar', member.avatar);
    }

    //  회원 가입 요청을 API에 보내고 응답 데이터를 반환
    const { data } = await api.put(
      `${BASE_URL}/${member.username}`,
      formData,
      headers
    );
    console.log('AUTH PUT: ', data);
    return data;
  },

  async changePassword(formData) {
    // 세번째 값을 생략하면 기본값이 json 데이터 형태로 넘어간다
    const { data } = await api.put(
      `${BASE_URL}/${formData.username}/changepassword`,
      formData
    );
    console.log('AUTH PUT: ', data);

    return data;
  },
  // 게시글 수정
  async update(article) {
    const formData = new FormData();
    formData.append('no', article.no);
    formData.append('title', article.title);
    formData.append('writer', article.writer);
    formData.append('content', article.content);
    if (article.files) {
      // 첨부파일이 있는 경우
      for (let i = 0; i < article.files.length; i++) {
        formData.append('files', article.files[i]);
      }
    }
    const { data } = await api.put(`${BASE_URL}/${article.no}`, article, {
      headers,
    });
    console.log('BOARD PUT: ', data);
    return data;
  },

  // 첨부파일 삭제
  async deleteAttachment(no) {
    const { data } = await api.delete(`${BASE_URL}/deleteAttachment/${no}`);
    console.log('ATTACHMENT DELETE: ', data);
    return data;
  },
};
