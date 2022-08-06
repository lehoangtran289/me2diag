import React from "react";
import { useHistory } from "react-router-dom";
import { NoRecordsFoundMessage, PleaseWaitMessage, sortCaret } from "../../../../../_metronic/_helpers";
import paginationFactory, { PaginationProvider } from "react-bootstrap-table2-paginator";
import { Pagination } from "../../../../../_metronic/_partials/controls";
import BootstrapTable from "react-bootstrap-table-next";
import ExamActionsColumnFormatter from "../formatter/ExamActionsColumnFormatter";
import DoctorNameColumnFormatter from "../formatter/DoctorNameColumnFormatter";
import { formatDate } from "../../../../utils/dateUtils";

function ExamsTable({ exams, paging, query, setQuery, listLoading, setListLoading, isPatientPage }) {
  const history = useHistory();

  const openViewExamDetail = (id, data) => {
    console.log("openViewExamDetail")
    history.push(`/examinations/${id}`,{
      patient: data
    });
  }

  // Table columns for patient examinations page
  const patientExamsColumns = [
    {
      dataField: "id",
      text: "Examination ID",
      headerStyle: (colum, colIndex) => {
        return { width: '13em' };
      },
    },
    {
      dataField: "appId",
      text: "Application ID",
      align: "center",
      headerAlign: "center",
      headerStyle: (colum, colIndex) => {
        return { width: '12em' };
      },
      formatter: (cellContent, row, rowIndex) => {
        return (
          <div className={`font-weight-bolder font-size-lg text-${cellContent === "PFS" ? "success" : "info"}`}>
            {`${cellContent}`}
          </div>
        );
      }
    },
    {
      dataField: "createdAt",
      text: "Exam Datetime",
      align: "center",
      headerAlign: "center",
      formatter: (cellContent, row, rowIndex) => {
        return <>{`${formatDate(new Date(cellContent))}`}</>
      }
    },
    {
      dataField: "userFullName",
      text: "Doctor in charge",
      formatter: DoctorNameColumnFormatter,
      headerStyle: (colum, colIndex) => {
        return { width: '17em' };
      },
    },
    {
      dataField: "result",
      text: "Result",
      align: "center",
      headerAlign: "center",
      formatter: (cellContent, row, rowIndex) => {
        return <div className={"font-weight-bold"}>{`${cellContent}`}</div>
      }
    },
    {
      dataField: "action",
      text: "Actions",
      formatter: ExamActionsColumnFormatter,
      formatExtraData: {
        openViewExamDetails: openViewExamDetail,
      },
      align: "center",
      headerAlign: "center",
    }
  ];

  // Table columns for examinations list page
  const AllExamsListColumns = [
    {
      dataField: "id",
      text: "Examination ID",
      headerStyle: (colum, colIndex) => {
        return { width: '13em' };
      },
    },
    {
      dataField: "appId",
      text: "Application ID",
      align: "center",
      headerAlign: "center",
      headerStyle: (colum, colIndex) => {
        return { width: '12em' };
      },
      formatter: (cellContent, row, rowIndex) => {
        return (
          <div className={`font-weight-bolder font-size-lg text-${cellContent === "PFS" ? "success" : "info"}`}>
            {`${cellContent}`}
          </div>
        );
      }
    },
    {
      dataField: "createdAt",
      text: "Exam Datetime",
      align: "center",
      headerAlign: "center",
      formatter: (cellContent, row, rowIndex) => {
        return <>{`${formatDate(new Date(cellContent))}`}</>
      }
    },
    {
      dataField: "patientName",
      text: "Patient name",
      formatter: (cellContent, row, rowIndex) => {
        return (
          <>
            <div className={"font-weight-bolder"}>{`${cellContent}`}</div>
            <div>{`ID: ${row["patientId"]}`}</div>
          </>
        );
      }
    },
    {
      dataField: "birthDate",
      text: "D.O.B",
      align: "center",
      headerAlign: "center",
      formatter: (cellContent, row, rowIndex) => {
        return <>{`${new Date(cellContent).toLocaleDateString('en-GB')}`}</>
      }
    },
    {
      dataField: "userFullName",
      text: "Doctor in charge",
      formatter: DoctorNameColumnFormatter,
      headerStyle: (colum, colIndex) => {
        return { width: '17em' };
      },
    },
    {
      dataField: "result",
      text: "Result",
      align: "center",
      headerAlign: "center",
      formatter: (cellContent, row, rowIndex) => {
        return <div className={"font-weight-bold"}>{`${cellContent}`}</div>
      }
    },
    {
      dataField: "action",
      text: "Actions",
      formatter: ExamActionsColumnFormatter,
      formatExtraData: {
        openViewExamDetails: openViewExamDetail,
      },
      align: "center",
      headerAlign: "center",
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
                data={exams === null ? [] : exams}
                columns={isPatientPage ? patientExamsColumns : AllExamsListColumns}
                // defaultSorted={uiHelpers.defaultSorted}
                onTableChange={handleTableChange}
                {...paginationTableProps}
              >
                <PleaseWaitMessage entities={exams} />
                <NoRecordsFoundMessage entities={exams} />
              </BootstrapTable>
            </Pagination>
          );
        }}
      </PaginationProvider>
    </>
  );
}

export default ExamsTable;