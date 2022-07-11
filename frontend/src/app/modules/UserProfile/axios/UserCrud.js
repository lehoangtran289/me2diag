import axios from "axios";
import {BACKEND_ORIGIN} from "../../../../config";

export const updateUser = ({ id, updatedUser }) => {
  const PATCH_USER_INFO = BACKEND_ORIGIN + `user/` + id;

  let formData = new FormData();
  formData.append('id', id);
  if (updatedUser.username) formData.append('username', updatedUser.username);
  if (updatedUser.firstName) formData.append('firstName', updatedUser.firstName);
  if (updatedUser.lastName) formData.append('lastName', updatedUser.lastName);
  if (updatedUser.phoneNo) formData.append('phoneNo', updatedUser.phoneNo);
  if (updatedUser.email) formData.append('email', updatedUser.email);
  if (updatedUser.description) formData.append('description', updatedUser.description);
  if (updatedUser.avatarUrl && updatedUser.avatarUrl instanceof File)
    formData.append('avatar', updatedUser.avatarUrl)

  for (var pair of formData.entries()) {
    console.log(pair[0]+ ', ' + pair[1]);
  }

  return axios({
    method: "patch",
    url: PATCH_USER_INFO,
    data: formData,
    headers: { "Content-Type": "multipart/form-data" },
  });
};

export const deactivateAccount = ({ id }) => {
  const DEACTIVATE_ACCOUNT = BACKEND_ORIGIN + `user/` + id;
  return axios.delete(DEACTIVATE_ACCOUNT);
}

export const changeUserPassword = ({ id, data }) => {
  const POST_CHANGE_PASSWORD = BACKEND_ORIGIN + `user/` + id + `/change-password`;
  return axios.post(POST_CHANGE_PASSWORD, data);
}