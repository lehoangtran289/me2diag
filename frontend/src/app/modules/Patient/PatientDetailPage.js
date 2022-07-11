import React, {useEffect} from 'react';
import {useSubheader} from "../../../_metronic/layout";

function PatientDetailPage(props) {
  const subheader = useSubheader();

  useEffect(() => {
    subheader.setTitle("Patient Details");
  })

  return (
    <div>hello world</div>
  );
}

export default PatientDetailPage;