import { BACKEND_ORIGIN } from "../../../../config";
import axios from "axios";

export const getAllPatients = () => {
  const GET_PATIENTS_INFO = BACKEND_ORIGIN + `patient`;
  return axios.get(GET_PATIENTS_INFO);
};