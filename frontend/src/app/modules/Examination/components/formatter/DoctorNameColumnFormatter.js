import React from "react";

function DoctorNameColumnFormatter(cellContent, row, rowIndex) {
  return (
    <div>
      <div className={"font-weight-bolder"}>
        {`${cellContent}`}
      </div>
      <div>
        {`${row["userEmail"]}`}
      </div>
    </div>
  );
}

export default DoctorNameColumnFormatter;