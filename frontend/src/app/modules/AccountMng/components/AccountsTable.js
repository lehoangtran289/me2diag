import React from "react";
import { useHistory } from "react-router-dom";
import {
  headerSortingClasses,
  NoRecordsFoundMessage,
  PleaseWaitMessage,
  sortCaret
} from "../../../../_metronic/_helpers";
import { AccountActionsColumnFormatter } from "../column-formatter/AccountActionsColumnFormatter";
import paginationFactory, { PaginationProvider } from "react-bootstrap-table2-paginator";
import { Pagination } from "../../../../_metronic/_partials/controls";
import BootstrapTable from "react-bootstrap-table-next";
import { StatusColumnFormatter } from "../column-formatter/StatusColumnFormatter";
import { RolesColumnFormatter } from "../column-formatter/RolesColumnFormatter";
import { DateColumnFormatter } from "../column-formatter/DateColumnFormatter";

function AccountsTable({ accounts, paging, query, setQuery, listLoading, setListLoading }) {
  const history = useHistory();

  const openEditAccountDetail = (id) => {
    console.log("openEditAccountDetail")
  }

  const openDeactivateAccountDialog = (id, isEnable) => {
    console.log("openDeleteAccountDialog")
    if (isEnable)
      history.push(`/accounts/${id}/deactivate`)
    else
      history.push(`/accounts/${id}/activate`)
  }

  // Table columns
  const columns = [
    {
      dataField: "id",
      text: "Patient ID"
    },
    {
      dataField: "username",
      text: "Username",
      sort: true,
      sortCaret: sortCaret,
      headerSortingClasses
    },
    {
      dataField: "firstname",
      text: "Firstname",
      sort: true,
      sortCaret: sortCaret,
      headerSortingClasses
    },
    {
      dataField: "lastName",
      text: "Lastname",
      sort: true,
      sortCaret: sortCaret,
      headerSortingClasses
    },
    {
      dataField: "email",
      text: "email",
      sort: false,
    },
    {
      dataField: "phoneNo",
      text: "Phone",
      sort: false,
    },
    {
      dataField: "gender",
      text: "Gender"
    },
    {
      dataField: "roles",
      text: "Roles",
      formatter: RolesColumnFormatter
    },
    {
      dataField: "isEnable",
      text: "Status",
      formatter: StatusColumnFormatter,
      sort: false,
      sortCaret: sortCaret
    },
    {
      dataField: "updatedAt",
      text: "Update at",
      formatter: DateColumnFormatter,
      sort: false,
      sortCaret: sortCaret
    },
    {
      dataField: "action",
      text: "Actions",
      formatter: AccountActionsColumnFormatter,
      formatExtraData: {
        openEditAccountDetails: openEditAccountDetail,
        openDeleteAccountDialog: openDeactivateAccountDialog
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
                data={accounts === null ? [] : accounts}
                columns={columns}
                // defaultSorted={uiHelpers.defaultSorted}
                onTableChange={handleTableChange}
                {...paginationTableProps}
              >
                <PleaseWaitMessage entities={accounts} />
                <NoRecordsFoundMessage entities={accounts} />
              </BootstrapTable>
            </Pagination>
          );
        }}
      </PaginationProvider>
    </>
  );
}

export default AccountsTable;