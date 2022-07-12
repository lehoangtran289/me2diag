import React, { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import { getAllAccounts } from "./_redux/accountCrud";
import { Card, CardBody, CardHeader, CardHeaderToolbar } from "../../../_metronic/_partials/controls";
import AccountsTable from "./components/AccountsTable";
import AccountsFilter from "./components/AccountsFilter";

function AccountListPage(props) {
  const history = useHistory();

  const [accounts, setAccounts] = useState([]);
  const [listLoading, setListLoading] = useState(false);

  const [paging, setPaging] = useState({
    totalPages: 0,
    currentPage: 1,
    totalItems: 0,
    rowsPerPage: 0,
  });

  // TODO: add sorting
  const [query, setQuery] = useState({
    query: "",
    page: 1,
    size: 10,
    sort: "",
    isEnable: "",
    roles: "",
  });

  useEffect(() => {
    props.setRerenderFlag(false);
  }, [props.rerenderFlag]);

  useEffect(() => {
    console.log(query);
    setListLoading(true);
    getAllAccounts(query)
      .then(r => {
        setAccounts(r.data.data["items"]);
        setPaging({
          ...paging,
          totalPages: r.data.data["total_pages"],
          totalItems: r.data.data["total_items"],
          currentPage: r.data.data["current_page"],
          rowsPerPage: r.data.data["page_size"]
        });
        setListLoading(false);
      })
      .catch(error => {
        setListLoading(false);
        console.log("Error get all accounts: " + error);
        alert("Cannot get accounts");
      });
  }, [query, props.rerenderFlag]);

  const newAccountButtonClick = () => {
    console.log("newAccountButtonClick")
    history.push("/accounts/new");
  }

  return (
    <Card>
      <CardHeader title={"Account list"}>
        <CardHeaderToolbar>
          <button
            type="button"
            className="btn btn-primary"
            onClick={newAccountButtonClick}
          >
            New user account
          </button>
        </CardHeaderToolbar>
      </CardHeader>
      <CardBody>
        <AccountsFilter query={query} setQuery={setQuery}/>
        <AccountsTable
          accounts={accounts}
          query={query}
          paging={paging}
          setQuery={setQuery}
          listLoading={listLoading}
          setListLoading={setListLoading}
        />
      </CardBody>
    </Card>
  );
}

export default AccountListPage;