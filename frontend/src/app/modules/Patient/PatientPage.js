import React,  { Suspense } from 'react';
import { Redirect, Route, Switch, useHistory, useRouteMatch } from "react-router-dom";
import PatientListPage from "./PatientListPage";
import { ContentRoute, LayoutSplashScreen } from "../../../_metronic/layout";

function PatientPage(props) {
  const {url} = useRouteMatch();
  const history = useHistory();

  return (
    <Suspense fallback={<LayoutSplashScreen />}>
      <Switch>
        <Route path={"/patients"} render={() => {
          return (<PatientListPage/>)
        }}/>

        <Route path={`patients/:patientId`} render={() => {
          return (
            <div></div>
          )
        }}/>
      </Switch>
    </Suspense>
  );
}

export default PatientPage;