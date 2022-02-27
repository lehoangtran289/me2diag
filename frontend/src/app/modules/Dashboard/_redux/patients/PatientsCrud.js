import { BACKEND_GR, BACKEND_ORIGIN } from '../../../../../config';

export const HEADER = {
  "Authorization" : `Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMkZDWkcyU1hOWllHUVhNUFBCQjhGVjNaViIsImp0aSI6IjAxRldYNkg2MTZCS1FTVjA5RFZXRkNGOFNCIiwidXNlcm5hbWUiOiJob2FuZ3RsIiwiZW1haWwiOiJob2FuZ3RsQGdtYWlsLmNvbSIsInR5cGUiOiJBQ0NFU1MiLCJyb2xlcyI6WyJBRE1JTiIsIlVTRVIiXSwiaWF0IjoxNjQ1OTUyNDA3LCJleHAiOjE2NDYwNzI0MDd9.M98PRpx-1oYKGfFweFR0O8OzkhAgyWl0GKjmaVYqiMhWhYuKJgxjP01QKIuIfl2wKHer6kUmLuYwYOEtFJextQ`
}

export const TEMP_USER = {
  "id": "02FCZG2SXNZYGQXMPPBB8FV3ZV",
  "username": "hoangtl",
  "email": "hoangtl@gmail.com",
  "first_name": "tran",
  "last_name": "hoang",
  "avatar": "http://127.0.0.1:8080/media/user/01FVJ9311FFK8SNMDQJEJJ5Q7B.png",
  "gender": "FEMALE",
}

// TODO: add query
export const getAllPatients = () => {
  const GET_PATIENTS_INFO = BACKEND_GR + `backend/api/v1/patient`;
  return fetch(GET_PATIENTS_INFO, {
    method: "GET",
    headers: HEADER
  }).then(res => {
    if (!res.ok) {
      return null;
    }
    return res.json()
  });
}

export const getPatientById = (id) => {
  const GET_PATIENTS_INFO = BACKEND_GR + `backend/api/v1/patient/${id}`;
  return fetch(GET_PATIENTS_INFO, {
    method: "GET",
    headers: HEADER
  }).then(res => {
    if (!res.ok) {
      return null;
    }
    return res.json()
  });
}

export const postNewPatient = (formData) => {
  const POST_PATIENT = BACKEND_GR + `backend/api/v1/patient`;
  return fetch(POST_PATIENT, {
    method: "POST",
    headers: {
      "Authorization" : `Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMkZDWkcyU1hOWllHUVhNUFBCQjhGVjNaViIsImp0aSI6IjAxRldYNkg2MTZCS1FTVjA5RFZXRkNGOFNCIiwidXNlcm5hbWUiOiJob2FuZ3RsIiwiZW1haWwiOiJob2FuZ3RsQGdtYWlsLmNvbSIsInR5cGUiOiJBQ0NFU1MiLCJyb2xlcyI6WyJBRE1JTiIsIlVTRVIiXSwiaWF0IjoxNjQ1OTUyNDA3LCJleHAiOjE2NDYwNzI0MDd9.M98PRpx-1oYKGfFweFR0O8OzkhAgyWl0GKjmaVYqiMhWhYuKJgxjP01QKIuIfl2wKHer6kUmLuYwYOEtFJextQ`
    },
    body: formData
  }).then(res => {
    return res.json()
  });
}

// TODO: add query
export const getAllExaminations = () => {
  const GET_EXAMINATION = BACKEND_GR + `backend/api/v1/examination`
  return fetch(GET_EXAMINATION, {
    method: "GET",
    headers: HEADER
  }).then(res => {
    if (!res.ok) {
      return null;
    }
    return res.json()
  });
}

export const diagnose = (data) => {
  const POST_DIAGNOSE = BACKEND_GR + `backend/api/v1/pfs/diagnose`;
  return fetch(POST_DIAGNOSE, {
    method: "POST",
    headers: {
      "Authorization" : `Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMkZDWkcyU1hOWllHUVhNUFBCQjhGVjNaViIsImp0aSI6IjAxRldYNkg2MTZCS1FTVjA5RFZXRkNGOFNCIiwidXNlcm5hbWUiOiJob2FuZ3RsIiwiZW1haWwiOiJob2FuZ3RsQGdtYWlsLmNvbSIsInR5cGUiOiJBQ0NFU1MiLCJyb2xlcyI6WyJBRE1JTiIsIlVTRVIiXSwiaWF0IjoxNjQ1OTUyNDA3LCJleHAiOjE2NDYwNzI0MDd9.M98PRpx-1oYKGfFweFR0O8OzkhAgyWl0GKjmaVYqiMhWhYuKJgxjP01QKIuIfl2wKHer6kUmLuYwYOEtFJextQ`,
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(data)
  }).then(res => {
    return res.json()
  });
}