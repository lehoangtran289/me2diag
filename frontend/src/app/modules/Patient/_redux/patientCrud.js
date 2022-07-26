import { BACKEND_ORIGIN } from "../../../../config";
import axios from "axios";

export const getAllPatients = (params) => {
  const GET_PATIENTS_INFO = BACKEND_ORIGIN + `patient`;
  return axios.get(GET_PATIENTS_INFO, {
    params: params
  });
};

export const deleteSelectedPatient = (id) => {
  const DELETE_PATIENT = BACKEND_ORIGIN + `patient/` + id;
  return axios.delete(DELETE_PATIENT);
};

export const editPatient = (id, patient) => {
  const EDIT_PATIENT = BACKEND_ORIGIN + `patient/` + id;

  let formData = new FormData();
  formData.append('id', patient.id);
  formData.append('name', patient.name);
  formData.append('phoneNo', patient.phoneNo);
  formData.append('email', patient.email);
  formData.append('birthDate', patient.birthDate ? new Date(patient.birthDate).toLocaleDateString() : null);
  formData.append('address', patient.address)
  formData.append('gender', patient.gender);

  if (patient.avatar)
    formData.append('avatar', patient.avatar)

  console.log("here");
  return axios({
    method: "patch",
    url: EDIT_PATIENT,
    data: formData,
    headers: { "Content-Type": "multipart/form-data" },
  });
}

export const createNewPatient = (patient) => {
  const CREATE_PATIENT = BACKEND_ORIGIN + `patient`;

  let formData = new FormData();
  formData.append('id', patient.id);
  formData.append('name', patient.name);
  formData.append('phoneNo', patient.phoneNo);
  formData.append('email', patient.email);
  formData.append('address', patient.address)
  formData.append('birthDate', patient.birthDate ? new Date(patient.birthDate).toLocaleDateString() : null);
  formData.append('gender', patient.gender);

  if (patient.avatar)
    formData.append('avatar', patient.avatar)

  // let formData = new FormData();
  // for ( let key in patient ) {
  //   formData.append(key, patient[key]);
  // }

  console.log("here");
  return axios({
    method: "post",
    url: CREATE_PATIENT,
    data: formData,
    headers: { "Content-Type": "multipart/form-data" },
  });
  // return axios.post(CREATE_PATIENT, patient, {
  //   headers: {
  //     'Content-Type': `multipart/form-data; boundary=123`
  //   }
  // });
}