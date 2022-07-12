import React from "react";

export function StatusColumnFormatter(cellContent, row) {
  const getLabelCssClasses = () => {
    return `label label-lg label-light-${
      row.isEnable ? "success" : "danger"
    } label-inline`;
  };
  return (
    <span className={getLabelCssClasses()}>
      {row.isEnable ? "Active" : "Inactive"}
    </span>
  );
}