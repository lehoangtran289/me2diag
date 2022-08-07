import { BACKEND_ORIGIN } from "../../../../config";
import axios from "axios";

export const getAllExaminations = (params) => {
  const GET_PATIENTS_INFO = BACKEND_ORIGIN + `examinations`;
  return axios.get(GET_PATIENTS_INFO, {
    params: params
  });
}

export const getExamination = (examId) => {
  const GET_EXAM_DETAIL = BACKEND_ORIGIN + `examinations/` + examId;
  return axios.get(GET_EXAM_DETAIL);
}
