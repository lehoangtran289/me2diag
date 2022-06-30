export const BACKEND_ORIGIN = process.env.REACT_APP_BACKEND_ORIGIN ? process.env.REACT_APP_BACKEND_ORIGIN : "http://127.0.0.1:8080/backend/api/v1/";

console.log(BACKEND_ORIGIN);

export const LOGIN_URL = BACKEND_ORIGIN + 'login';
export const ME_URL = BACKEND_ORIGIN + 'user/me';
export const REFRESH_URL = BACKEND_ORIGIN + 'refresh-token'

// TODO
// ROLES map

// old
export const REQUEST_PASSWORD_URL = BACKEND_ORIGIN + 'api/v1/auth/forgot-password';
export const REGISTER_URL = BACKEND_ORIGIN + 'api/v1/auth/register';

