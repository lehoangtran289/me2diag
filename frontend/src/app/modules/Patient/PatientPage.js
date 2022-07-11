import React, {Suspense, useState} from 'react';
import {Redirect, Route, Switch, useHistory, useLocation, useRouteMatch} from "react-router-dom";
import {ContentRoute, LayoutSplashScreen} from "../../../_metronic/layout";
import PatientCreateDialog from "./components/PatientCreateDialog";
import PatientListPage from "./PatientListPage";
import PatientDeleteDialog from "./components/PatientDeleteDialog";
import {matchPath} from "react-router";

function PatientPage(props) {
  const {url} = useRouteMatch();
  const history = useHistory();
  const [rerenderFlag, setRerenderFlag] = useState(false);

  const location = useLocation();
  console.log(location.pathname);

  const onHide = async () => {
    await setRerenderFlag(true);
    history.push("/patients");
  }

  return (
    <>
      <Switch>
        <Route path={`${url}/new`} children={({match}) => {
          return (
            match &&
            <PatientCreateDialog
              show={Boolean(match)}
              onHide={onHide}
            />
          )
        }}/>
        <Route path={`${url}/:patientId/delete`} children={({match}) => {
          return (
            match &&
            <PatientDeleteDialog
              show={Boolean(match)}
              onHide={onHide}
            />
          )
        }}/>
      </Switch>

      {/*TODO: workaround this routing since url/:patientId ~ url/new :<*/}
      <Switch>
        <Route exact path={`${url}/:patientId`} render={() => {
          return location.pathname === `${url}/new` ?
            <PatientListPage rerenderFlag={rerenderFlag} setRerenderFlag={setRerenderFlag}/> :
            <div>Patient detail</div>
        }}/>
        <Route path={`${url}`} children={({match}) => {
          return (
            <PatientListPage rerenderFlag={rerenderFlag} setRerenderFlag={setRerenderFlag}/>
          )
        }}/>
      </Switch>
    </>
  );
}

export default PatientPage;