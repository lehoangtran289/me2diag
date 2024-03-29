import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { useSelector, shallowEqual, connect, useDispatch } from "react-redux";
import { useFormik } from "formik";
import * as Yup from "yup";
import { ModalProgressBar } from "../../../_metronic/_partials/controls";
import { toAbsoluteUrl } from "../../../_metronic/_helpers";
import * as auth from "../Auth";
import {updateUser} from "./axios/UserCrud";
import {toastify} from "../../utils/toastUtils";

function PersonaInformation(props) {
  // Fields
  const [loading, setloading] = useState(false);
  const [pic, setPic] = useState("");
  const dispatch = useDispatch();
  const user = useSelector((state) => state.auth.user, shallowEqual);

  useEffect(() => {
    if (user.avatarUrl) {
      setPic(user.avatarUrl);
    }
  }, [user]);

  // Methods
  const saveUser = (values, setStatus, setSubmitting) => {
    console.log(values);
    setloading(true);
    const updatedUser = Object.assign(user, values);

    // update then setState -> user for update preparation
    updateUser({
      id: user.id,
      updatedUser: updatedUser
    }).then(r => {
      console.log(r);
      setloading(false);
      setSubmitting(false);
      dispatch(props.setUser(r.data.data));
      toastify.success('Update user success');
    }).catch(err => {
      console.log(err);
      setloading(false);
      setSubmitting(false);
      toastify.error('Update user failed');
    })
  };
  // UI Helpers
  const initialValues = {
    avatarUrl: user.avatarUrl ? user.avatarUrl : null,
    firstName: user.firstName,
    lastName: user.lastName,
    phoneNo: user.phoneNo,
    email: user.email,
    description: user.description,
  };
  const Schema = Yup.object().shape({
    avatarUrl: Yup.mixed(),
    firstName: Yup.string().required("First name is required"),
    lastName: Yup.string().required("Last name is required"),
    phoneNo: Yup.string().required("Phone is required"),
    email: Yup.string()
      .email("Wrong email format")
      .required("Email is required"),
    description: Yup.string(),
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
    onSubmit: (values, { setStatus, setSubmitting }) => {
      console.log("submit update user info")
      saveUser(values, setStatus, setSubmitting);
    },
    onReset: (values, { resetForm }) => {
      resetForm();
    },
  });
  const getUserPic = () => {
    if (!pic) {
      return "none";
    }
    return `url(${pic})`;
  };
  const removePic = () => {
    setPic("");
  };
  return (
    <form
      className="card card-custom card-stretch"
      onSubmit={formik.handleSubmit}
    >
      {loading && <ModalProgressBar />}

      {/* begin::Header */}
      <div className="card-header py-3">
        <div className="card-title align-items-start flex-column">
          <h3 className="card-label font-weight-bolder text-dark">
            Personal Information
          </h3>
          <span className="text-muted font-weight-bold font-size-sm mt-1">
            Update your personal information
          </span>
        </div>
        <div className="card-toolbar">
          <button
            type="submit"
            className="btn btn-success mr-2"
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
        {/* begin::Body */}
        <div className="card-body">
          <div className="row">
            <label className="col-xl-3"></label>
            <div className="col-lg-9 col-xl-6">
              <h5 className="font-weight-bold mb-6">Personal Info</h5>
            </div>
          </div>
          <div className="form-group row">
            <label className="col-xl-3 col-lg-3 col-form-label">Avatar</label>
            <div className="col-lg-9 col-xl-6">
              {/*TODO: user's avatar*/}
              <div
                className="image-input image-input-outline"
                id="kt_profile_avatar"
                style={{
                  backgroundImage: `url(${toAbsoluteUrl(
                    "/media/users/blank.png"
                  )}`,
                }}
              >
                <div
                  className="image-input-wrapper"
                  style={{ backgroundImage: `${getUserPic()}` }}
                />
                <label
                  className="btn btn-xs btn-icon btn-circle btn-white btn-hover-text-primary btn-shadow"
                  data-action="change"
                  data-toggle="tooltip"
                  title=""
                  data-original-title="Change avatar"
                >
                  <i className="fa fa-pen icon-sm text-muted"></i>
                  <input
                    type="file"
                    name="pic"
                    accept=".png, .jpg, .jpeg"
                    onChange={(e) => {
                      setPic(URL.createObjectURL(e.currentTarget.files[0]))
                      formik.setFieldValue('avatarUrl', e.currentTarget.files[0]);
                    }}
                  />
                  <input type="hidden" name="profile_avatar_remove" />
                </label>
                <span
                  className="btn btn-xs btn-icon btn-circle btn-white btn-hover-text-primary btn-shadow"
                  data-action="cancel"
                  data-toggle="tooltip"
                  title=""
                  data-original-title="Cancel avatar"
                >
                  <i className="ki ki-bold-close icon-xs text-muted"></i>
                </span>
                <span
                  onClick={removePic}
                  className="btn btn-xs btn-icon btn-circle btn-white btn-hover-text-primary btn-shadow"
                  data-action="remove"
                  data-toggle="tooltip"
                  title=""
                  data-original-title="Remove avatar"
                >
                  <i className="ki ki-bold-close icon-xs text-muted"></i>
                </span>
              </div>
              <span className="form-text text-muted">
                Allowed file types: png, jpg, jpeg.
              </span>
            </div>
          </div>
          <div className="form-group row">
            <label className="col-xl-3 col-lg-3 col-form-label">
              First Name
            </label>
            <div className="col-lg-9 col-xl-6">
              <input
                type="text"
                placeholder="First name"
                className={`form-control form-control-lg form-control-solid ${getInputClasses(
                  "firstName"
                )}`}
                name="firstName"
                {...formik.getFieldProps("firstName")}
              />
              {formik.touched.firstName && formik.errors.firstName ? (
                <div className="invalid-feedback">
                  {formik.errors.firstName}
                </div>
              ) : null}
            </div>
          </div>
          <div className="form-group row">
            <label className="col-xl-3 col-lg-3 col-form-label">
              Last Name
            </label>
            <div className="col-lg-9 col-xl-6">
              <input
                type="text"
                placeholder="Last name"
                className={`form-control form-control-lg form-control-solid ${getInputClasses(
                  "lastName"
                )}`}
                name="lastName"
                {...formik.getFieldProps("lastName")}
              />
              {formik.touched.lastName && formik.errors.lastName ? (
                <div className="invalid-feedback">{formik.errors.lastName}</div>
              ) : null}
            </div>
          </div>
          {/*<div className="form-group row">*/}
          {/*  <label className="col-xl-3 col-lg-3 col-form-label">*/}
          {/*    Company Name*/}
          {/*  </label>*/}
          {/*  <div className="col-lg-9 col-xl-6">*/}
          {/*    <input*/}
          {/*      type="text"*/}
          {/*      placeholder="Company name"*/}
          {/*      className={`form-control form-control-lg form-control-solid`}*/}
          {/*      name="companyName"*/}
          {/*      {...formik.getFieldProps("companyName")}*/}
          {/*    />*/}
          {/*    <span className="form-text text-muted">*/}
          {/*      If you want your invoices addressed to a company. Leave blank to*/}
          {/*      use your full name.*/}
          {/*    </span>*/}
          {/*  </div>*/}
          {/*</div>*/}
          <div className="row">
            <label className="col-xl-3"></label>
            <div className="col-lg-9 col-xl-6">
              <h5 className="font-weight-bold mt-10 mb-6">Contact Info</h5>
            </div>
          </div>
          <div className="form-group row">
            <label className="col-xl-3 col-lg-3 col-form-label">
              Contact Phone
            </label>
            <div className="col-lg-9 col-xl-6">
              <div className="input-group input-group-lg input-group-solid">
                <div className="input-group-prepend">
                  <span className="input-group-text">
                    <i className="fa fa-phone"></i>
                  </span>
                </div>
                <input
                  type="text"
                  placeholder="+1(123)112-11-11"
                  className={`form-control form-control-lg form-control-solid ${getInputClasses(
                    "phoneNo"
                  )}`}
                  name="phoneNo"
                  {...formik.getFieldProps("phoneNo")}
                />
              </div>
              {formik.touched.phoneNo && formik.errors.phoneNo ? (
                <div className="invalid-feedback display-block">
                  {formik.errors.phoneNo}
                </div>
              ) : null}
              <span className="form-text text-muted">
                The system never shares phone number with anyone.
              </span>
            </div>
          </div>
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
                  type="email"
                  placeholder="Email"
                  className={`form-control form-control-lg form-control-solid ${getInputClasses(
                    "email"
                  )}`}
                  name="email"
                  {...formik.getFieldProps("email")}
                />
              </div>
              {formik.touched.email && formik.errors.email ? (
                <div className="invalid-feedback display-block">
                  {formik.errors.email}
                </div>
              ) : null}
            </div>
          </div>
          <div className="form-group row">
            <label className="col-xl-3 col-lg-3 col-form-label">
              Description
            </label>
            <div className="col-lg-9 col-xl-6">
              <input
                type="text"
                placeholder="description"
                className={`form-control form-control-lg form-control-solid ${getInputClasses(
                  "description"
                )}`}
                name="description"
                {...formik.getFieldProps("description")}
              />
              {formik.touched.description && formik.errors.description ? (
                <div className="invalid-feedback">
                  {formik.errors.description}
                </div>
              ) : null}
            </div>
          </div>
          {/*<div className="form-group row">*/}
          {/*  <label className="col-xl-3 col-lg-3 col-form-label">*/}
          {/*    Company Site*/}
          {/*  </label>*/}
          {/*  <div className="col-lg-9 col-xl-6">*/}
          {/*    <div className="input-group input-group-lg input-group-solid">*/}
          {/*      <input*/}
          {/*        type="text"*/}
          {/*        placeholder="https://keenthemes.com"*/}
          {/*        className={`form-control form-control-lg form-control-solid`}*/}
          {/*        name="website"*/}
          {/*        {...formik.getFieldProps("website")}*/}
          {/*      />*/}
          {/*    </div>*/}
          {/*    {formik.touched.website && formik.errors.website ? (*/}
          {/*      <div className="invalid-feedback display-block">*/}
          {/*        {formik.errors.website}*/}
          {/*      </div>*/}
          {/*    ) : null}*/}
          {/*  </div>*/}
          {/*</div>*/}
        </div>
        {/* end::Body */}
      </div>
      {/* end::Form */}
    </form>
  );
}

export default connect(null, auth.actions)(PersonaInformation);
