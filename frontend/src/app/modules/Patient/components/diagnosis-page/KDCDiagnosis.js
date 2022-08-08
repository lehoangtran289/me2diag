import React, { useEffect, useState } from "react";
import { toastify } from "../../../../utils/toastUtils";
import {
  getAllKDCDomainConfigs,
  getAllKDCLinguisticDomainConfigs
} from "../../../KDCConfig/_redux/KDCConfigCrud";
import { capitalizeFirstLetter } from "../../../../utils/common";
import BootstrapTable from "react-bootstrap-table-next";
import { NoRecordsFoundMessage, PleaseWaitMessage } from "../../../../../_metronic/_helpers";
import { diagnoseKDC } from "../../_redux/patientCrud";
import KDCTypeColumnFormatter from "../../column-formatters/KDCTypeColumnFormatter";
import KDCValueColumnFormatter from "../../column-formatters/KDCValueColumnFormatter";
import KDCResultFormatter from "../../column-formatters/KDCResultFormatter";
import {useHistory} from "react-router-dom";

export const icdData = [
  { code: "N00", name: "None", description: "No disease found" },
  { code: "N04", name: "Nephrotic syndrome", description: "Congenital nephrotic syndrome and lipoid nephrosis" },
  { code: "N17", name: "Acute renal failure", description: "Acute renal impairment" },
  { code: "N18", name: "Chronic renal failure", description: "Chronic renal failure and additional code" +
      " from N18.1 to N18.9 for underlying disease" +
      " or presence of hypertension." },
]

function KdcDiagnosis({ patientId, ...props }) {
  const history = useHistory();
  const [rerender, setRerender] = useState(false);
  const [loading, setLoading] = useState(false);
  const [linguisticDomain, setLinguisticDomain] = useState({});
  const [domainConfigs, setDomainConfigs] = useState([]);
  const [result, setResult] = useState(null);
  const [KDCData, setKDCData] = useState([
    { field: "WBC", value: 50.0, min: 0.0, max: 0.0, description: "", isLinguistic: false },
    { field: "LY", value: 40.0, min: 0.0, max: 0.0, description: "", isLinguistic: false },
    { field: "NE", value: 36.0, min: 0.0, max: 0.0, description: "",  isLinguistic: false },
    { field: "RBC", value: 78.0, min: 0.0, max: 0.0, description: "",  isLinguistic: false },
    { field: "HGB", value: 100.0, min: 0.0, max: 0.0, description: "",   isLinguistic: false },
    { field: "HCT", value: 40.0, min: 0.0, max: 0.0, description: "",   isLinguistic: false },
    { field: "PLT", value: 40.0, min: 0.0, max: 0.0, description: "",   isLinguistic: false },
    { field: "Na", value: 150.0, min: 0.0, max: 0.0, description: "",   isLinguistic: false },
    { field: "K", value: 60.0, min: 0.0, max: 0.0, description: "",   isLinguistic: false },
    { field: "total_protein", value: "VERY HIGH", min: 0.0, max: 0.0, description: "", isLinguistic: true },
    { field: "Albumin", value: 47.0, min: 0.0, max: 0.0, description: "",  isLinguistic: false },
    { field: "Ure", value: 68.0, min: 0.0, max: 0.0, description: "",  isLinguistic: false },
    { field: "Creatinin", value: "MORE HIGH", min: 0.0, max: 0.0, description: "", isLinguistic: true },
  ]);

  const convertLinguistic = (data) => {
    let res = {};
    data.forEach((e) => {
      const field = capitalizeFirstLetter(e["linguistic_domain_element"].toLowerCase().replace("_", " "));
      res[field] = e["vvalue"];
    });
    return res;
  };

  useEffect(() => {
    setLoading(true);
    getAllKDCLinguisticDomainConfigs()
      .then(res => {
        setLinguisticDomain(convertLinguistic(res.data.data));
        setLoading(false);
      })
      .catch(err => {
        setLoading(false);
        console.log(err);
        toastify.error("Error fetching linguistic domain configs");
      });
    setLoading(true);
    getAllKDCDomainConfigs()
      .then(res => {
        setDomainConfigs(res.data.data);
        mergeDomainConfigs(res.data.data);
        setLoading(false);
      })
      .catch(err => {
        setLoading(false);
        console.log(err);
        toastify.error("Error getting domain configs");
      });
  }, []);

  const mergeDomainConfigs = (data) => {
    let newKDCData = [...KDCData]
    newKDCData.forEach((e) => {
      const obj = data.find(o => o.name === e.field.toUpperCase())
      e.max = obj.max;
      e.min = obj.min;
      e.description = obj.description
    })
    setKDCData(newKDCData)
  }

  const convertResult = (data) => {
    return {
      id: data["examinationId"],
      ...icdData.find(e => e.code === data["result"])
    };
  };

  const diagnoseWithKDC = () => {
    diagnoseKDC(patientId, KDCData)
      .then(r => {
        console.log(r.data);
        setResult(convertResult(r.data.data));
        setRerender(!rerender);
      })
      .catch(err => {
        console.log(err);
        toastify.error("Diagnose failed!");
      });
  };

  const handleResetForm = () => {
    let newData = [...KDCData];
    newData.forEach((e) => {
      e.value = 0.0
      e.isLinguistic = false
    })
    setKDCData(newData)
  }

  useEffect(() => {
    console.log(KDCData);
  }, [KDCData])

  // BEGIN TABLE CONFIG-----------------------------------------
  const setData = (idx, field, data) => {
    let newKDCData = [...KDCData];
    newKDCData[idx][field] = data;
    setKDCData(newKDCData);
  };

  const setInputType = (idx, field, data) => {
    let newKDCData = [...KDCData];
    newKDCData[idx][field] = data;
    if (data) {
      console.log(linguisticDomain);
      newKDCData[idx]["value"] = 'VERY LOW'
    }
    else newKDCData[idx]["value"] = 0.0
    setKDCData(newKDCData);
  };

  const symptomTableColumns = [
    {
      dataField: "field",
      text: "Measurement",
      headerAlign: "center",
      align: "center",
      headerStyle: (colum, colIndex) => {
        return { width: '10em' };
      },
      formatter: (cellContent, row, rowIndex) => {
        return (
          <div className={"font-weight-bold"}>
            {`${cellContent.replace('_', " ").toUpperCase()}`}
          </div>
        )
      },
    },
    {
      dataField: "description",
      text: "Description",
      headerAlign: "center",
      align: "center",
      headerStyle: (colum, colIndex) => {
        return { width: '16em' };
      },
    },
    {
      dataField: "min",
      text: "Minimum",
      headerAlign: "center",
      align: "center",
      headerStyle: (colum, colIndex) => {
        return { width: '10em' };
      },
    },
    {
      dataField: "max",
      text: "Maximum",
      headerAlign: "center",
      align: "center",
      headerStyle: (colum, colIndex) => {
        return { width: '10em' };
      },
    },
    {
      dataField: "isLinguistic",
      text: "Is linguistic value?",
      headerAlign: "center",
      align: "center",
      headerStyle: (colum, colIndex) => {
        return { width: '13em' };
      },
      formatter: KDCTypeColumnFormatter,
      formatExtraData: {
        setData: setInputType,
      },
    },
    {
      dataField: "value",
      text: "Value",
      headerAlign: "center",
      headerStyle: (colum, colIndex) => {
        return { width: '13em' };
      },
      formatter: KDCValueColumnFormatter,
      formatExtraData: {
        setData: setData,
        linguisticDomain: linguisticDomain
      },
    },
  ]

  const resultTableColumns = [
    {
      dataField: "id",
      text: "Examination ID",
      headerStyle: (colum, colIndex) => {
        return { width: '22em' };
      }
    },
    {
      dataField: "code",
      text: "ICD Code",
      headerStyle: (colum, colIndex) => {
        return { width: '10em' };
      },
      headerAlign: "center",
      align: "center",
      formatter: KDCResultFormatter
    },
    {
      dataField: "name",
      text: "Disease name",
      headerAlign: "center",
      align: "center",
      headerStyle: (colum, colIndex) => {
        return { width: '13em' };
      }
    },
    {
      dataField: "description",
      text: "Description",
      headerStyle: (colum, colIndex) => {
        return { width: '22em' };
      }
    }
  ]

  // END TABLE CONFIG ------------------------------------------

  return (
    <>
      <form className="card card-custom">
        {/* begin::Header */}
        <div className="card-header py-3">
          <div className="card-title align-items-start flex-column">
            <h3 className="card-label font-weight-bolder text-dark">
              Kidney diseases diagnosis
            </h3>
            <span className="text-muted font-weight-bold font-size-sm mt-1">
              Diagnose patient's kidney-related disease based on given measurements
            </span>
          </div>
        </div>
        {/* end::Header */}
        {/* begin::Form */}
        <div className="form">
          <div className="card-body">
            <BootstrapTable
              wrapperClasses="table-responsive"
              classes="table table-head-custom table-vertical-center overflow-hidden"
              bootstrap4
              bordered={true}
              keyField={"field"}
              data={KDCData ? KDCData : []}
              columns={symptomTableColumns}
            >
              <PleaseWaitMessage entities={KDCData} />
              <NoRecordsFoundMessage entities={KDCData} />
            </BootstrapTable>
            <div className="card-toolbar mt-3">
              <div
                className="btn btn-success mr-2"
                onClick={diagnoseWithKDC}
              >
                Diagnose
              </div>
              <div
                className="btn btn-secondary ml-1"
                onClick={handleResetForm}
              >
                Reset
              </div>
            </div>
          </div>
        </div>
        {/* end::Form */}
      </form>
      <div className="card card-custom mt-5">
        {/* begin::Header */}
        <div className="card-header py-3">
          <div className="card-title align-items-start flex-column">
            <h3 className="card-label font-weight-bolder text-dark">
              Diagnosis result
            </h3>
            <span className="text-muted font-weight-bold font-size-sm mt-1">
              Kidney diseases diagnosis possible result
            </span>
          </div>
          {
            result &&
            <div className="card-toolbar mt-3">
              <div
                className="btn btn-success mr-2 no-print"
                onClick={() => history.push(`/examinations/` + result["id"])}
              >
                Examination details
              </div>
            </div>
          }
        </div>
        {/* end::Header */}
        {/* begin::Body*/}
        <div className="card-body">
          <BootstrapTable
            wrapperClasses="table-responsive"
            classes="table table-head-custom table-vertical-center overflow-hidden"
            bootstrap4
            keyField={"id"}
            data={result ? [result] : []}
            columns={resultTableColumns}
          >
            <PleaseWaitMessage entities={[result]} />
            <NoRecordsFoundMessage entities={[result]} />
          </BootstrapTable>
        </div>
        {/* end::Body*/}
      </div>
    </>
  );
}

export default KdcDiagnosis;