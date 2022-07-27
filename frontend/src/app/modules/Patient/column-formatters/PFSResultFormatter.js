import React from "react";

function PfsResultFormatter(cellContent, row, rowIndex) {
  if (cellContent) {
    return cellContent > 50.0 ? (
      <div className={"font-size-lg text-danger font-weight-bold"}>
        {`${cellContent} %`}
      </div>
    ) : (
      <div className={"font-size-base text-info font-weight-bold"}>
        {`${cellContent} %`}
      </div>
    )
  } else {
    return null;
  }
}

export default PfsResultFormatter;