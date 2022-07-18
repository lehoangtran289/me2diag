import React, {useState} from 'react';
import PictureFuzzyConfigCard from "./picture-fuzzy-set-config/PictureFuzzyConfigCard";
import PictureFuzzyTable from "./picture-fuzzy-set-config/PictureFuzzyTable";

function PictureFuzzySetConfig(props) {
  const [rerender, setRerender] = useState(false);
  const [loading, setLoading] = useState(false);

  return (
    <>
      {/*BEGIN:: hedge config card*/}
      <PictureFuzzyConfigCard
        rerender={rerender}
        setRerender={setRerender}
        loading={loading}
        setLoading={setLoading}
      />
      {/*END:: hedge config card*/}
      {/* BEGIN:: linguistic table domain */}
      <PictureFuzzyTable
        rerender={rerender}
        setRerender={setRerender}
        loading={loading}
        setLoading={setLoading}
      />
      {/* END:: linguistic table domain */}
    </>
  );
}

export default PictureFuzzySetConfig;