import React, { Suspense } from "react";
import { Route, Switch, useHistory, useRouteMatch } from "react-router-dom";
import ExaminationListPage from "./ExaminationListPage";
import { LayoutSplashScreen } from "../../../_metronic/layout";

function ExaminationPage(props) {
  const { url } = useRouteMatch();
  const history = useHistory();

  return (
    <Suspense fallback={<LayoutSplashScreen />}>
      <Switch>
        <Route path={`/examinations`} render={() => {
          return (<ExaminationListPage />);
        }} />

        <Route path={`/examinations/:examinationId`} render={() => {
          return (
            <div></div>
          );
        }} />
      </Switch>
    </Suspense>
  );
}

export default ExaminationPage;
