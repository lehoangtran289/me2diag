import React, { useEffect, useState } from "react";
import BootstrapTable from "react-bootstrap-table-next";
import {
  getSelectRow,
  getHandlerTableChange,
  NoRecordsFoundMessage,
  PleaseWaitMessage,
  sortCaret,
  headerSortingClasses
} from "../../../../_metronic/_helpers";
import paginationFactory, {
  PaginationProvider,
} from "react-bootstrap-table2-paginator";
import { Pagination } from "../../../../_metronic/_partials/controls";

function PatientsTable({ patients, paging, query, setQuery, listLoading, setListLoading }) {

  // Table columns
  const columns = [
    {
      dataField: "id",
      text: "Patient ID"
    },
    {
      dataField: "name",
      text: "Fullname",
      sort: true,
      sortCaret: sortCaret,
      headerSortingClasses
    },
    // {
    //   dataField: "lastName",
    //   text: "Lastname",
    //   sort: true,
    //   sortCaret: sortCaret,
    //   headerSortingClasses
    // },
    {
      dataField: "phoneNo",
      text: "Phone",
      sort: false,
    },
    {
      dataField: "address",
      text: "address",
      sort: false,
    },
    {
      dataField: "email",
      text: "email",
      sort: false,
    },
    {
      dataField: "gender",
      text: "Gender",
      sort: false,
      sortCaret: sortCaret
    },
    {
      dataField: "birthDate",
      text: "Date of birth",
    },
    {
      dataField: "action",
      text: "Actions",
      // TODO
      // formatter: columnFormatters.ActionsColumnFormatter,
      // formatExtraData: {
      //   openEditCustomerDialog: customersUIProps.openEditCustomerDialog,
      //   openDeleteCustomerDialog: customersUIProps.openDeleteCustomerDialog
      // },
      // classes: "text-right pr-0",
      // headerClasses: "text-right pr-3",
      style: {
        minWidth: "100px"
      }
    }
  ];

  // Table pagination properties
  const paginationOptions = {
    custom: true,
    page: paging.currentPage,
    sizePerPage: paging.rowsPerPage,
    totalSize: paging.totalItems,
    sizePerPageList: [
      { text: "10", value: 10 },
      { text: "15", value: 15 },
      { text: "20", value: 20 },
    ],
  };
  console.log(paginationOptions);

  const handleTableChange = (type, { page, sizePerPage, sortField, sortOrder, data }) => {
    const pageNumber = page || 1;
    setQuery({
      ...query,
      page: pageNumber,
      size: sizePerPage
    })
  };

  return (
    <>
      <PaginationProvider pagination={paginationFactory(paginationOptions)}>
        {({ paginationProps, paginationTableProps }) => {
          return (
            <Pagination
              isLoading={listLoading}
              paginationProps={paginationProps}
            >
              <BootstrapTable
                wrapperClasses="table-responsive"
                bordered={false}
                classes="table table-head-custom table-vertical-center overflow-hidden"
                bootstrap4
                remote
                keyField="id"
                data={patients === null ? [] : patients}
                columns={columns}
                // defaultSorted={uiHelpers.defaultSorted}
                onTableChange={handleTableChange}
                {...paginationTableProps}
              >
                <PleaseWaitMessage entities={patients} />
                <NoRecordsFoundMessage entities={patients} />
              </BootstrapTable>
            </Pagination>
          );
        }}
      </PaginationProvider>
    </>
  );
}

export default PatientsTable;