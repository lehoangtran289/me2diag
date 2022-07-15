import React from 'react';

export function NameColumnFormatter(cellContent, row) {
  return (
    <>
      <div className={"container-fluid px-0 mx-0"}>
        <div className={"row px-0 mx-0"}>
          <div className={"col-lg-3 pr-0 pl-0 ml-0"}>
            <div className="symbol mr align-self-start">
              <div
                className="symbol-label"
                style={{backgroundImage: `url(${row.avatarUrl})`}}
              ></div>
            </div>
          </div>
          <div className={"col-lg-9 pr-0 flex align-items-center"}>
            <div className="font-weight-bolder text-dark">
              {row.username ? row.username : ""}
            </div>
            <div>
              {row.email ? row.email : ""}
            </div>
          </div>
        </div>
      </div>
    </>
  );
}