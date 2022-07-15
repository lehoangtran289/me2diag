import React from "react";
import {formatDate} from "../../../utils/dateUtils";

export function LastLoginColumnFormatter(cellContent, row) {
  return (
    row.lastLogin &&
    <span>
      {formatDate(new Date(row.lastLogin))}
    </span>
  );
}