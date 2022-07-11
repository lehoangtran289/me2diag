import React, {Suspense, useState} from 'react';
import { Redirect, Route, Switch, useHistory, useRouteMatch } from "react-router-dom";
import { ContentRoute, LayoutSplashScreen } from "../../../_metronic/layout";
import PatientCreateDialog from "./components/PatientCreateDialog";
import PatientListPage from "./PatientListPage";

function PatientPage(props) {
  const {url} = useRouteMatch();
  const history = useHistory();
  const [rerenderFlag, setRerenderFlag] = useState(false);

  const onHide = async () => {
    await setRerenderFlag(true);
    history.push("/patients");
  }

  return (
    <>
      <Route path={`${url}`} render={() => {
        return (
          <PatientListPage rerenderFlag={rerenderFlag} setRerenderFlag={setRerenderFlag}/>
        )
      }} />
      <Route path={`${url}/new`} children={({ match }) => {
        return (
          match &&
          <PatientCreateDialog
            show={Boolean(match)}
            onHide={onHide}
          />
        )
      }} />

      {/*<PatientListPage/>*/}
      {/*<Route path={`patients/:patientId`} render={() => {*/}
      {/*  return (*/}
      {/*    <div></div>*/}
      {/*  )*/}
      {/*}}/>*/}
    </>
  );
}

export default PatientPage;