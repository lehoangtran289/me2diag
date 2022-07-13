import { BACKEND_ORIGIN } from "../../../../config";
import axios from "axios";

export const editAccountDetail = (id, updatedUser) => {
  const PATCH_USER = BACKEND_ORIGIN + `user/` + id;

  let formData = new FormData();
  formData.append('id', id);
  if (updatedUser.username) formData.append('username', updatedUser.username);
  if (updatedUser.lastName) formData.append('lastName', updatedUser.lastName);
  if (updatedUser.firstName) formData.append('firstName', updatedUser.firstName);
  if (updatedUser.phoneNo) formData.append('phoneNo', updatedUser.phoneNo);
  if (updatedUser.email) formData.append('email', updatedUser.email);
  if (updatedUser.description) formData.append('description', updatedUser.description);
  if (updatedUser.roles) formData.append('roles', updatedUser.roles);
  if (updatedUser.avatarUrl && updatedUser.avatarUrl instanceof File)
    formData.append('avatar', updatedUser.avatarUrl)

  for (const pair of formData.entries()) {
    console.log(pair[0]+ ', ' + pair[1]);
  }

  return axios({
    method: "patch",
    url: PATCH_USER,
    data: formData,
    headers: { "Content-Type": "multipart/form-data" },
  });
}

export const createNewAccount = (account) => {
  const POST_ACCOUNT = BACKEND_ORIGIN + `user/register`;
  return axios.post(POST_ACCOUNT, account)
}

export const getAllAccounts = (params) => {
  const GET_ACCOUNTS_INFO = BACKEND_ORIGIN + `user`;
  return axios.get(GET_ACCOUNTS_INFO, {
    params: params
  });
};

export const deactivateSelectedAccount = (accountId) => {
  const DEACTIVATE_ACCOUNT = BACKEND_ORIGIN + `user/` + accountId + `/deactivate`;
  console.log(DEACTIVATE_ACCOUNT);
  return axios.delete(DEACTIVATE_ACCOUNT);
}

export const activateSelectedAccount = (accountId) => {
  const ACTIVATE_ACCOUNT = BACKEND_ORIGIN + `user/` + accountId + `/activate`;
  console.log(ACTIVATE_ACCOUNT);
  return axios.post(ACTIVATE_ACCOUNT);
}