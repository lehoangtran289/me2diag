import {BACKEND_ORIGIN} from "../../../../config";

export const getAllPatients = () => {
  const GET_PATIENTS_INFO = BACKEND_ORIGIN + `patient`;
  return fetch(GET_PATIENTS_INFO, {
    method: "GET",
  }).then(res => {
    if (!res.ok) {
      return null;
    }
    return res.json()
  });
}