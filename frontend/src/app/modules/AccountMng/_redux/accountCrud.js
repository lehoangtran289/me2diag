import { BACKEND_ORIGIN } from "../../../../config";
import axios from "axios";

export const getAllAccounts = (params) => {
  const GET_ACCOUNTS_INFO = BACKEND_ORIGIN + `user`;
  return axios.get(GET_ACCOUNTS_INFO, {
    params: params
  });
};