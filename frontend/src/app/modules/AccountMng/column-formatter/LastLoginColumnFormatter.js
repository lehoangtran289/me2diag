import React from "react";
import {formatDate, timeSince} from "../../../utils/dateUtils";

export function LastLoginColumnFormatter(cellContent, row) {
  return (
    row.lastLogin &&
    <span>
      {timeSince(new Date(row.lastLogin))}
    </span>
  );
}