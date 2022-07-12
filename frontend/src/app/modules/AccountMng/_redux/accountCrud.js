import { BACKEND_ORIGIN } from "../../../../config";
import axios from "axios";

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