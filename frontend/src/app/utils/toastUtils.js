import {toast} from "react-toastify";

export const toastify = {
  success: (content) => {
    toast.success(content, {
      position: 'top-right',
      autoClose: 5000,
      hideProgressBar: false,
      closeOnClick: true
    });
  },
  error: (content) => {
    toast.error(content, {
      position: 'top-right',
      autoClose: 5000,
      hideProgressBar: false,
      closeOnClick: true
    });
  }
}