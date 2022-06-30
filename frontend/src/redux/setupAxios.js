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
      console.log(`[AXIOS rq] config`);
      console.log(config);
      return config;
    },
    err => {
      Promise.reject(err);
    }
  );

  axios.interceptors.response.use(
    response => {
      console.log(`[AXIOS rq] response`);
      console.log(response);
      return response;
    },
    err => {
      console.log(`[AXIOS rq] err`);
      console.log(err.response);

      // Return any error which is not due to authentication back to the calling service
      if (!err.response || err.response.status !== 401) {
        return new Promise((resolve, reject) => {
          reject(err);
        });
      }

      // Logout user if token refresh didn't work or user is disabled
      if (
        err.config.url === `${REFRESH_URL}` || err.response.status === 403
      ) {
        store.dispatch(actions.logout());
        window.location.href = "/auth/login";

        return new Promise((resolve, reject) => {
          reject(err);
        });
      }

      if (err.config.url === `${LOGIN_URL}`) {
        // User send wrong password or username
        return new Promise((resolve, reject) => {
          resolve(err);
        });
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
          "refresh_token": refreshToken
        }, {
          "Authorization": `Bearer ${accessToken}`
        })
        .then(({ data: { data: { accessToken, refreshToken } } }) => {
          store.dispatch(actions.refreshToken(accessToken, refreshToken));
          // originalReq.headers.Authorization = `Bearer ${accessToken}`;
          // return axios(originalReq);
        })
        .catch(err => {
          store.dispatch(actions.logout());
          window.location.href = "/auth/login";
          return new Promise((resolve, reject) => reject(err));
        });
    }
  );
}
