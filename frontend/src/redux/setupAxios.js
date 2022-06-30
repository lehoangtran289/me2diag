// export default function setupAxios(axios, store) {
//   axios.interceptors.request.use(
//     config => {
//       const {
//         auth: { authToken }
//       } = store.getState();
//
//       if (authToken) {
//         config.headers.Authorization = `Bearer ${authToken}`;
//       }
//
//       return config;
//     },
//     err => Promise.reject(err)
//   );
// }

import { actions } from "../app/modules/Auth";
import { LOGIN_URL, REFRESH_URL } from "../config";

export default function setupAxios(axios, store) {
  axios.interceptors.request.use(
    config => {
      const { auth: { accessToken } } = store.getState();

      if (accessToken) {
        config.headers.Authorization = `Bearer ${accessToken}`;
      }

      return config;
    },
    err => {
      Promise.reject(err);
    }
  );

  axios.interceptors.response.use(
    response => {
      return response;
    },
    err => {
      // Return any error which is not due to authentication back to the calling service
      if (!err.response || err.response.status !== 401) {
        return new Promise((resolve, reject) => {
          reject(err);
        });
      }

      // Logout user if token refresh didn't work or user is disabled
      if (
        err.config.url === `${REFRESH_URL}`
      ) {
        store.dispatch(actions.logout());
        window.location.href = "/auth/login";

        return new Promise((resolve, reject) => {
          reject(err);
        });
      }

      if(err.config.url === `${LOGIN_URL}`){
        // User send wrong password or username
        return new Promise((resolve, reject) => {
          resolve(err);
        })
      }

      // Try request again with new token
      const originalReq = err.config;
      if (!err.config || err.config._retry) {
        return new Promise((resolve, reject) => {
          reject(err);
        });
      }

      const {
        auth: { accessToken, refreshToken }
      } = store.getState();
      originalReq._retry = true;

      return axios
        .post(`${REFRESH_URL}`, {
          "access_token": accessToken,
          "refresh_token": refreshToken
        })
        .then(res => {
          store.dispatch(actions.refreshToken(res.data.accessToken));
          originalReq.headers.Authorization = `Bearer ${res.data.accessToken}`;
          return axios(originalReq);
        })
        .catch(err => {
          store.dispatch(actions.logout());
          window.location.href = "/auth/login";
          return new Promise((resolve, reject) => reject(err));
        });
    }
  );
}
