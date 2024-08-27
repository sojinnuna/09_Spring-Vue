import api from 'axios';
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
};
