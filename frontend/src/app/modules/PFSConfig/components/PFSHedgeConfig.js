import React, {useState} from 'react';
import PFSHedgeConfigCard from "./hedge-config/PFSHedgeConfigCard";
import PFSLinguisticDomainTable from "./hedge-config/PFSLinguisticDomainTable";

function PfsHedgeConfig(props) {
  const [rerender, setRerender] = useState(false);
  const [loading, setLoading] = useState(false);

  return (
    <>
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
    </>
  );
}

export default PfsHedgeConfig;