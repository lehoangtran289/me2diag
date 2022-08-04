import React from "react";
import SVG from "react-inlinesvg";
import { toAbsoluteUrl } from "../../../_helpers";

export function GeneralTilesWidget({
  className,
  baseColor = "primary",
  widgetHeight = "150px",
  title,
  value
}) {
  return (
    <>
      <div
        className={`card card-custom bg-${baseColor} ${className}`}
        style={{ height: widgetHeight }}
      >
        <div className="card-body">
          <span className="svg-icon svg-icon-3x svg-icon-white ml-n2">
            <SVG src={toAbsoluteUrl("/media/svg/icons/Layout/Layout-4-blocks.svg")} />
          </span>
          <div
            className={`text-inverse-${baseColor} font-weight-bolder font-size-h2 mt-3`}
          >
            {`${value}`}
          </div>

          <a
            href="#"
            className={`text-inverse-${baseColor} font-weight-bold font-size-lg mt-1`}
          >
            {`${title}`}
          </a>
        </div>
      </div>
    </>
  );
}

