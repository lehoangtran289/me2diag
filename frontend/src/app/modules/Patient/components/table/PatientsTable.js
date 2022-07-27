import React from "react";
import BootstrapTable from "react-bootstrap-table-next";
import {
  headerSortingClasses,
  NoRecordsFoundMessage,
  PleaseWaitMessage,
  sortCaret
} from "../../../../../_metronic/_helpers";
import paginationFactory, {PaginationProvider,} from "react-bootstrap-table2-paginator";
import {Pagination} from "../../../../../_metronic/_partials/controls";
import {PatientActionsColumnFormatter} from "../../column-formatters/ActionsColumnFormatter";
import {useHistory} from "react-router-dom";
import {NameColumnFormatter} from "../../column-formatters/NameColumnFormatter";

function PatientsTable({ patients, paging, query, setQuery, listLoading, setListLoading }) {
  const history = useHistory();

  const openViewPatientDetail = (id, data) => {
    console.log("openViewPatientDetail")
    history.push(`/patients/${id}/diagnose`,{
      patient: data
    });
  }

  const openDeletePatientDialog = (id) => {
    console.log("openDeletePatientDialog")
    history.push(`/patients/${id}/delete`)
  }

  const openEditPatientDetail = (id, rowData) => {
    console.log("openEditPatientDetail")
    history.push(`patients/${id}/edit`, {
      patient: rowData
    })
  }

  // Table columns
  const columns = [
    {
      dataField: "id",
      text: "Patient ID"
    },
    {
      dataField: "name",
      text: "Name",
      sort: true,
      sortCaret: sortCaret,
      headerSortingClasses,
      formatter: NameColumnFormatter,
      formatExtraData: {
        openViewPatientDetails: openViewPatientDetail
      }
    },
    {
      dataField: "phoneNo",
      text: "Phone",
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
      dataField: "address",
      text: "address",
      sort: false,
    },
    {
      dataField: "action",
      text: "Actions",
      formatter: PatientActionsColumnFormatter,
      formatExtraData: {
        openViewPatientDetails: openViewPatientDetail,
        openDeletePatientDialog: openDeletePatientDialog,
        openEditPatientDetails: openEditPatientDetail
      },
      classes: "text-right pr-0",
      headerClasses: "text-right pr-3",
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

  const handleTableChange = (type, { page, sizePerPage, sortField, sortOrder, data }) => {
    console.log(sortField, sortOrder);
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