import React, {useState} from 'react';
import {Switch, useHistory, useLocation, useRouteMatch} from "react-router-dom";
import {ContentRoute} from "../../../_metronic/layout";
import PatientCreateDialog from "./components/PatientCreateDialog";
import PatientListPage from "./PatientListPage";
import PatientDeleteDialog from "./components/PatientDeleteDialog";
import PatientDetailPage from "./PatientDetailPage";

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
        <ContentRoute path={`${url}/new`} children={({match}) => {
          return (
            match &&
            <PatientCreateDialog
              show={Boolean(match)}
              onHide={onHide}
            />
          )
        }}/>
        <ContentRoute path={`${url}/:patientId/delete`} children={({match}) => {
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
        <ContentRoute exact path={`${url}/:patientId`} render={() => {
          return location.pathname === `${url}/new` ?
            <PatientListPage rerenderFlag={rerenderFlag} setRerenderFlag={setRerenderFlag}/> :
            <PatientDetailPage/>
        }}/>
        <ContentRoute path={`${url}`} children={({match}) => {
          return (
            <PatientListPage rerenderFlag={rerenderFlag} setRerenderFlag={setRerenderFlag}/>
          )
        }}/>
      </Switch>
    </>
  );
}

export default PatientPage;