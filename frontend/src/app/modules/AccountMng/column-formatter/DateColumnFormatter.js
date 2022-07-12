import React from "react";

export function DateColumnFormatter(cellContent, row) {
  return (
    <span>
      {new Date(row.updatedAt).toLocaleDateString()}
    </span>
  );
}