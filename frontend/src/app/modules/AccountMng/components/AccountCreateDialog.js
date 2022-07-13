import React, { useEffect, useState } from "react";
import { toastify } from "../../../utils/toastUtils";
import * as Yup from "yup";
import { Modal } from "react-bootstrap";
import { Field, Form, Formik } from "formik";
import { Input, Select } from "../../../../_metronic/_partials/controls";
import { createNewAccount } from "../_redux/accountCrud";

function AccountCreateDialog({ show, onHide }) {
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {

  }, []);

  // server request for saving customer
  const saveAccount = (account) => {
    console.log(account);
    setIsLoading(true);
    createNewAccount(account)
      .then(() => {
        console.log("create new account ok");
        // setIsLoading(false);
        toastify.success('Create new account success!');
        onHide();
      })
      .catch(error => {
        // setIsLoading(false);
        console.log("Error get all accounts: " + error);
        toastify.error("Cannot create new account");
      })
  };

  // Validation schema
  const AccountSchema = Yup.object().shape({
    username: Yup.string()
      .min(3, "Minimum 3 symbols")
      .max(255, "Maximum 50 symbols")
      .required("Firstname is required"),
    email: Yup.string()
      .email("Invalid email")
      .required("Email is required"),
    password: Yup.string()
      .max(150, "Maximum 150 chars"),
    first_name: Yup.string()
      .max(150, "Maximum 150 chars"),
    last_name: Yup.string()
      .max(150, "Maximum 150 chars"),
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
        <Modal.Title id="example-modal-sizes-title-lg">{`Create new account`}</Modal.Title>
      </Modal.Header>
      <Formik
        validationSchema={AccountSchema}
        onSubmit={(values) => saveAccount(values)}
        initialValues={{
          username: "",
          email: "",
          password: "",
          first_name: "",
          last_name: "",
          gender: "female",
          roles: []
        }}
        enableReinitialize={true}
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
                      placeholder="Account username"
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
                {/* Email */}
                <div className="form-group row">
                  <div className="col-lg-6">
                    <Field
                      name="first_name"
                      component={Input}
                      placeholder="First Name"
                      label="First Name"
                    />
                  </div>
                  <div className="col-lg-6">
                    <Field
                      name="last_name"
                      component={Input}
                      placeholder="Last Name"
                      label="Last Name"
                    />
                  </div>
                </div>
                <div className="form-group row">
                  <div className="col-lg-6">
                    <Field
                      name="password"
                      component={Input}
                      placeholder="Password"
                      label="Password"
                    />
                  </div>
                  <div className="col-lg-6">
                    <Select name="gender" label="Gender" >
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

export default AccountCreateDialog;