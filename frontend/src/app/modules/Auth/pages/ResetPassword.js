import React, {useEffect, useState} from 'react';
import * as Yup from "yup";
import {useFormik} from "formik";
import {requestPassword, resetPassword} from "../_redux/authCrud";
import {Link, Redirect, useParams} from "react-router-dom";
import {injectIntl} from "react-intl";
import {connect} from "react-redux";
import * as auth from "../_redux/authRedux";
import {toastify} from "../../../utils/toastUtils";

const initialValues = {
  newPassword: "",
  newPasswordConfirmation: ""
};

function ResetPassword({ ...props }) {
  const { intl } = props;
  const token = new URLSearchParams(props.location.search).get('token');
  const [isRequested, setIsRequested] = useState(false);
  const ResetPasswordSchema = Yup.object().shape({
    newPassword: Yup.string()
      .min(3, "Minimum 3 symbols")
      .max(50, "Maximum 50 symbols")
      .required(
        intl.formatMessage({
          id: "AUTH.VALIDATION.REQUIRED_FIELD",
        })
      ),
    newPasswordConfirmation: Yup.string()
      .oneOf([Yup.ref('newPassword'), null], 'Passwords must match')
  });

  const getInputClasses = (fieldname) => {
    if (formik.touched[fieldname] && formik.errors[fieldname]) {
      return "is-invalid";
    }

    if (formik.touched[fieldname] && !formik.errors[fieldname]) {
      return "is-valid";
    }

    return "";
  };

  useEffect(() => {
    console.log(token)
  }, [])

  const formik = useFormik({
    initialValues,
    validationSchema: ResetPasswordSchema,
    onSubmit: (values, { setStatus, setSubmitting }) => {
      resetPassword({ token: token, new_password: values.newPassword })
        .then(() => {
          setIsRequested(true)
          toastify.success("Password change succeed")
        })
        .catch(() => {
          setIsRequested(false);
          setSubmitting(false);
          setStatus(
            intl.formatMessage(
              { id: "AUTH.VALIDATION.INVALID_FIELD" }
            )
          );
        });
    },
  });

  return (
    <>
      {isRequested && <Redirect to="/auth" />}
      {!isRequested && (
        <div className="login-form login-forgot" style={{ display: "block" }}>
          <div className="text-center mb-10 mb-lg-20">
            <h3 className="font-size-h1">Reset Password</h3>
            <div className="text-muted font-weight-bold">
              Enter new password and confirmation below to reset your password
            </div>
          </div>
          <form
            onSubmit={formik.handleSubmit}
            className="form fv-plugins-bootstrap fv-plugins-framework animated animate__animated animate__backInUp"
          >
            {formik.status && (
              <div className="mb-10 alert alert-custom alert-light-danger alert-dismissible">
                <div className="alert-text font-weight-bold">
                  {formik.status}
                </div>
              </div>
            )}
            <div className="form-group fv-plugins-icon-container">
              <input
                type="password"
                placeholder="New Password"
                className={`form-control form-control-solid h-auto py-5 px-6 ${getInputClasses(
                  "newPassword"
                )}`}
                name="newPassword"
                {...formik.getFieldProps("newPassword")}
              />
              {formik.touched.newPassword && formik.errors.newPassword ? (
                <div className="fv-plugins-message-container">
                  <div className="fv-help-block">{formik.errors.newPassword}</div>
                </div>
              ) : null}
            </div>
            <div className="form-group fv-plugins-icon-container">
              <input
                type="password"
                placeholder="New Password Confirmation"
                className={`form-control form-control-solid h-auto py-5 px-6 ${getInputClasses(
                  "newPasswordConfirmation"
                )}`}
                name="newPasswordConfirmation"
                {...formik.getFieldProps("newPasswordConfirmation")}
              />
              {formik.touched.newPasswordConfirmation && formik.errors.newPasswordConfirmation ? (
                <div className="fv-plugins-message-container">
                  <div className="fv-help-block">{formik.errors.newPasswordConfirmation}</div>
                </div>
              ) : null}
            </div>
            <div className="form-group d-flex flex-wrap flex-center">
              <button
                id="kt_login_forgot_submit"
                type="submit"
                className="btn btn-primary font-weight-bold px-9 py-4 my-3 mx-4"
                disabled={formik.isSubmitting}
              >
                Submit
              </button>
              <Link to="/auth">
                <button
                  type="button"
                  id="kt_login_forgot_cancel"
                  className="btn btn-light-primary font-weight-bold px-9 py-4 my-3 mx-4"
                >
                  Cancel
                </button>
              </Link>
            </div>
          </form>
        </div>
      )}
    </>
  );
}

export default injectIntl(connect(null, auth.actions)(ResetPassword));