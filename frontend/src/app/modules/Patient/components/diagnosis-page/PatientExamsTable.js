import React from "react";

function PatientExamsTable(props) {
  return (
    <div className="card card-custom">
      {/* begin::Header */}
      <div className="card-header py-3">
        <div className="card-title align-items-start flex-column">
          <h3 className="card-label font-weight-bolder text-dark">
            Patient examinations history
          </h3>
          <span className="text-muted font-weight-bold font-size-sm mt-1">
            Records of all patient's examination history
          </span>
        </div>
      </div>
    </div>
  );
}

export default PatientExamsTable;