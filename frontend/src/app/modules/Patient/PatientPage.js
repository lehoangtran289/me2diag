import React, {useState} from 'react';
import {Switch, useHistory, useLocation, useRouteMatch} from "react-router-dom";
import {ContentRoute} from "../../../_metronic/layout";
import PatientCreateDialog from "./components/dialogs/PatientCreateDialog";
import PatientListPage from "./PatientListPage";
import PatientDeleteDialog from "./components/dialogs/PatientDeleteDialog";
import PatientDetailPage from "./PatientDetailPage";
import PatientEditDialog from "./components/dialogs/PatientEditDialog";

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
        <ContentRoute path={`${url}/:patientId/edit`} children={({match}) => {
          return (
            match &&
            <PatientEditDialog
              show={Boolean(match)}
              onHide={onHide}
              {...props}
            />
          )
        }}/>
      </Switch>

      {/*TODO: workaround this routing since url/:patientId ~ url/new :<*/}
      <Switch>
        <ContentRoute exact path={`${url}/:patientId`} render={() => {
          if (location.pathname === `${url}/new`)
            return <PatientListPage rerenderFlag={rerenderFlag} setRerenderFlag={setRerenderFlag}/>
          else
            return <PatientDetailPage { ...props }/>
        }}/>
        <ContentRoute path={`${url}/:patientId/`} children={({match}) => {
          if (["delete", "edit"].some(v => location.pathname.includes(v)))
            return <PatientListPage rerenderFlag={rerenderFlag} setRerenderFlag={setRerenderFlag}/>
          else
            return <PatientDetailPage { ...props }/>
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