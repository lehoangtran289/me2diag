import React, {useEffect, useState} from "react";
import {useSubheader} from "../../../_metronic/layout";
import PFSLinguisticDomainTable from "./components/PFSLinguisticDomainTable";
import PFSHedgeConfigCard from "./PFSHedgeConfigCard";

function PfsConfigPage(props) {
  const subheader = useSubheader();
  useEffect(() => {
    subheader.setTitle("PFS problem settings");
  })

  const [rerender, setRerender] = useState(false);
  const [loading, setLoading] = useState(false);

  return (
    <div className="d-flex flex-row">
      {/*BEGIN:: hedge config card*/}
      <PFSHedgeConfigCard
        rerender={rerender}
        setRerender={setRerender}
        loading={loading}
        setLoading={setLoading}
      />
      {/*END:: hedge config card*/}
      {/* BEGIN:: linguistic table domain */}
      <PFSLinguisticDomainTable
        rerender={rerender}
        loading={loading}
        setLoading={setLoading}
      />
      {/* END:: linguistic table domain */}
    </div>
  );
}

export default PfsConfigPage;