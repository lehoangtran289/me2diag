export const BACKEND_ORIGIN = process.env.REACT_APP_BACKEND_ORIGIN ? process.env.REACT_APP_BACKEND_ORIGIN : "http://127.0.0.1:8000/";
export const BACKEND_ORIGIN2 = process.env.REACT_APP_BACKEND_ORIGIN ? process.env.REACT_APP_BACKEND_ORIGIN : "http://127.0.0.1:8000";
export const BACKEND_GR = process.env.REACT_APP_BACKEND_ORIGIN ? process.env.REACT_APP_BACKEND_ORIGIN : "http://127.0.0.1:8080/";

console.log(BACKEND_ORIGIN);

export const LOGIN_URL = BACKEND_ORIGIN + 'api/v1/auth/login';
export const REGISTER_URL = BACKEND_ORIGIN + 'api/v1/auth/register';
export const REQUEST_PASSWORD_URL = BACKEND_ORIGIN + 'api/v1/auth/forgot-password';
export const ME_URL = BACKEND_ORIGIN + 'api/v1/me';

export const WORKSPACE_VISIBILITY = [
  'private',
  'public'
]

