import React, {useEffect, useState} from "react";
import {NoRecordsFoundMessage, PleaseWaitMessage} from "../../../../../_metronic/_helpers";
import BootstrapTable from "react-bootstrap-table-next";
import {toastify} from "../../../../utils/toastUtils";
import {diagnosePFS} from "../../_redux/patientCrud";
import {getAllPFSLinguisticDomainConfigs} from "../../../PFSConfig/_redux/PFSConfigCrud";
import PFSDiagnoseFormatter from "../../column-formatters/PFSDiagnoseFormatter";
import {capitalizeFirstLetter} from "../../../../utils/common";
import PFSResultFormatter from "../../column-formatters/PFSResultFormatter";
import {useHistory} from "react-router-dom";

function PfsDiagnosis({patientId, ...props}) {
  const history = useHistory();

  const [rerender, setRerender] = useState(false);
  const [loading, setLoading] = useState(false);
  const [isValid, setIsValid] = useState({
    status: true,
    field: ""
  });
  const [linguisticDomain, setLinguisticDomain] = useState({});
  const [pfs, setPfs] = useState([
    {
      symptom: "Temperature"
    },
    {
      symptom: "Headache"
    },
    {
      symptom: "Stomach Pain"
    },
    {
      symptom: "Cough"
    },
    {
      symptom: "Chest Pain"
    }
  ]);
  const [result, setResult] = useState({
    id: ""
  });

  useEffect(() => {
    setLoading(true);
    getAllPFSLinguisticDomainConfigs()
      .then(res => {
        setLinguisticDomain(convertLinguistic(res.data.data));
        mockPFSData(convertLinguistic(res.data.data));
        setLoading(false);
      })
      .catch(err => {
        setLoading(false);
        console.log(err);
        toastify.error("Error fetching linguistic domain configs");
      });
  }, []);

  useEffect(() => {
    console.log(pfs);
    console.log(result);
  }, [pfs, result]);

  const mockPFSData = (data) => {
    setPfs([
      {
        symptom: "Temperature",
        positive: data["Medium"],
        neutral: data["Low"],
        negative: data["Low"]
      },
      {
        symptom: "Headache",
        positive: data["None"],
        neutral: data["High"],
        negative: data["Low"]
      },
      {
        symptom: "Stomach Pain",
        positive: data["Slightly high"],
        neutral: data["Very low"],
        negative: data["Low"]
      },
      {
        symptom: "Cough",
        positive: data["Very low"],
        neutral: data["Slightly high"],
        negative: data["Very low"]
      },
      {
        symptom: "Chest Pain",
        positive: data["Slightly low"],
        neutral: data["Slightly low"],
        negative: data["Low"]
      }
    ])
  }

  const convertLinguistic = (data) => {
    let res = {
      "None": 0.0,
      "Very low": 0.0,
      "Low": 0.0,
      "Slightly low": 0.0,
      "Medium": 0.0,
      "Slightly high": 0.0,
      "High": 0.0,
      "Very High": 0.0,
      "Completely": 0.0
    };
    data.forEach((e) => {
      const field = e.linguistic_domain_element.toLowerCase().replace("_", " ");
      for (let key in res) {
        if (key.toLowerCase() === field) {
          res[key] = e.vvalue;
          break;
        }
      }
    });
    return res;
  };

  const convertResult = (data) => {
    let res = {
      id: data["examinationId"]
    };
    data["result"].forEach((e) => {
      for (let k in e) {
        const field = capitalizeFirstLetter(k.toLowerCase().replace("_", " "));
        res[field] = Number(e[k] * 100).toFixed(2);
      }
    });
    return res;
  };

  const diagnoseWithPFS = () => {
    diagnosePFS(patientId, pfs)
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
    let newPfs = [...pfs]
    newPfs.forEach((e) => {
      e["positive"] = 0.0
      e["neutral"] = 0.0
      e["negative"] = 0.0
    });
    setPfs(newPfs)
  }

  // BEGIN TABLE COLUMN CONFIG-----------------------------------------
  const validatePfs = (pfs) => {
    for (const e of pfs) {
      const sum = Number(e["positive"]) + Number(e["neutral"]) + Number(e["negative"]);
      if (sum > 1.0) {
        setIsValid({
          status: false,
          field: e["symptom"]
        });
        return;
      }
    }
    setIsValid({
      status: true,
      field: ""
    });
  };

  const setPfsData = (idx, field, vvalue) => {
    let newPfs = [...pfs];
    newPfs[idx][field] = Number(vvalue);
    setPfs(newPfs);
    validatePfs(newPfs);
  };

  const symptomTableColumns = [
    {
      dataField: "symptom",
      text: "Symptom"
    },
    {
      dataField: "positive",
      text: "Positive",
      formatter: PFSDiagnoseFormatter,
      formatExtraData: {
        setData: setPfsData,
        field: "positive",
        data: pfs,
        linguisticDomain: linguisticDomain
      },
      headerAlign: "center"
    },
    {
      dataField: "neutral",
      text: "Neutral",
      formatter: PFSDiagnoseFormatter,
      formatExtraData: {
        setData: setPfsData,
        field: "neutral",
        linguisticDomain: linguisticDomain
      },
      headerAlign: "center"
    },
    {
      dataField: "negative",
      text: "Negative",
      formatter: PFSDiagnoseFormatter,
      formatExtraData: {
        setData: setPfsData,
        field: "negative",
        linguisticDomain: linguisticDomain
      },
      headerAlign: "center"
    }
  ];

  const resultTableColumns = [
    {
      dataField: "id",
      text: "Examination ID",
      headerStyle: (colum, colIndex) => {
        return {width: '22em'};
      }
    },
    {
      dataField: "Fever",
      text: "Fever",
      headerAlign: "center",
      align: "center",
      headerStyle: (colum, colIndex) => {
        return {width: '13.5em'};
      },
      formatter: PFSResultFormatter,
    },
    {
      dataField: "Malaria",
      text: "Malaria",
      headerAlign: "center",
      align: "center",
      headerStyle: (colum, colIndex) => {
        return {width: '13.5em'};
      },
      formatter: PFSResultFormatter,
    },
    {
      dataField: "Typhoid",
      text: "Typhoid",
      headerAlign: "center",
      align: "center",
      headerStyle: (colum, colIndex) => {
        return {width: '13.5em'};
      },
      formatter: PFSResultFormatter,
    },
    {
      dataField: "Stomach",
      text: "Stomach",
      headerAlign: "center",
      align: "center",
      headerStyle: (colum, colIndex) => {
        return {width: '13.5em'};
      },
      formatter: PFSResultFormatter,
    },
    {
      dataField: "Chest problem",
      text: "Chest problem",
      headerAlign: "center",
      align: "center",
      formatter: PFSResultFormatter,
      headerStyle: (colum, colIndex) => {
        return {width: '16.5em'};
      },
    },
  ]

  // BEGIN TABLE COLUMN CONFIG-----------------------------------------

  return (
    <>
      <form className="card card-custom">
        {/* begin::Header */}
        <div className="card-header py-3">
          <div className="card-title align-items-start flex-column">
            <h3 className="card-label font-weight-bolder text-dark">
              Common diseases diagnosis
            </h3>
            <span className="text-muted font-weight-bold font-size-sm mt-1">
              Diagnose patient based on regular symptoms
            </span>
          </div>
        </div>
        {/* end::Header */}
        {/* begin::Form */}
        <div className="form">
          <div className="card-body">
            <BootstrapTable
              wrapperClasses="table-responsive"
              bordered={false}
              classes="table table-head-custom table-vertical-center overflow-hidden"
              bootstrap4
              keyField={"symptom"}
              data={pfs ? pfs : []}
              columns={symptomTableColumns}
            >
              <PleaseWaitMessage entities={pfs}/>
              <NoRecordsFoundMessage entities={pfs}/>
            </BootstrapTable>
            {
              !isValid.status ?
                <span className="text-danger font-weight-bold font-size-sm mt-1">
                  {`Note: Invalid PFS at "${isValid.field}". Sum of pfs (positive + neutral + negative) must be less than or equal 1`}
                </span> : ""
            }
            <div className="card-toolbar mt-3">
              <div
                className="btn btn-success mr-2 no-print"
                onClick={diagnoseWithPFS}
              >
                Diagnose
              </div>
              <div
                className="btn btn-secondary ml-1 no-print"
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
              Affection probability on 5 common diseases
            </span>
          </div>
          {
            result["id"] &&
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
            <PleaseWaitMessage entities={[result]}/>
            <NoRecordsFoundMessage entities={[result]}/>
          </BootstrapTable>
        </div>
        {/* end::Body*/}
      </div>
    </>
  );
}

export default PfsDiagnosis;