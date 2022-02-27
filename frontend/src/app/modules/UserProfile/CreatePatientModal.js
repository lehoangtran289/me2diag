import React, { useEffect, useState } from 'react';
import * as Yup from 'yup';
import { Button, Form, Modal } from 'react-bootstrap';
import { Formik } from 'formik';
import ImageThumb from '../../../_metronic/layout/components/header/header-menu/component/ImageThumb';
import { postNewPatient } from '../Dashboard/_redux/patients/PatientsCrud';
import { toast } from 'react-toastify';

function CreatePatientModal({ openModal, handlePatientCreateModalClose, update, setUpdate }) {

  const [loading, setLoading] = useState(false);

  useEffect(() => {

  }, []);

  const handlePatientCreateSubmit = (values) => {
    console.log(values);//fixme

    let formData = new FormData();
    formData.append('id', values.id);
    formData.append('name', values.name);
    formData.append('birthDate', values.birthDate);
    if (values.avatar) formData.append('avatar', values.avatar);
    formData.append('gender', values.gender);

    console.log('formData');
    console.log(formData);

    setLoading(true);

    // fetch
    postNewPatient(formData)
      .then(r => {
        console.log(r);
        if (r.status.code === 'ER96') {
          toast.error('Duplicate patient', {
            position: 'top-right',
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true
          });
        }
        setUpdate(!update);
        setLoading(false);
        handlePatientCreateModalClose();
      })
      .catch((err) => {
        console.log(err);
        setLoading(false);
        handlePatientCreateModalClose();
      });
  };

  const gender = [
    'male', 'female'
  ];

  const schema = Yup.object().shape({
    id: Yup.string().required(),
    name: Yup.string().required(),
    birthDate: Yup.string(),
    gender: Yup.string(),
    avatar: Yup.mixed()
  });

  return (
    <>
      <Modal show={openModal} onHide={handlePatientCreateModalClose}>
        <Modal.Header closeButton>
          <Modal.Title>Create new Patient</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Formik
            validationSchema={schema}
            onSubmit={handlePatientCreateSubmit}
            initialValues={{
              id: '',
              avatar: null,
              name: '',
              birthDate: '',
              gender: 'male'
            }}
          >
            {
              ({ // formik obj
                 handleSubmit,
                 handleChange,
                 handleBlur,
                 values,
                 touched,
                 isValid,
                 errors,
                 setFieldValue
               }) => (
                <Form noValidate onSubmit={handleSubmit}>
                  <Form.Group>
                    <Form.Label>Patient ID</Form.Label>
                    <Form.Control
                      type='text'
                      placeholder='Patient id'
                      name='id'
                      value={values.id}
                      onChange={handleChange}
                      isInvalid={!!errors.id}
                    />
                    <Form.Control.Feedback type='invalid'>
                      {errors.id}
                    </Form.Control.Feedback>
                  </Form.Group>
                  <Form.Group>
                    <Form.Label>Name</Form.Label>
                    <Form.Control
                      type='text'
                      placeholder='Patient name'
                      name='name'
                      value={values.name}
                      onChange={handleChange}
                      isInvalid={!!errors.name}
                    />
                    <Form.Control.Feedback type='invalid'>
                      {errors.name}
                    </Form.Control.Feedback>
                  </Form.Group>
                  <Form.Group>
                    <Form.Label>Date of birth</Form.Label>
                    <Form.Control
                      type='text'
                      placeholder='DOB'
                      name='birthDate'
                      value={values.birthDate}
                      onChange={handleChange}
                      isInvalid={!!errors.birthDate}
                    />
                    <Form.Control.Feedback type='invalid'>
                      {errors.birthDate}
                    </Form.Control.Feedback>
                  </Form.Group>
                  <Form.Group>
                    <Form.Label>Avatar</Form.Label>
                    <Form.Control
                      type={'file'}
                      name={'avatar'}
                      // value={values.background}
                      onChange={(e) => {
                        setFieldValue('avatar', e.currentTarget.files[0]);
                      }}
                    />
                    <ImageThumb file={values.avatar} />
                  </Form.Group>
                  <Form.Group>
                    <Form.Label>Gender</Form.Label>
                    <Form.Control
                      as='select'
                      name='gender'
                      onBlur={handleBlur}
                      value={values.gender}
                      onChange={handleChange}
                    >
                      {
                        gender.map((w, key) => (
                          <option key={key} value={w}>{w}</option>
                        ))
                      }
                    </Form.Control>
                  </Form.Group>
                  <Button type='submit' className={'mr-5'}>
                    {loading ? <span className='ml-2 spinner spinner-white' /> : <span>Create</span>}
                  </Button>
                  <Button variant={'light'} onClick={handlePatientCreateModalClose}>Cancel</Button>
                </Form>
              )}
          </Formik>
        </Modal.Body>
      </Modal>
    </>
  );
}

export default CreatePatientModal;

