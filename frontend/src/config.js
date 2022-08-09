export const BACKEND_ORIGIN = process.env.REACT_APP_BACKEND_ORIGIN ? process.env.REACT_APP_BACKEND_ORIGIN : "http://127.0.0.1:8080/backend/api/v1/";
export const REACT_ENV = process.env.REACT_APP_ENV ? process.env.REACT_APP_ENV : "DEVELOP"

console.log(BACKEND_ORIGIN);
console.log(REACT_ENV);

export const LOGIN_URL = BACKEND_ORIGIN + 'login';
export const ME_URL = BACKEND_ORIGIN + 'user/me';
export const REFRESH_URL = BACKEND_ORIGIN + 'refresh-token'
export const REQUEST_PASSWORD_URL = BACKEND_ORIGIN + 'forgot-password';
export const RESET_PASSWORD_URL = BACKEND_ORIGIN + 'reset-password'

// old
export const REGISTER_URL = BACKEND_ORIGIN + 'api/v1/auth/register';

