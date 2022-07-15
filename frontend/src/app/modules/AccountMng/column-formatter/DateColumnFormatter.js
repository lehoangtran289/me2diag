import React from "react";
import {formatDate} from "../../../utils/dateUtils";

export function DateColumnFormatter(cellContent, row) {
  return (
    <span>
      {formatDate(new Date(row.updatedAt))}
    </span>
  );
}