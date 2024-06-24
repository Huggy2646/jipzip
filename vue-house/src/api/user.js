import { localAxios } from '@/utils/http-common';
const axios = localAxios();

const baseURL = '/userapi';

// 로그인
export const postLogin = (success, fail, user) => {
    axios
        .post(baseURL + '/login', JSON.stringify(user))
        .then(success)
        .catch(fail);
};

// id로 유저 정보 조회
export const getUser = (success, fail, id) => {
    axios
        .get(baseURL + `/users/${id}`)
        .then(success)
        .catch(fail);
};

// id로 유저 가입 정보 조회
// export const getUserInfo = (success, fail, id) => {
//   axios
//     .get(baseURL + `/infos/${id}`)
//     .then(success)
//     .catch(fail);
// };

// id로 유저 가입 정보 조회
// 동기방식
export const getUserInfo = async (id) => {
    try {
        const response = await axios.get(baseURL + `/infos/${id}`);
        return response; // 성공시 response 반환
    } catch (error) {
        throw error; // 실패시 error를 throw
    }
};

// 회원가입
export const postUser = (success, fail, request) => {
    axios
        .post(baseURL + '/users', JSON.stringify(request))
        .then(success)
        .catch(fail);
};

// 회원 정보 수정
export const putUser = (success, fail, user) => {
    axios
        .put(baseURL + '/users', JSON.stringify(user))
        .then(success)
        .catch(fail);
};

// 회원 탈퇴
export const delUser = (success, fail, id) => {
    axios
        .delete(baseURL + '/users', JSON.stringify(id))
        .then(success)
        .catch(fail);
};

// 비밀번호 변경
export const modifypw = (success, fail, modifyInfo) => {
    axios
        .put(baseURL + '/modifypw', JSON.stringify(modifyInfo))
        .then(success)
        .catch(fail);
};
