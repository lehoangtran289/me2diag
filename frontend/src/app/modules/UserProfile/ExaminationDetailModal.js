import React, { useEffect } from 'react';
import { Modal } from 'react-bootstrap';

function ExaminationDetailModal({ examination, openModal, handlePatientExamModalClose }) {

  useEffect(() => {
    console.log(examination);
  }, [examination])

  return (
    <>
      <Modal show={openModal} onHide={handlePatientExamModalClose} size={'lg'}>
        <Modal.Header closeButton>
          <Modal.Title>Examination Details</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {/*TODO: examination details*/}
          <div>
            <h6 className={'font-weight-bolder mb-5'}>
              {`Examination ${examination && examination.examination_id}`}
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
                  value={examination && examination.result[1].FEVER}
                  className={`form-control form-control-solid mr-0 ml-auto`}
                />
              </div>
              <div className='col-lg-2'>
                <input
                  type="text"
                  disabled
                  value={examination && examination.result[2].MALARIA}
                  className={`form-control form-control-solid mr-0 ml-auto`}
                />
              </div>
              <div className='col-lg-2'>
                <input
                  type="text"
                  disabled
                  value={examination && examination.result[4].TYPHOID}
                  className={`form-control form-control-solid mr-0 ml-auto`}
                />
              </div>
              <div className='col-lg-2'>
                <input
                  type="text"
                  disabled
                  value={examination && examination.result[3].STOMACH}
                  className={`form-control form-control-solid mr-0 ml-auto`}
                />
              </div>
              <div className='col-lg-2'>
                <input
                  type="text"
                  disabled
                  value={examination && examination.result[0].CHEST_PROBLEM}
                  className={`form-control form-control-solid mr-0 ml-auto`}
                />
              </div>
            </div>
          </div>
        </Modal.Body>
      </Modal>
    </>
  );
}

export default ExaminationDetailModal;