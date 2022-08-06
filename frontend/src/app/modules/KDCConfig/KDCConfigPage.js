import React, {useEffect, useState} from "react";
import {useSubheader} from "../../../_metronic/layout";
import KDCLinguisticDomainTable from "./components/KDCLinguisticDomainTable";
import KDCHedgeConfigCard from "./KDCHedgeConfigCard";
import KDCDomainConfig from "./components/KDCDomainConfig";

function KdcConfigPage(props) {
  const subheader = useSubheader();
  useEffect(() => {
    subheader.setTitle("KDC problem settings");
  })
  const [rerender, setRerender] = useState(false);
  const [loading, setLoading] = useState(false);

  return (
    <div className={"container-fluid px-0"}>
      <div className="row px-0 mb-5">
        {/*BEGIN:: hedge config card*/}
        <KDCHedgeConfigCard
          rerender={rerender}
          setRerender={setRerender}
          loading={loading}
          setLoading={setLoading}
        />
        {/*END:: hedge config card*/}
        {/* BEGIN:: linguistic table domain */}
        <KDCLinguisticDomainTable
          rerender={rerender}
          loading={loading}
          setLoading={setLoading}
        />
        {/* END:: linguistic table domain */}
      </div>
      <div className="row px-0 mt-5">
        {/* BEGIN:: domain */}
        <KDCDomainConfig />
        {/* END:: domain */}
      </div>
    </div>
  );
}

export default KdcConfigPage;