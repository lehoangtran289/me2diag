import React from 'react';
import SVG from "react-inlinesvg";
import {toAbsoluteUrl} from "../../../../_metronic/_helpers";

export function PatientActionsColumnFormatter(
  cellContent,
  row,
  rowIndex,
  { openViewPatientDetails, openDeletePatientDialog, openEditPatientDetails }
) {
  return (
    <>
      <a
        title="View patient detail"
        className="btn btn-icon btn-light btn-hover-primary btn-sm mr-3"
        onClick={() => openViewPatientDetails(row.id)}
      >
        <span className="svg-icon svg-icon-md svg-icon-primary">
          <SVG
            src={toAbsoluteUrl("/media/svg/icons/General/Visible.svg")}
          />
        </span>
      </a>
      <a
        title="View patient detail"
        className="btn btn-icon btn-light btn-hover-primary btn-sm mr-3"
        onClick={() => openEditPatientDetails(row.id, row)}
      >
        <span className="svg-icon svg-icon-md svg-icon-primary">
          <SVG
            src={toAbsoluteUrl("/media/svg/icons/General/Edit.svg")}
          />
        </span>
      </a>
      <a
        title="Delete patient"
        className="btn btn-icon btn-light btn-hover-danger btn-sm"
        onClick={() => openDeletePatientDialog(row.id)}
      >
        <span className="svg-icon svg-icon-md svg-icon-danger">
          <SVG src={toAbsoluteUrl("/media/svg/icons/General/Trash.svg")} />
        </span>
      </a>
    </>
  );
}