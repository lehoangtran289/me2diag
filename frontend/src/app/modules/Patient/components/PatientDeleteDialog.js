import React, {useState} from 'react';
import {useParams} from "react-router-dom";
import {Modal} from "react-bootstrap";
import {ModalProgressBar} from "../../../../_metronic/_partials/controls";
import {deleteSelectedPatient} from "../_redux/patientCrud";
import {toastify} from "../../../utils/toastUtils";

export default function PatientDeleteDialog({ show, onHide }) {
  const { patientId } = useParams();

  const [isLoading, setIsLoading] = useState(false);

  const deletePatient = () => {
    // server request for deleting customer by selected ids
    setIsLoading(true);
    deleteSelectedPatient(patientId)
      .then(r => {
        setIsLoading(false);
        console.log(r);
        toastify.success('Delete patient success!');
        onHide();
      })
      .catch(err => {
        setIsLoading(false);
        console.log(err);
        toastify.error('Delete patient failed!', {
          position: 'top-right',
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true
        });
      })
  };

  return (
    <Modal
      show={show}
      onHide={onHide}
      aria-labelledby="example-modal-sizes-title-lg"
    >
      {/*begin::Loading*/}
      {isLoading && <ModalProgressBar />}
      {/*end::Loading*/}
      <Modal.Header closeButton>
        <Modal.Title id="example-modal-sizes-title-lg">
          Customers Delete
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        {!isLoading && (
          <span>Are you sure to permanently delete selected customers?</span>
        )}
        {isLoading && <span>Customer are deleting...</span>}
      </Modal.Body>
      <Modal.Footer>
        <div>
          <button
            type="button"
            onClick={onHide}
            className="btn btn-light btn-elevate"
          >
            Cancel
          </button>
          <> </>
          <button
            type="button"
            onClick={deletePatient}
            className="btn btn-primary btn-elevate"
          >
            Delete
          </button>
        </div>
      </Modal.Footer>
    </Modal>
  );
}