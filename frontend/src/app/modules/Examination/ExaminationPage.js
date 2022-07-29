import React, { Suspense, useState } from "react";
import { Route, Switch, useHistory, useLocation, useRouteMatch } from "react-router-dom";
import ExaminationListPage from "./ExaminationListPage";
import { LayoutSplashScreen } from "../../../_metronic/layout";

function ExaminationPage(props) {
  const { url } = useRouteMatch();
  const history = useHistory();
  const [rerenderFlag, setRerenderFlag] = useState(false);

  const location = useLocation();
  console.log(location.pathname);

  const onHide = async () => {
    await setRerenderFlag(true);
    history.push("/patients");
  }

  return (
    <Suspense fallback={<LayoutSplashScreen />}>
      {/*<Switch>*/}
      {/*  <Route path={`/examinations/:examinationId`} render={() => {*/}
      {/*    return (*/}
      {/*      <div></div>*/}
      {/*    );*/}
      {/*  }} />*/}
      {/*</Switch>*/}
      <Switch>
        <Route path={`/examinations`} render={() => {
          return (<ExaminationListPage rerenderFlag={rerenderFlag} setRerenderFlag={setRerenderFlag}/>);
        }} />
      </Switch>
    </Suspense>
  );
}

export default ExaminationPage;
