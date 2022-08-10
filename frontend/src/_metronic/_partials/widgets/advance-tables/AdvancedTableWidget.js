import React, {useEffect} from 'react';
import SVG from "react-inlinesvg";
import {NoRecordsFoundMessage, PleaseWaitMessage, toAbsoluteUrl} from "../../../_helpers";
import BootstrapTable from "react-bootstrap-table-next";

function AdvancedTableWidget({className, title, data, columns, ...props}) {

  useEffect(() => {
    console.log(title);
    console.log(data);
    console.log(columns);
  })

  return (
    <div className={`card card-custom ${className}`}>
      {/* begin::Header */}
      <div className="card-header border-0 py-5">
        <h3 className="card-title align-items-start flex-column">
          <span className="card-label font-weight-bolder text-dark">
            {`${title}`}
          </span>
        </h3>
        {/*<div className="card-toolbar">*/}
        {/*  <a*/}
        {/*    href="#"*/}
        {/*    className="btn btn-success font-weight-bolder font-size-sm"*/}
        {/*  >*/}
        {/*    <span className="svg-icon svg-icon-md svg-icon-white">*/}
        {/*      <SVG*/}
        {/*        src={toAbsoluteUrl(*/}
        {/*          "/media/svg/icons/Communication/Add-user.svg"*/}
        {/*        )}*/}
        {/*        className="h-50 align-self-center"*/}
        {/*      ></SVG>*/}
        {/*    </span>*/}
        {/*    Add New Member*/}
        {/*  </a>*/}
        {/*</div>*/}
      </div>
      {/* end::Header */}

      {/* begin::Body */}
      <div className="card-body py-0">
        <BootstrapTable
          wrapperClasses="table-responsive"
          bordered={false}
          classes="table table-head-custom table-vertical-center overflow-hidden"
          bootstrap4
          remote
          keyField="id"
          data={data ? data : []}
          columns={columns}
        >
          <PleaseWaitMessage entities={data}/>
          <NoRecordsFoundMessage entities={data}/>
        </BootstrapTable>
      </div>
      {/* end::Body */}
    </div>
  );
}

export default AdvancedTableWidget;