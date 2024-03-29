import React, {useState} from "react";
import {Switch, useHistory, useLocation, useRouteMatch} from "react-router-dom";
import {ContentRoute} from "../../../_metronic/layout";
import AccountListPage from "./AccountListPage";
import AccountCreateDialog from "./components/AccountCreateDialog";
import AccountDeactivateDialog from "./components/AccountDeactivateDialog";
import AccountEditDialog from "./components/AccountEditDialog";
import AccountActivateDialog from "./components/AccountActivateDialog";

function AccountMngPage(props) {
  const {url} = useRouteMatch();
  const history = useHistory();
  const [rerenderFlag, setRerenderFlag] = useState(false);

  const location = useLocation();
  console.log(location.pathname);

  const onHide = async () => {
    await setRerenderFlag(true);
    history.push("/accounts");
  }

  return (
    <>
      <Switch>
        <ContentRoute path={`${url}/new`} children={({match}) => {
          return (
            match &&
            <AccountCreateDialog
              show={Boolean(match)}
              onHide={onHide}
              {...props}
            />
          )
        }}/>
        <ContentRoute path={`${url}/:userId/deactivate`} children={({match}) => {
          return (
            match &&
            <AccountDeactivateDialog
              show={Boolean(match)}
              onHide={onHide}
            />
          )
        }}/>
        <ContentRoute path={`${url}/:userId/activate`} children={({match}) => {
          return (
            match &&
            <AccountActivateDialog
              show={Boolean(match)}
              onHide={onHide}
            />
          )
        }}/>
        <ContentRoute path={`${url}/:userId/edit`} children={({match}) => {
          return (
            match &&
            <AccountEditDialog
              show={Boolean(match)}
              onHide={onHide}
              {...props}
            />
          )
        }}/>
      </Switch>

      <ContentRoute path={`${url}`} children={({match}) => {
        return (
          <AccountListPage rerenderFlag={rerenderFlag} setRerenderFlag={setRerenderFlag}/>
        )
      }}/>
    </>
  );
}

export default AccountMngPage;