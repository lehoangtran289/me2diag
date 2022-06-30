import { persistReducer } from "redux-persist";
import storage from "redux-persist/lib/storage";
import { put, takeLatest } from "redux-saga/effects";
import { getUserByToken } from "./authCrud";

export const actionTypes = {
  Login: "[Login] Action",
  Logout: "[Logout] Action",
  Register: "[Register] Action",
  UserRequested: "[Request User] Action",
  UserLoaded: "[Load User] Auth API",
  SetUser: "[Set User] Action",
  refreshToken: "[Refresh Auth] Action"
};

const initialAuthState = {
  user: undefined,
  accessToken: undefined,
  refreshToken: undefined,
  payload: undefined
};

export const reducer = persistReducer(
  { storage, key: "v713-demo1-auth", whitelist: ["user", "accessToken"] },
  (state = initialAuthState, action) => {
    switch (action.type) {
      case actionTypes.Login: {
        const { accessToken, refreshToken, payload } = action.payload;

        return { ...state, accessToken, refreshToken, payload, user: undefined };
      }

      case actionTypes.Register: {
        const { accessToken } = action.payload;

        return { accessToken, user: undefined };
      }

      case actionTypes.Logout: {
        // TODO: Change this code. Actions in reducer aren't allowed.
        return initialAuthState;
      }

      case actionTypes.UserLoaded: {
        const { user } = action.payload;
        return { ...state, user };
      }

      case actionTypes.SetUser: {
        const { user } = action.payload;
        return { ...state, user };
      }

      default:
        return state;
    }
  }
);

export const actions = {
  login: ({ accessToken, refreshToken, payload }) => ({
    type: actionTypes.Login,
    payload: { accessToken, refreshToken, payload }
  }),
  register: (accessToken) => ({
    type: actionTypes.Register,
    payload: { accessToken }
  }),
  logout: () => ({ type: actionTypes.Logout }),
  requestUser: (user) => ({
    type: actionTypes.UserRequested,
    payload: { user }
  }),
  fulfillUser: (user) => ({ type: actionTypes.UserLoaded, payload: { user } }),
  setUser: (user) => ({ type: actionTypes.SetUser, payload: { user } }),
  refreshToken: (accessToken, refreshToken) => ({
    type: actionTypes.refreshToken,
    payload: { accessToken, refreshToken }
  })
};

export function* saga() {
  // Login action -> requestUser action ~ getUserByToken() -> UserLoaded action
  yield takeLatest(actionTypes.Login, function* loginSaga() {
    yield put(actions.requestUser());
  });

  yield takeLatest(actionTypes.Register, function* registerSaga() {
    yield put(actions.requestUser());
  });

  yield takeLatest(actionTypes.UserRequested, function* userRequested() {
    const { data: { data: user } } = yield getUserByToken();

    yield put(actions.fulfillUser(user));
  });
}
