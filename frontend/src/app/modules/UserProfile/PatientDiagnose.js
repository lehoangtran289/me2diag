import React, { useEffect, useState } from 'react';
import { useLocation, useParams } from 'react-router-dom';
import { diagnose, getPatientById } from '../Dashboard/_redux/patients/PatientsCrud';
import { Button, Col, Row } from 'react-bootstrap';
import PatientDetail from './PatientDetail';
import { toast } from 'react-toastify';

function PatientDiagnose(props) {
  const { patientId } = useParams();
  const location = useLocation();

  const [patient, setPatient] = useState({});

  const initPFS = {
    'positive': 0.0,
    'neutral': 0.0,
    'negative': 0.0
  };
  const [temperature, setTemperature] = useState(initPFS);
  const [headache, setHeadache] = useState(initPFS);
  const [stomachPain, setStomachPain] = useState(initPFS);
  const [cough, setCough] = useState(initPFS);
  const [chestPain, setChestPain] = useState(initPFS);

  const [result, setResult] = useState(null);

  useEffect(() => {
    if (location.state.patient) {
      console.log(location.state.patient.avatarUrl);
      setPatient(location.state.patient);
    } else {
      getPatientById(patientId)
        .then(r => {
          console.log(r.data);
          setPatient(r.data);
        })
        .catch(e => console.log(e));
    }

  }, []);

  const handleDiagnoseClick = () => {
    // console.log(temperature);
    // console.log(headache);
    // console.log(stomachPain);
    // console.log(cough);
    // console.log(chestPain);

    // validate
    if (
      temperature.positive + temperature.neutral + temperature.negative > 1 ||
      headache.positive + headache.neutral + headache.negative > 1 ||
      stomachPain.positive + stomachPain.neutral + stomachPain.negative > 1 ||
      cough.positive + cough.neutral + cough.negative > 1 ||
      chestPain.positive + chestPain.neutral + chestPain.negative > 1
    ) {
      toast.error('Invalid input, sum of a symptom in pfs must <= 1', {
        position: 'top-right',
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true
      });
      return;
    }

    const requestBody = {
      "patient_id": patientId,
      "symptoms": [
        {
          "TEMPERATURE" : {
            "positive": temperature.positive,
            "neutral": temperature.neutral,
            "negative": temperature.negative
          }
        },
        {
          "HEADACHE" : {
            "positive": headache.positive,
            "neutral": headache.neutral,
            "negative": headache.negative
          }
        },
        {
          "STOMACH_PAIN" : {
            "positive": stomachPain.positive,
            "neutral": stomachPain.neutral,
            "negative": stomachPain.negative
          }
        },
        {
          "COUGH" : {
            "positive": cough.positive,
            "neutral": cough.neutral,
            "negative": cough.negative
          }
        },
        {
          "CHEST_PAIN" : {
            "positive": chestPain.positive,
            "neutral": chestPain.neutral,
            "negative": chestPain.negative
          }
        }
      ]
    }
    console.log(requestBody);

    diagnose(requestBody)
      .then(r => {
        console.log(r.data);
        setResult(r.data);
      })
      .catch(e => console.log(e));
  };

  return (
    <div className={'card card-custom'}>
      {/* begin::Header */}
      <div className='card-header py-3'>
        <div className='card-title align-items-start flex-column'>
          <h3 className='card-label font-weight-bolder text-dark'>
            Patient Diagnosis
          </h3>
          <span className='text-muted font-weight-bold font-size-sm mt-1'>
            Diagnose patient based on common symptoms
          </span>
        </div>
      </div>
      {/* end::Header */}
      <div className='card-body'>
        <Row>
          <Col lg={4}>
            {/*  PATIENT DATA */}
            <h6 className={'font-weight-bolder mb-5'}>
              Patient Details
            </h6>
            <div className='separator separator-dashed my-5' />
            <PatientDetail patient={patient} />
          </Col>
          <Col lg={1}/>
          <Col lg={7}>
            {/* DIAGNOSIS */}
            <h6 className={'font-weight-bolder mb-5'}>
              Diagnosis
            </h6>
            <div className='separator separator-dashed my-5' />
            <div className='form-group row'>
              <label className='col-lg-2 col-form-label'> </label>
              <div className='col-lg-3'>
                <h6>Positive</h6>
              </div>
              <div className='col-lg-3'>
                <h6>Neutral</h6>
              </div>
              <div className='col-lg-3'>
                <h6>Negative</h6>
              </div>
            </div>

            {/*BEGIN TEMPERATURE*/}
            <div className='form-group row'>
              <label className='col-lg-2 col-form-label'>Temperature</label>
              <div className='col-lg-3'>
                <input
                  type='number'
                  min={0}
                  max={1}
                  step={0.01}
                  value={temperature.positive}
                  onChange={(e) => {
                    setTemperature({
                      ...temperature,
                      'positive': parseFloat(e.target.value)
                    });
                  }}
                  className={`form-control form-control-solid mr-5 ml-auto`}
                />
              </div>
              <div className='col-lg-3'>
                <input
                  type='number'
                  min={0}
                  max={1}
                  step={0.01}
                  value={temperature.neutral}
                  onChange={(e) => {
                    setTemperature({
                      ...temperature,
                      'neutral': parseFloat(e.target.value)
                    });
                  }}
                  className={`form-control form-control-solid mr-5 ml-auto`}
                />
              </div>
              <div className='col-lg-3'>
                <input
                  type='number'
                  min={0}
                  max={1}
                  step={0.01}
                  value={temperature.negative}
                  onChange={(e) => {
                    setTemperature({
                      ...temperature,
                      'negative': parseFloat(e.target.value)
                    });
                  }}
                  className={`form-control form-control-solid mr-5 ml-auto`}
                />
              </div>
            </div>
            {/*END TEMPERATURE*/}

            {/*BEGIN HEADACHE*/}
            <div className='form-group row'>
              <label className='col-lg-2 col-form-label'>Headache</label>
              <div className='col-lg-3'>
                <input
                  type='number'
                  min={0}
                  max={1}
                  step={0.01}
                  value={headache.positive}
                  onChange={(e) => {
                    setHeadache({
                      ...headache,
                      'positive': parseFloat(e.target.value)
                    });
                  }}
                  className={`form-control form-control-solid mr-5 ml-auto`}
                />
              </div>
              <div className='col-lg-3'>
                <input
                  type='number'
                  min={0}
                  max={1}
                  step={0.01}
                  value={headache.neutral}
                  onChange={(e) => {
                    setHeadache({
                      ...headache,
                      'neutral': parseFloat(e.target.value)
                    });
                  }}
                  className={`form-control form-control-solid mr-5 ml-auto`}
                />
              </div>
              <div className='col-lg-3'>
                <input
                  type='number'
                  min={0}
                  max={1}
                  step={0.01}
                  value={headache.negative}
                  onChange={(e) => {
                    setHeadache({
                      ...headache,
                      'negative': parseFloat(e.target.value)
                    });
                  }}
                  className={`form-control form-control-solid mr-5 ml-auto`}
                />
              </div>
            </div>
            {/*END HEADACHE*/}

            {/*BEGIN STOMACH PAIN*/}
            <div className='form-group row'>
              <label className='col-lg-2 col-form-label'>Stomach pain</label>
              <div className='col-lg-3'>
                <input
                  type='number'
                  min={0}
                  max={1}
                  step={0.01}
                  value={stomachPain.positive}
                  onChange={(e) => {
                    setStomachPain({
                      ...stomachPain,
                      'positive': parseFloat(e.target.value)
                    });
                  }}
                  className={`form-control form-control-solid mr-5 ml-auto`}
                />
              </div>
              <div className='col-lg-3'>
                <input
                  type='number'
                  min={0}
                  max={1}
                  step={0.01}
                  value={stomachPain.neutral}
                  onChange={(e) => {
                    setStomachPain({
                      ...stomachPain,
                      'neutral': parseFloat(e.target.value)
                    });
                  }}
                  className={`form-control form-control-solid mr-5 ml-auto`}
                />
              </div>
              <div className='col-lg-3'>
                <input
                  type='number'
                  min={0}
                  max={1}
                  step={0.01}
                  value={stomachPain.negative}
                  onChange={(e) => {
                    setStomachPain({
                      ...stomachPain,
                      'negative': parseFloat(e.target.value)
                    });
                  }}
                  className={`form-control form-control-solid mr-5 ml-auto`}
                />
              </div>
            </div>
            {/*END STOMACH PAIN*/}

            {/*BEGIN COUGH*/}
            <div className='form-group row'>
              <label className='col-lg-2 col-form-label'>Cough</label>
              <div className='col-lg-3'>
                <input
                  type='number'
                  min={0}
                  max={1}
                  step={0.01}
                  value={cough.positive}
                  onChange={(e) => {
                    setCough({
                      ...cough,
                      'positive': parseFloat(e.target.value)
                    });
                  }}
                  className={`form-control form-control-solid mr-5 ml-auto`}
                />
              </div>
              <div className='col-lg-3'>
                <input
                  type='number'
                  min={0}
                  max={1}
                  step={0.01}
                  value={cough.neutral}
                  onChange={(e) => {
                    setCough({
                      ...cough,
                      'neutral': parseFloat(e.target.value)
                    });
                  }}
                  className={`form-control form-control-solid mr-5 ml-auto`}
                />
              </div>
              <div className='col-lg-3'>
                <input
                  type='number'
                  min={0}
                  max={1}
                  step={0.01}
                  value={cough.negative}
                  onChange={(e) => {
                    setCough({
                      ...cough,
                      'negative': parseFloat(e.target.value)
                    });
                  }}
                  className={`form-control form-control-solid mr-5 ml-auto`}
                />
              </div>
            </div>
            {/*END COUGH*/}

            {/*BEGIN CHEST PAIN*/}
            <div className='form-group row'>
              <label className='col-lg-2 col-form-label'>Chest Pain</label>
              <div className='col-lg-3'>
                <input
                  type='number'
                  min={0}
                  max={1}
                  step={0.01}
                  value={chestPain.positive}
                  onChange={(e) => {
                    setChestPain({
                      ...chestPain,
                      'positive': parseFloat(e.target.value)
                    });
                  }}
                  className={`form-control form-control-solid mr-5 ml-auto`}
                />
              </div>
              <div className='col-lg-3'>
                <input
                  type='number'
                  min={0}
                  max={1}
                  step={0.01}
                  value={chestPain.neutral}
                  onChange={(e) => {
                    setChestPain({
                      ...chestPain,
                      'neutral': parseFloat(e.target.value)
                    });
                  }}
                  className={`form-control form-control-solid mr-5 ml-auto`}
                />
              </div>
              <div className='col-lg-3'>
                <input
                  type='number'
                  min={0}
                  max={1}
                  step={0.01}
                  value={chestPain.negative}
                  onChange={(e) => {
                    setChestPain({
                      ...chestPain,
                      'negative': parseFloat(e.target.value)
                    });
                  }}
                  className={`form-control form-control-solid mr-5 ml-auto`}
                />
              </div>
            </div>
            {/*END CHEST PAIN*/}

            <Button
              onClick={handleDiagnoseClick}
              variant='primary'
              style={{ justifyContent: 'flex-start' }}
              className={'text-left mb-3'}
            >
              Diagnose
            </Button>

            <div className='separator separator-dashed my-5' />

            {/* BEGIN RESULT */}
            {
              result ?
                <div>
                  <h6 className={'font-weight-bolder mb-5'}>
                    {`Result - examination ${result.examinationId}`}
                  </h6>
                  <div className='separator separator-dashed my-5' />
                  <div className='form-group row'>
                    <div className='col-lg-2'>
                      <h6>Fever</h6>
                    </div>
                    <div className='col-lg-2'>
                      <h6>Malaria</h6>
                    </div>
                    <div className='col-lg-2'>
                      <h6>Typhoid</h6>
                    </div>
                    <div className='col-lg-2'>
                      <h6>Stomach</h6>
                    </div>
                    <div className='col-lg-2'>
                      <h6>Chest problem</h6>
                    </div>
                  </div>
                  <div className='form-group row'>
                    <div className='col-lg-2'>
                      <input
                        type="text"
                        disabled
                        value={result.result[0].FEVER}
                        className={`form-control form-control-solid mr-0 ml-auto`}
                      />
                    </div>
                    <div className='col-lg-2'>
                      <input
                        type="text"
                        disabled
                        value={result.result[1].MALARIA}
                        className={`form-control form-control-solid mr-0 ml-auto`}
                      />
                    </div>
                    <div className='col-lg-2'>
                      <input
                        type="text"
                        disabled
                        value={result.result[2].TYPHOID}
                        className={`form-control form-control-solid mr-0 ml-auto`}
                      />
                    </div>
                    <div className='col-lg-2'>
                      <input
                        type="text"
                        disabled
                        value={result.result[3].STOMACH}
                        className={`form-control form-control-solid mr-0 ml-auto`}
                      />
                    </div>
                    <div className='col-lg-2'>
                      <input
                        type="text"
                        disabled
                        value={result.result[4].CHEST_PROBLEM}
                        className={`form-control form-control-solid mr-0 ml-auto`}
                      />
                    </div>
                  </div>
                </div>
                : ''
            }
          </Col>
        </Row>
      </div>
    </div>
  );
}

export default PatientDiagnose;