import React from "react";
import SVG from "react-inlinesvg";
import { toAbsoluteUrl } from "../../../../_metronic/_helpers";

export function AccountActionsColumnFormatter(
  cellContent,
  row,
  rowIndex,
  { openEditAccountDetails, openDeleteAccountDialog }
) {
  return (
    <>
      <a
        title="View patient detail"
        className="btn btn-icon btn-light btn-hover-primary btn-sm mx-3"
        onClick={() => openEditAccountDetails(row.id)}
      >
        <span className="svg-icon svg-icon-md svg-icon-primary">
          <SVG
            src={toAbsoluteUrl("/media/svg/icons/General/Visible.svg")}
          />
        </span>
      </a>
      <> </>

      <a
        title="Delete patient"
        className="btn btn-icon btn-light btn-hover-danger btn-sm"
        onClick={() => openDeleteAccountDialog(row.id)}
      >
        <span className="svg-icon svg-icon-md svg-icon-danger">
          <SVG src={toAbsoluteUrl("/media/svg/icons/General/Trash.svg")} />
        </span>
      </a>
    </>
  );
}