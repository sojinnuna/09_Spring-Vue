import axios from 'axios';

export const downloadFile = async (fileUrl) => {
  try {
    const link = document.createElement('a'); // a 태그 생성
    link.href = fileUrl; // a태그의 경로에 파일 url 설정

    document.body.appendChild(link); // html의 body에 a 태그 추가
    link.click(); // click 이벤트 강제 발생
    document.body.removeChild(link); // html의 body에서 a 태그 삭제
  } catch (error) {
    console.error(error);
  }
};
