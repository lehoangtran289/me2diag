import React from "react";

function KdcResultFormatter(cellContent, row, rowIndex) {
  if (cellContent) {
    return cellContent !== "N00" ? (
      <div className={"font-size-lg text-danger font-weight-bold"}>
        {`${cellContent}`}
      </div>
    ) : (
      <div className={"font-size-base text-info font-weight-bold"}>
        {`${cellContent}`}
      </div>
    )
  } else {
    return null;
  }
}

export default KdcResultFormatter;