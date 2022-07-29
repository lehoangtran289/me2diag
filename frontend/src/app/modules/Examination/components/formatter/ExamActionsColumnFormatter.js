import React from "react";
import SVG from "react-inlinesvg";
import { toAbsoluteUrl } from "../../../../../_metronic/_helpers";

function ExamActionsColumnFormatter(cellContent, row, rowIndex, { openViewExamDetails }) {

  return (
    <>
      <a
        title="View patient detail"
        className="btn btn-icon btn-light btn-hover-primary btn-sm"
        onClick={() => openViewExamDetails(row.id, row)}
      >
        <span className="svg-icon svg-icon-md svg-icon-primary">
          <SVG
            src={toAbsoluteUrl("/media/svg/icons/General/Visible.svg")}
          />
        </span>
      </a>
    </>
  );
}

export default ExamActionsColumnFormatter;