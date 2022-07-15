import React from "react";

export function RolesColumnFormatter(cellContent, row) {
  return (
    <span>
      {row.roles.map((value, key) => {
        return <div key={key}>{value}</div>
      })}
    </span>
  );
}