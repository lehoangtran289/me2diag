import React, { useEffect } from "react";
import { ContentRoute, useSubheader } from "../../../_metronic/layout";
import { Redirect, Route, Switch, useParams, useRouteMatch } from "react-router-dom";
import PatientCard from "./components/diagnosis-page/PatientCard";
import PFSDiagnosis from "./components/diagnosis-page/PFSDiagnosis";
import KDCDiagnosis from "./components/diagnosis-page/KDCDiagnosis";
import { useState } from "react";

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
            to={`${url}/pfs`}
          />
          <ContentRoute path={`${url}/pfs`} children={({match}) => {
            return (
              <PFSDiagnosis patientId={patientId} { ...props }/>
            )
          }}/>
          <Route
            path={`${url}/kdc`}
            component={KDCDiagnosis}
          />
        </Switch>
      </div>
    </div>
  );
}

export default PatientDetailPage;