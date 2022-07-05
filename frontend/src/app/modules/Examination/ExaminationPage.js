import React from 'react';
import {Route, useHistory, useRouteMatch} from "react-router-dom";
import ExaminationListPage from "./ExaminationListPage";

function ExaminationPage(props) {
  const {url} = useRouteMatch();
  const history = useHistory();

  return (
    <>
      <Route path={`${url}`} render={() => {
        return (<ExaminationListPage/>)
      }}/>

      <Route path={`${url}/:examinationId`} render={() => {
        return (
          <div></div>
        )
      }}/>

      {/*<Redirect to={"/error/error-v1"}/>*/}
    </>
  );
}

export default ExaminationPage;
