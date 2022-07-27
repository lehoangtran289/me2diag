import React from "react";

function PfsDiagnoseFormatter(cellContent, row, rowIndex, { field, setData, linguisticDomain, data }) {
  const handleChange = (e) => {
    setData(rowIndex, field, e.target.value)
  };

  return (
    <div className="d-flex flex-row">
      <select
        className='form-control form-control-lg form-control-solid'
        onChange={handleChange}
        value={row[field]}
      >
        {
          Object.entries(linguisticDomain).map(obj => {
            const key = obj[0];
            const value = obj[1];
            return (
              <option
                key={key}
                value={value}
              >
                {key}
              </option>
            );
          })
        }
      </select>
    </div>
  );
}

export default PfsDiagnoseFormatter;