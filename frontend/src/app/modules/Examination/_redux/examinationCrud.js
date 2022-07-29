import { BACKEND_ORIGIN } from "../../../../config";
import axios from "axios";

export const getAllExaminations = (params) => {
  const GET_PATIENTS_INFO = BACKEND_ORIGIN + `examinations`;
  return axios.get(GET_PATIENTS_INFO, {
    params: params
  });
}
