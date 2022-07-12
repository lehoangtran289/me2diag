import React from "react";

export function RolesColumnFormatter(cellContent, row) {
  return (
    <span>
      {row.roles.join(', ')}
    </span>
  );
}