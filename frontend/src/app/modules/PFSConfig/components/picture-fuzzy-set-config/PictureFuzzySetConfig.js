import React, { useEffect, useState } from "react";
import { ModalProgressBar } from "../../../../../_metronic/_partials/controls";
import { useFormik } from "formik";
import * as Yup from "yup";
import { getAllPictureFuzzySetsConfigs, savePictureFuzzySetConfigs } from "../../_redux/PFSConfigCrud";
import { toastify } from "../../../../utils/toastUtils";
import { NoRecordsFoundMessage, PleaseWaitMessage } from "../../../../../_metronic/_helpers";
import BootstrapTable from "react-bootstrap-table-next";
import PictureFuzzySetFormatter from "../column-formatters/PictureFuzzySetFormatter";

function PictureFuzzySetConfig(props) {
  const [rerender, setRerender] = useState(false);
  const [loading, setLoading] = useState(false);
  const [isValidCell, setIsValidCell] = useState(true);
  const [pfs, setPfs] = useState([]);

  useEffect(() => {
    setLoading(true);
    getAllPictureFuzzySetsConfigs()
      .then(res => {
        const pfsLst = res.data.data;
        console.log(transformPFS(pfsLst));
        setPfs(transformPFS(pfsLst));
        setLoading(false);
      })
      .catch(err => {
        setLoading(false);
        console.log(err);
        toastify.error("Error getting picture fuzzy sets");
      });
  }, [rerender]);

  const reverseFormPFS = (pfsLst) => {
    let result = [];
    for (let e of pfsLst) {
      for (let k in e) {
        if (k !== "id") {
          result.push({
            symptom: e.id,
            diagnose: k,
            picture_fuzzy_set: e[k]
          });
        }
      }
    }
    return result;
  };

  const transformPFS = (pfsLst) => {
    let result = [];
    pfsLst.forEach((e) => {
      const idx = result.findIndex(_element => _element.id === e["symptom"]);
      if (idx >= 0) { // update
        result[idx][e["diagnose"]] = {
          positive: e["pictureFuzzySet"].positive,
          neutral: e["pictureFuzzySet"].neutral,
          negative: e["pictureFuzzySet"].negative
        };
      } else { // push
        let obj = {
          id: e["symptom"]
        };
        obj[e["diagnose"]] = {
          positive: e["pictureFuzzySet"].positive,
          neutral: e["pictureFuzzySet"].neutral,
          negative: e["pictureFuzzySet"].negative
        };
        result.push(obj);
      }
    });
    return result;
  };

  const validatePfs = (pfs) => {
    for (const e of pfs) {
      for (let k in e) {
        if (k !== "id" && e[k]["positive"] + e[k]["neutral"] + e[k]["negative"] > 1.0) {
          console.log(e);
          console.log(k);
          setIsValidCell(false);
          toastify.error(`Error setting pfs in ${e.id} - ${k}`);
          return;
        }
      }
    }
    setIsValidCell(true);
  };

  const savePfsConfigs = () => {
    savePictureFuzzySetConfigs(reverseFormPFS(pfs))
      .then(r => {
        toastify.success("Save new picture fuzzy set configs succeed!");
        setRerender(!rerender);
      })
      .catch(err => {
        console.log(err);
        toastify.error("Save new picture fuzzy set configs failed!");
      });
  };

  // BEGIN TABLE COLUMN CONFIG-----------------------------------------
  const setPfsData = (idx, field, cellContent) => {
    let newPfs = [...pfs];
    newPfs[idx][field] = cellContent;
    setPfs(newPfs);
    validatePfs(newPfs);
  };

  const configsTableColumns = [
    {
      dataField: "id",
      text: ""
    },
    {
      dataField: "CHEST_PROBLEM",
      text: "CHEST_PROBLEM",
      formatter: PictureFuzzySetFormatter,
      formatExtraData: {
        setData: setPfsData,
        field: "CHEST_PROBLEM"
      },
      headerAlign: "center"
    },
    {
      dataField: "FEVER",
      text: "FEVER",
      formatter: PictureFuzzySetFormatter,
      formatExtraData: {
        setData: setPfsData,
        field: "FEVER"
      },
      headerAlign: "center"
    },
    {
      dataField: "MALARIA",
      text: "MALARIA",
      formatter: PictureFuzzySetFormatter,
      formatExtraData: {
        setData: setPfsData,
        field: "MALARIA"
      },
      headerAlign: "center"
    },
    {
      dataField: "STOMACH",
      text: "STOMACH",
      formatter: PictureFuzzySetFormatter,
      formatExtraData: {
        setData: setPfsData,
        field: "STOMACH"
      },
      headerAlign: "center"
    },
    {
      dataField: "TYPHOID",
      text: "TYPHOID",
      formatter: PictureFuzzySetFormatter,
      formatExtraData: {
        setData: setPfsData,
        field: "TYPHOID"
      },
      headerAlign: "center"
    }
  ];
  // BEGIN TABLE COLUMN CONFIG-----------------------------------------

  return (
    <>
      <div className="flex-row-fluid mt-5">
        <form className="card card-custom h-100">
          {loading && <ModalProgressBar />}

          {/* begin::Header */}
          <div className="card-header py-3">
            <div className="card-title align-items-start flex-column">
              <h3 className="card-label text-dark">
                2. Symptoms-Diseases pfs settings
              </h3>
              <span className="text-muted font-weight-bold font-size-sm mt-1">
                Change Symptoms-Diseases picture fuzzy relation settings
              </span>
            </div>
            <div className="card-toolbar">
              <div
                className="btn btn-success mr-2"
                onClick={savePfsConfigs}
              >
                Save Changes
              </div>
              <div
                className="btn btn-secondary"
              >
                Cancel
              </div>
            </div>
          </div>
          {/* end::Header */}
          {/* begin::Form */}
          <div className="form">
            <div className="card-body">
              {/* begin::Table  */}
              <BootstrapTable
                wrapperClasses="table-responsive"
                bordered={false}
                classes="table table-head-custom table-vertical-center overflow-hidden"
                bootstrap4
                keyField={"id"}
                data={pfs ? pfs : []}
                columns={configsTableColumns}
              >
                <PleaseWaitMessage entities={pfs} />
                <NoRecordsFoundMessage entities={pfs} />
              </BootstrapTable>
              {/*  end::Table */}
              {
                !isValidCell ?
                <span className="text-danger font-weight-bold font-size-sm mt-1">
                  {`Note: Sum of pfs (positive + neutral + negative) must be less than or equal 1`}
                </span> : ""
              }
            </div>
          </div>
          {/* end::Form */}
        </form>
      </div>
    </>
  );
}

export default PictureFuzzySetConfig;