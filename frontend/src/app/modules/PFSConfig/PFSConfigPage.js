import React, {useEffect} from "react";
import {useSubheader} from "../../../_metronic/layout";
import PFSHedgeConfig from "./components/PFSHedgeConfig";
import PictureFuzzySetConfig from "./components/PictureFuzzySetConfig";

function PfsConfigPage(props) {
  const subheader = useSubheader();
  useEffect(() => {
    subheader.setTitle("PFS problem settings");
  })

  return (
    <>
      <div className="d-flex flex-row">
        {/*BEGIN:: hedge config card*/}
        <PFSHedgeConfig/>
      </div>
      <div className="d-flex flex-row mt-5">
        <PictureFuzzySetConfig/>
      </div>
    </>
  );
}

export default PfsConfigPage;