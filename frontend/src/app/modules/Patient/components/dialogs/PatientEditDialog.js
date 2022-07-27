import React from "react";
import { useEffect, useState } from "react";
import { createNewPatient, editPatient, getPatientDetail } from "../../_redux/patientCrud";
import { toastify } from "../../../../utils/toastUtils";
import * as Yup from "yup";
import { Modal } from "react-bootstrap";
import { Field, Form, Formik } from "formik";
import { DatePickerField, Input, Select } from "../../../../../_metronic/_partials/controls";
import ImageThumb from "../../../../utils/ImageThumb";
import { useParams } from "react-router-dom";
import { toAbsoluteUrl } from "../../../../../_metronic/_helpers";

function PatientEditDialog({ show, onHide, ...props }) {
  const { patientId } = useParams();
  const [isLoading, setIsLoading] = useState(false);

  const [patient, setPatient] = useState({});
  const [month, day, year] = patient.birthDate ? patient.birthDate.split('/') : [1, 1, 1970];

  useEffect(() => {
    if (props.location.state && props.location.state.patient) {
      setPatient(props.location.state.patient);
    }
    else {
      getPatientDetail(patientId)
        .then(r => {
          console.log(r);
          setPatient(r.data.data)
        }).catch(err => {
        console.log(err);
        toastify.error(`Get detail of patient failed!`)
      })
    }
  }, []);

  useEffect(() => {
    console.log(patient);
  }, [patient])

  // server request for saving customer
  const savePatient = (patient) => {
    console.log(patient);
    // setIsLoading(true);
    editPatient(patientId, patient)
      .then(() => {
        console.log(`Edit patient ${patientId} details ok`);
        // setIsLoading(false);
        toastify.success(`Edit patient ${patientId} details success!`);
        onHide();
      })
      .catch(error => {
        // setIsLoading(false);
        console.log(`Error editing patients: ` + error);
        toastify.error(`Edit patient ${patientId} details`);
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
    gender: Yup.string(),
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
        <Modal.Title id="example-modal-sizes-title-lg">{`Edit patient: ${patient.name}`}</Modal.Title>
      </Modal.Header>
      <Formik
        validationSchema={PatientEditSchema}
        onSubmit={(values) => savePatient(values)}
        initialValues={{
          id: patient.id ? patient.id : "",
          name: patient.name ? patient.name : "",
          phoneNo: patient.phoneNo ? patient.phoneNo : "",
          email: patient.email ? patient.email : "",
          address: patient.address ? patient.address : "",
          birthDate: new Date(year, month, day),
          gender: patient.gender ? patient.gender : "",
          avatar: ""
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
                <div className={"form-group row mx-2"}>
                  <div className="symbol symbol-60 symbol-xxl-100 mr-5 align-self-start align-self-xxl-center">
                    {
                      patient.avatarUrl ?
                        <div
                          className="symbol-label"
                          style={{ backgroundImage: `url(${patient.avatarUrl})` }}
                        /> :
                        <div
                          className="symbol-label"
                          style={{
                            backgroundImage: `url(${toAbsoluteUrl(
                              "/media/users/blank.png"
                            )}`,
                          }}
                        />
                    }
                  </div>
                </div>
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
                    <Select name="gender" label="Gender">
                      <option value="FEMALE">Female</option>
                      <option value="MALE">Male</option>
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

export default PatientEditDialog;
