import React, { Suspense, useState } from "react";
import { Route, Switch, useHistory, useLocation, useRouteMatch } from "react-router-dom";
import ExaminationListPage from "./ExaminationListPage";
import { ContentRoute, LayoutSplashScreen } from "../../../_metronic/layout";
import PatientListPage from "../Patient/PatientListPage";
import PatientDetailPage from "../Patient/PatientDetailPage";
import AccountEditDialog from "../AccountMng/components/AccountEditDialog";
import ExaminationDetailPage from "./ExaminationDetailPage";

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
      <Switch>
        <ContentRoute path={`/examinations/:examinationId`} children={({match}) => {
          return <ExaminationDetailPage { ...props }/>
        }}/>
        <ContentRoute path={`/examinations`} render={() => {
          return (<ExaminationListPage rerenderFlag={rerenderFlag} setRerenderFlag={setRerenderFlag}/>);
        }} />
      </Switch>
    </Suspense>
  );
}

export default ExaminationPage;
