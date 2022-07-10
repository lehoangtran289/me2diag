import React,  { Suspense } from 'react';
import { Redirect, Route, Switch, useHistory, useRouteMatch } from "react-router-dom";
import { ContentRoute, LayoutSplashScreen } from "../../../_metronic/layout";
import PatientCreateDialog from "./components/PatientCreateDialog";
import PatientListPage from "./PatientListPage";

function PatientPage(props) {
  const {url} = useRouteMatch();
  const history = useHistory();

  return (
    <>
      <Route path={`/patients/new`} children={({ match }) => {
        return (
          match &&
          <PatientCreateDialog
            show={Boolean(match)}
            onHide={() => {
              history.push("/patients");
            }}
          />
        )
      }} />

      <PatientListPage/>
      {/*<Route path={`patients/:patientId`} render={() => {*/}
      {/*  return (*/}
      {/*    <div></div>*/}
      {/*  )*/}
      {/*}}/>*/}
    </>
  );
}

export default PatientPage;