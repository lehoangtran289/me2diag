import React, { useEffect, useState } from "react";
import { ContentRoute, useSubheader } from "../../../_metronic/layout";
import { Redirect, Switch, useParams, useRouteMatch } from "react-router-dom";
import PatientCard from "./components/diagnosis-page/PatientCard";
import PFSDiagnosis from "./components/diagnosis-page/PFSDiagnosis";
import KDCDiagnosis from "./components/diagnosis-page/KDCDiagnosis";
import PatientExamsPage from "./components/diagnosis-page/PatientExamsTable";

function PatientDetailPage({ ...props }) {
  const { patientId } = useParams();
  const { url } = useRouteMatch();
  const subheader = useSubheader();

  const [rerender, setRerender] = useState(false);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    console.log(url);
    subheader.setTitle("Patient Details");
  });

  return (
    <div className="container-fluid px-0">
      <div className={"row px-0"}>
        <PatientCard
          rerender={rerender}
          setRerender={setRerender}
          loading={loading}
          setLoading={setLoading}
          {...props}
        />
        <div className="col-lg-8 col-sm-12">
          <Switch>
            <Redirect
              from={`${url}`}
              exact={true}
              to={`${url}/diagnose/pfs`}
            />
            <ContentRoute path={`${url}/diagnose/pfs`} children={({ match }) => {
              return (
                <PFSDiagnosis patientId={patientId} {...props} />
              );
            }} />
            <ContentRoute path={`${url}/diagnose/kdc`} children={({ match }) => {
              return (
                <KDCDiagnosis patientId={patientId} {...props} />
              );
            }} />
            <ContentRoute path={`${url}/exams`} children={({ match }) => {
              return (
                <PatientExamsPage patientId={patientId}
                                  rerenderFlag={rerender}
                                  setRerenderFlag={setRerender}
                                  {...props} />
              );
            }} />
          </Switch>
        </div>
      </div>
    </div>
  );
}

export default PatientDetailPage;