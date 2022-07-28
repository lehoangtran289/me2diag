import React from "react";
import BootstrapSwitchButton from "bootstrap-switch-button-react";
import { debounce } from "../../../utils/debounce";

function KdcTypeColumnFormatter(cellContent, row, rowIndex, { setData }) {
  const handleToggle = (checked) => {
    setData(rowIndex, "isLinguistic", checked)
  }

  return (
    <div className={"d-flex align-items-center justify-content-center"}>
      <BootstrapSwitchButton
        checked={cellContent}
        onstyle={"success"}
        onlabel="True"
        offlabel="False"
        width={100}
        onChange={debounce((checked) => {
          handleToggle(checked);
        }, 300)}
      />
    </div>
  );
}

export default KdcTypeColumnFormatter;