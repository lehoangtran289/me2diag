import React from "react";
import SVG from "react-inlinesvg";
import { toAbsoluteUrl } from "../../../../_metronic/_helpers";

export function NameColumnFormatter(cellContent, row, rowIndex, { openViewPatientDetails }) {

  const handlePatientOnClick = () => {
    openViewPatientDetails(row.id);
  }

  return (
    <>
      <div className={"container-fluid px-0 mx-0"} >
        <div className={"row px-0 mx-0"}>
          <div className={"col-lg-3 pr-0 pl-0 ml-0"}>
            <div className="symbol mr align-self-start">
              {row.avatarUrl ?
                <div
                  className="symbol-label"
                  style={{ backgroundImage: `url(${row.avatarUrl})` }} />
                :
                <div
                  className="symbol-label">
                  <span className="svg-icon svg-icon-xl">
                    <SVG
                      src={toAbsoluteUrl(
                        "/media/svg/icons/General/User.svg"
                      )}
                    ></SVG>{" "}
                  </span>
                </div>
              }
            </div>
          </div>
          <a className={"col-lg-9 pl-0 pr-0 flex align-items-center"} onClick={handlePatientOnClick}>
            <div className="font-weight-bolder text-dark">
              {row.name ? row.name : ""}
            </div>
            <div>
              {row.email ? row.email : ""}
            </div>
          </a>
        </div>
      </div>
    </>
  );
}