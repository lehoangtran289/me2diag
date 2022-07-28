import React from "react";

function KdcValueColumnFormatter(cellContent, row, rowIndex, { setData, linguisticDomain }) {

  return (
    row.isLinguistic ?
      <>
        <select
          className='form-control form-control-sm form-control-solid'
          onChange={(e) => {
            setData(rowIndex, "value", e.target.value)
          }}
          value={cellContent}
        >
          {
            Object.entries(linguisticDomain).map(obj => {
              const key = obj[0].toUpperCase();
              const value = obj[1];
              return (
                <option
                  key={key}
                  value={key}
                >
                  {key}
                </option>
              );
            })
          }
        </select>
      </> :
      <div className="d-flex align-items-center justify-content-center">
        <input
          type="number"
          className={`form-control form-control-sm form-control-solid w-50`}
          defaultValue={cellContent ? cellContent : 0.0}
          onBlur={(e) => {
            setData(rowIndex, "value", Number(e.target.value))
          }}
        />
      </div>
  );
}

export default KdcValueColumnFormatter;