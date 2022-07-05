import React from 'react';
import {Route, useHistory, useRouteMatch} from "react-router-dom";
import PatientListPage from "./PatientListPage";

function PatientPage(props) {
  const {url} = useRouteMatch();
  const history = useHistory();

  return (
    <>
      <Route path={`${url}`} render={() => {
        return (<PatientListPage/>)
      }}/>

      <Route path={`${url}/:patientId`} render={() => {
        return (
          <div></div>
        )
      }}/>

      {/*<Redirect to={"/error/error-v1"}/>*/}
    </>
  );
}

export default PatientPage;