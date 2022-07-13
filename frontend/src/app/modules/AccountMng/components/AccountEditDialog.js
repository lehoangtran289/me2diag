import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Modal } from "react-bootstrap";
import { Field, Form, Formik } from "formik";
import { Input, Select } from "../../../../_metronic/_partials/controls";
import { toastify } from "../../../utils/toastUtils";
import * as Yup from "yup";
import { editAccountDetail } from "../_redux/accountCrud";

function AccountEditDialog({ show, onHide, ...props }) {

  const { userId } = useParams();
  const [user, setUser] = useState({});

  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    if (props.location.state.account) {
      setUser(props.location.state.account);
    }
    else {
      // TODO: get account info from server
    }
  }, []);

  useEffect(() => {
    console.log(user);
  }, [user])

  // server request for saving customer
  const saveAccount = (account) => {
    console.log(account);
    // setIsLoading(true);
    editAccountDetail(userId, account)
      .then(() => {
        console.log("Edit account detail ok");
        // setIsLoading(false);
        toastify.success('Edit account detail success!');
        onHide();
      })
      .catch(error => {
        // setIsLoading(false);
        console.log("Error edit account detail: " + error);
        toastify.error("Cannot edit account detail");
      })
  };

  // Validation schema
  const AccountEditSchema = Yup.object().shape({
    username: Yup.string()
      .min(3, "Minimum 3 symbols")
      .max(50, "Maximum 50 symbols"),
    firstName: Yup.string()
      .min(3, "Minimum 3 symbols")
      .max(50, "Maximum 50 symbols"),
    lastName: Yup.string()
      .min(3, "Minimum 3 symbols")
      .max(50, "Maximum 50 symbols"),
    phoneNo: Yup.string()
      .matches(/^\d+$/),
    email: Yup.string()
      .email("Invalid email")
      .required("Email is required"),
    gender: Yup.string(),
    roles: Yup.mixed()
  });

  return (
    <Modal
      size="lg"
      show={show}
      onHide={onHide}
      aria-labelledby="example-modal-sizes-title-lg"
    >
      {/*{isLoading && <ModalProgressBar />}*/}
      <Modal.Header closeButton>
        <Modal.Title id="example-modal-sizes-title-lg">{`Edit account details`}</Modal.Title>
      </Modal.Header>
      <Formik
        validationSchema={AccountEditSchema}
        onSubmit={(values) => saveAccount(values)}
        initialValues={{
          username: user.username ? user.username : "",
          firstName: user.firstName ? user.firstName : "",
          lastName: user.lastName ? user.lastName : "",
          email: user.email ? user.email : "",
          phoneNo: user.phoneNo ? user.phoneNo : "",
          gender: user.gender ? user.gender : "female",
          roles: user.roles ? user.roles : []
        }}
        enableReinitialize={true}
        // initialValues={customer}
      >
        {({
            handleSubmit,
            handleChange,
            handleBlur,
            values,
            touched,
            isValid,
            errors,
            setFieldValue
          }) => (
          <>
            <Modal.Body className="overlay overlay-block cursor-default">
              {isLoading && (
                <div className="overlay-layer bg-transparent">
                  <div className="spinner spinner-lg spinner-success" />
                </div>
              )}
              <Form className="form form-label-right">
                <div className="form-group row">
                  <div className="col-lg-6">
                    <Field
                      name="username"
                      component={Input}
                      placeholder="Username"
                      label="Username"
                    />
                  </div>
                  {/* Full Name */}
                  <div className="col-lg-6">
                    <Field
                      name="email"
                      component={Input}
                      placeholder="Email"
                      label="Email"
                    />
                  </div>
                </div>
                <div className="form-group row">
                  <div className="col-lg-6">
                    <Field
                      name="firstName"
                      component={Input}
                      placeholder="First name"
                      label="First name"
                    />
                  </div>
                  <div className="col-lg-6">
                    <Field
                      name="lastName"
                      component={Input}
                      placeholder="Last name"
                      label="Last name"
                    />
                  </div>
                </div>
                <div className="form-group row">
                  <div className="col-lg-6">
                    <Field
                      name="phoneNo"
                      component={Input}
                      placeholder="Phone number"
                      label="Phone number"
                    />
                  </div>
                  {/* Gender */}
                  <div className="col-lg-6">
                    <Select name="gender" label="Gender">
                      <option value="female">Female</option>
                      <option value="male">Male</option>
                    </Select>
                  </div>
                </div>
                <div className="form-group row">
                  <div className="col-lg-6">
                    <Select name="roles" label="Roles" multiple>
                      <option value="USER">USER</option>
                      <option value="EXPERT">EXPERT</option>
                      <option value="ADMIN">ADMIN</option>
                    </Select>
                  </div>
                </div>
              </Form>
            </Modal.Body>
            <Modal.Footer>
              <button
                type="button"
                onClick={onHide}
                className="btn btn-light btn-elevate"
              >
                Cancel
              </button>
              <> </>
              <button
                type="submit"
                onClick={() => handleSubmit()}
                className="btn btn-primary btn-elevate"
              >
                Save
              </button>
            </Modal.Footer>
          </>
        )}
      </Formik>
    </Modal>
  );
}

export default AccountEditDialog;