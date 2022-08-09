import axios from "axios";
import {LOGIN_URL, ME_URL, REGISTER_URL, REQUEST_PASSWORD_URL, RESET_PASSWORD_URL} from "../../../../config";

export function login(username, password) {
  return axios.post(LOGIN_URL, { username, password });
}

export function register(email, fullname, username, password) {
  return axios.post(REGISTER_URL, { email, fullname, username, password });
}

export function requestPassword(email) {
  return axios.post(REQUEST_PASSWORD_URL, { email });
}

export function resetPassword(data) { //token, newPassword
  return axios.post(RESET_PASSWORD_URL, data)
}

export function getUserByToken() {
  // Authorization head should be fulfilled in interceptor.
  return axios.get(ME_URL);
}
