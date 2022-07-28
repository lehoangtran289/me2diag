import React from 'react';

function PictureFuzzySetFormatter(cellContent, row, rowIndex, { field, setData }) {
  return (
    <div className="d-flex flex-row">
      <input
        type="number" min={0.0} max={1.0} step={0.01}
        className={`form-control form-control-sm form-control-solid`}
        defaultValue={cellContent.positive}
        onBlur={(e) => {
          setData(rowIndex, field, {
            ...cellContent,
            positive: e.target.value ? Number(e.target.value) : 0.0
          })
        }}
      />
      <input
        type="number" min={0.0} max={1.0} step={0.01}
        className={`form-control form-control-sm form-control-solid`}
        defaultValue={cellContent.neutral}
        onBlur={(e) => {
          setData(rowIndex, field, {
            ...cellContent,
            neutral: e.target.value ? Number(e.target.value) : 0.0
          })
        }}
      />
      <input
        type="number" min={0.0} max={1.0} step={0.01}
        className={`form-control form-control-sm form-control-solid`}
        defaultValue={cellContent.negative}
        onBlur={(e) => {
          setData(rowIndex, field, {
            ...cellContent,
            negative: e.target.value ? Number(e.target.value) : 0.0
          })
        }}
      />
    </div>
  );
}

export default PictureFuzzySetFormatter;