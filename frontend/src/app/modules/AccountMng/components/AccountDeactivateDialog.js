import React, { useState } from "react";
import { useParams } from "react-router-dom";
import { toastify } from "../../../utils/toastUtils";
import { Modal } from "react-bootstrap";
import { ModalProgressBar } from "../../../../_metronic/_partials/controls";
import { deactivateSelectedAccount } from "../_redux/accountCrud";

export default function AccountDeactivateDialog({ show, onHide }) {
  const { userId } = useParams();

  const [isLoading, setIsLoading] = useState(false);

  const deactivateAccount = () => {
    // server request for deleting customer by selected ids
    setIsLoading(true);
    deactivateSelectedAccount(userId)
      .then(r => {
        setIsLoading(false);
        console.log(r);
        toastify.success('Deactivate account success!');
        onHide();
      })
      .catch(err => {
        setIsLoading(false);
        console.log(err);
        toastify.error("Deactivate account failed");
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
          Account deactivation
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        {!isLoading && (
          <span>Are you sure to deactivate the selected user account?</span>
        )}
        {isLoading && <span>Account is deactivating...</span>}
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
            onClick={deactivateAccount}
            className="btn btn-danger btn-elevate"
          >
            Deactivate
          </button>
        </div>
      </Modal.Footer>
    </Modal>
  );
}