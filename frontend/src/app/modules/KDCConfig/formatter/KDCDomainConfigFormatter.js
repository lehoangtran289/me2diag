import React from "react";

function KdcDomainConfigFormatter(cellContent, row, rowIndex, { field, setData }) {
  return (
    <div className={"d-flex align-items-center justify-content-center"}>
      <input
        key={"domain1"}
        type="number" step={0.1}
        className={`form-control form-control-solid w-50`}
        defaultValue={cellContent ? Number(cellContent) : 0.0}
        onBlur={(e) => {
          setData(rowIndex, field, e.target.value ? Number(e.target.value) : 0.0)
        }}
      />
    </div>
  );
}

export default KdcDomainConfigFormatter;