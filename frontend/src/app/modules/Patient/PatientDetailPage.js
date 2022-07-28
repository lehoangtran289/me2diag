import React, { useEffect, useState } from "react";
import { ContentRoute, useSubheader } from "../../../_metronic/layout";
import { Redirect, Switch, useParams, useRouteMatch } from "react-router-dom";
import PatientCard from "./components/diagnosis-page/PatientCard";
import PFSDiagnosis from "./components/diagnosis-page/PFSDiagnosis";
import KDCDiagnosis from "./components/diagnosis-page/KDCDiagnosis";
import PatientExamsTable from "./components/diagnosis-page/PatientExamsTable";

function PatientDetailPage({ ...props }) {
  const { patientId } = useParams();
  const {url} = useRouteMatch();
  const subheader = useSubheader();

  const [rerender, setRerender] = useState(false);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    console.log(url);
    subheader.setTitle("Patient Details");
  })

  return (
    <div className="d-flex flex-row">
      <PatientCard
        rerender={rerender}
        setRerender={setRerender}
        loading={loading}
        setLoading={setLoading}
        { ...props }
      />
      <div className="flex-row-fluid ml-lg-8">
        <Switch>
          <Redirect
            from={`${url}`}
            exact={true}
            to={`${url}/diagnose/pfs`}
          />
          <ContentRoute path={`${url}/diagnose/pfs`} children={({match}) => {
            return (
              <PFSDiagnosis patientId={patientId} { ...props }/>
            )
          }}/>
          <ContentRoute path={`${url}/diagnose/kdc`} children={({match}) => {
            return (
              <KDCDiagnosis patientId={patientId} { ...props }/>
            )
          }}/>
          <ContentRoute path={`${url}/examinations`} children={({match}) => {
            return (
              <PatientExamsTable patientId={patientId} { ...props }/>
            )
          }}/>
        </Switch>
      </div>
    </div>
  );
}

export default PatientDetailPage;