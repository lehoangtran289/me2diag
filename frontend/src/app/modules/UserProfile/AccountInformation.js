/* eslint-disable jsx-a11y/anchor-is-valid */
import React, {useState, useEffect} from "react";
import {Link, useHistory} from "react-router-dom";
import {useSelector, shallowEqual, connect, useDispatch} from "react-redux";
import {useFormik} from "formik";
import * as Yup from "yup";
import {ModalProgressBar} from "../../../_metronic/_partials/controls";
import * as auth from "../Auth";
import {deactivateAccount, updateUser} from "./axios/UserCrud";
import {toastify} from "../../utils/toastUtils";
import {Button, Modal} from "react-bootstrap";

function AccountInformation(props) {
  // Fields
  const dispatch = useDispatch();
  const history = useHistory();
  const user = useSelector((state) => state.auth.user, shallowEqual);
  const [loading, setloading] = useState(false);

  useEffect(() => {
  }, [user]);

  // method
  const saveUser = (values, setStatus, setSubmitting) => {
    console.log(values);
    setloading(true);

    // update then setState -> user for update preparation
    updateUser({
      id: user.id,
      updatedUser: {
        username: values.username,
        email: values.email,
      }
    }).then(r => {
      console.log(r);
      setloading(false);
      setSubmitting(false);
      dispatch(props.setUser(r.data.data));
      toastify.success('Update account info success');
    }).catch(err => {
      console.log(err);
      setloading(false);
      setSubmitting(false);
      toastify.error('Update account info failed');
    })
  };

  // UI Helpers
  const initialValues = {
    username: user.username,
    email: user.email,
  };
  const Schema = Yup.object().shape({
    username: Yup.string().required("Username is required"),
    email: Yup.string()
      .email("Wrong email format")
      .required("Email is required"),
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
  const formik = useFormik({
    initialValues,
    validationSchema: Schema,
    onSubmit: (values, {setStatus, setSubmitting}) => {
      saveUser(values, setStatus, setSubmitting);
    },
    onReset: (values, {resetForm}) => {
      resetForm();
    },
  });

  // Deactivate user confirmation modal state
  const [confirmModalOpen, setConfirmModalOpen] = useState(false);
  const openConfirmationModal = () => {
    setConfirmModalOpen(true);
  };
  const closeConfirmationModal = () => {
    setConfirmModalOpen(false);
  }
  const handleConfirmationModalSave = () => {
    setConfirmModalOpen(false);
    deactivateAccount(user.id)
      .then(r => {
        console.log(r);
        toastify.success("Deactivate account success");
        history.push("/logout");
      })
      .catch(err => {
        console.log(err);
        toastify.error("Deactivate account failed");
      })
  };

  return (
    <>
      <form className="card card-custom" onSubmit={formik.handleSubmit}>
        {loading && <ModalProgressBar/>}

        {/* begin::Header */}
        <div className="card-header py-3">
          <div className="card-title align-items-start flex-column">
            <h3 className="card-label font-weight-bolder text-dark">
              Account Information
            </h3>
            <span className="text-muted font-weight-bold font-size-sm mt-1">
            Change your account settings
          </span>
          </div>
          <div className="card-toolbar">
            <button
              type="submit"
              className="btn btn-success mr-2"
              disabled={
                formik.isSubmitting || (formik.touched && !formik.isValid)
              }
            >
              Save Changes
              {formik.isSubmitting}
            </button>
            <Link
              to="/user-profile/"
              className="btn btn-secondary"
            >
              Cancel
            </Link>
          </div>
        </div>
        {/* end::Header */}
        {/* begin::Form */}
        <div className="form">
          <div className="card-body">
            {/* begin::Heading */}
            <div className="row">
              <label className="col-xl-3"></label>
              <div className="col-lg-9 col-xl-6">
                <h5 className="font-weight-bold mb-6">Account:</h5>
              </div>
            </div>
            {/* begin::Form Group */}
            <div className="form-group row">
              <label className="col-xl-3 col-lg-3 col-form-label">Username</label>
              <div className="col-lg-9 col-xl-6">
                <div>
                  <input
                    type="text"
                    className={`form-control form-control-lg form-control-solid ${getInputClasses(
                      "username"
                    )}`}
                    name="username"
                    {...formik.getFieldProps("username")}
                  />
                  {formik.touched.username && formik.errors.username ? (
                    <div className="invalid-feedback">
                      {formik.errors.username}
                    </div>
                  ) : null}
                </div>
              </div>
            </div>
            {/* begin::Form Group */}
            <div className="form-group row">
              <label className="col-xl-3 col-lg-3 col-form-label">
                Email Address
              </label>
              <div className="col-lg-9 col-xl-6">
                <div className="input-group input-group-lg input-group-solid">
                  <div className="input-group-prepend">
                  <span className="input-group-text">
                    <i className="fa fa-at"></i>
                  </span>
                  </div>
                  <input
                    type="text"
                    placeholder="Email"
                    className={`form-control form-control-lg form-control-solid ${getInputClasses(
                      "email"
                    )}`}
                    name="email"
                    {...formik.getFieldProps("email")}
                  />
                  {formik.touched.email && formik.errors.email ? (
                    <div className="invalid-feedback">{formik.errors.email}</div>
                  ) : null}
                </div>
                <span className="form-text text-muted">
                Email will not be publicly displayed.{` `}
                  <a href="#" className="font-weight-bold">
                  Learn more
                </a>
                .
              </span>
              </div>
            </div>
            <div className="separator separator-dashed my-5"></div>
            {/* begin::Form Group */}
            <div className="row">
              <label className="col-xl-3"></label>
              <div className="col-lg-9 col-xl-6">
                <h5 className="font-weight-bold mb-6">Security:</h5>
              </div>
            </div>
            <div className="form-group row">
              <label className="col-xl-3 col-lg-3 col-form-label">
                Password reset verification
              </label>
              <div className="col-lg-9 col-xl-6">
                <div className="checkbox-inline">
                  <label className="checkbox m-0">
                    <input type="checkbox"/>
                    <span></span>Require personal information to reset your
                    password.
                  </label>
                </div>
                <p className="form-text text-muted py-2">
                  For extra security, this requires you to confirm your email or
                  phone number when you reset your password.
                  <a href="#" className="font-weight-boldk">
                    Learn more
                  </a>
                  .
                </p>
                <button
                  type="button"
                  className="btn btn-light-danger font-weight-bold btn-sm"
                  onClick={openConfirmationModal}
                >
                  Deactivate your account ?
                </button>
              </div>
            </div>
          </div>
        </div>
        {/* end::Form */}
      </form>
      <Modal show={confirmModalOpen} onHide={closeConfirmationModal}>
        <Modal.Header closeButton>
          <Modal.Title>Account deactivation</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <div>
            Are you sure to deactivate your account and log out?
          </div>
          <div>
            Note: You can contact administrator for re-activating your account.
          </div>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={closeConfirmationModal}>
            Close
          </Button>
          <Button variant="danger" onClick={handleConfirmationModalSave}>
            Deactivate
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default connect(null, auth.actions)(AccountInformation);
