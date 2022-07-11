import React, { useEffect, useState } from "react";
import { Modal } from "react-bootstrap";
import {
  Input,
  Select,
  DatePickerField, ModalProgressBar
} from "../../../../_metronic/_partials/controls";
import { Formik, Form, Field } from "formik";
import * as Yup from "yup";
import { createNewPatient } from "../_redux/patientCrud";
import ImageThumb from "../../../utils/ImageThumb";
import {toastify} from "../../../utils/toastUtils";

function PatientCreateDialog({ show, onHide }) {

  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {

  }, []);

  // server request for saving customer
  const savePatient = (patient) => {
    console.log(patient);
    // setIsLoading(true);
    createNewPatient(patient)
      .then(() => {
        console.log("create new patient ok");
        // setIsLoading(false);
        toastify.success('Create new patient success!');
        onHide();
      })
      .catch(error => {
        // setIsLoading(false);
        console.log("Error get all patients: " + error);
        alert("Cannot get patients");
      })
  };

  // Validation schema
  const PatientEditSchema = Yup.object().shape({
    id: Yup.string()
      // .min(8, "Minimum 8 chars")
      .max(20, "Maximum 20 chars"),
    name: Yup.string()
      .min(3, "Minimum 3 symbols")
      .max(50, "Maximum 50 symbols")
      .required("Firstname is required"),
    phoneNo: Yup.string()
      .matches(/^\d+$/),
    email: Yup.string()
      .email("Invalid email")
      .required("Email is required"),
    address: Yup.string()
      .max(150, "Maximum 150 chars"),
    // gender: Yup.string().require(),
    birthDate: Yup.mixed()
      .nullable(false)
      .required("Date of Birth is required"),
    avatar: Yup.mixed()
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
        <Modal.Title id="example-modal-sizes-title-lg">{`Create new patient`}</Modal.Title>
      </Modal.Header>
      <Formik
        validationSchema={PatientEditSchema}
        onSubmit={(values) => savePatient(values)}
        initialValues={{
          id: "",
          name: "",
          phoneNo: "",
          email: "",
          address: "",
          birthDate: '01/01/1970',
          gender: "female",
          avatar: null
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
                      name="id"
                      component={Input}
                      placeholder="Patient ID"
                      label="Patient ID"
                    />
                  </div>
                  {/* Full Name */}
                  <div className="col-lg-6">
                    <Field
                      name="name"
                      component={Input}
                      placeholder="Full Name"
                      label="Full Name"
                    />
                  </div>
                </div>
                {/* Email */}
                <div className="form-group row">
                  <div className="col-lg-6">
                    <Field
                      name="address"
                      component={Input}
                      placeholder="Address"
                      label="Address"
                    />
                  </div>
                  <div className="col-lg-6">
                    <Field
                      name="phoneNo"
                      component={Input}
                      placeholder="Phone number"
                      label="Phone number"
                    />
                  </div>
                </div>
                <div className="form-group row">
                  {/* Gender */}
                  <div className="col-lg-6">
                    <Select name="Gender" label="Gender">
                      <option value="Female">Female</option>
                      <option value="Male">Male</option>
                    </Select>
                  </div>
                  {/* Date of birth */}
                  <div className="col-lg-6">
                    <DatePickerField
                      name="birthDate"
                      label="Date of Birth"
                    />
                  </div>
                </div>
                <div className="form-group row">
                  <div className="col-lg-6">
                    <Field
                      type="email"
                      name="email"
                      component={Input}
                      placeholder="Email"
                      label="Email"
                    />
                  </div>
                  <div className="col-lg-6">
                    <label>Patient image</label>
                    <input
                      type="file"
                      className="form-control"
                      placeholder="Patient image"
                      onChange={(e) => {
                        setFieldValue('avatar', e.currentTarget.files[0])
                      }}
                    />
                    <ImageThumb file={values.avatar}/>
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

export default PatientCreateDialog;