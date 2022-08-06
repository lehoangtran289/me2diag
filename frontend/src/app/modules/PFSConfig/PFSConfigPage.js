import React, {useEffect} from "react";
import {useSubheader} from "../../../_metronic/layout";
import PFSHedgeConfig from "./components/hedge-config/PFSHedgeConfig";
import PictureFuzzySetConfig from "./components/picture-fuzzy-set-config/PictureFuzzySetConfig";

function PfsConfigPage(props) {
  const subheader = useSubheader();
  useEffect(() => {
    subheader.setTitle("PFS problem settings");
  })

  return (
    <div className={"container-fluid px-0"}>
      <div className="row px-0 mb-5">
        {/*BEGIN:: hedge config card*/}
        <PFSHedgeConfig/>
      </div>
      <div className="row px-0 mt-5">
        <PictureFuzzySetConfig/>
      </div>
    </div>
  );
}

export default PfsConfigPage;