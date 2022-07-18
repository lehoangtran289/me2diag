import React, {useEffect, useState} from 'react';
import {ModalProgressBar} from "../../../../_metronic/_partials/controls";
import {useFormik} from "formik";
import * as Yup from "yup";
import {getAllPictureFuzzySetsConfigs} from "../_redux/PFSConfigCrud";
import {toastify} from "../../../utils/toastUtils";
import {
  headerSortingClasses,
  NoRecordsFoundMessage,
  PleaseWaitMessage,
  sortCaret
} from "../../../../_metronic/_helpers";
import BootstrapTable from "react-bootstrap-table-next";

function PictureFuzzySetConfig(props) {
  const [rerender, setRerender] = useState(false);
  const [loading, setLoading] = useState(false);

  const [pfs, setPfs] = useState([]);

  useEffect(() => {
    setLoading(true);
    getAllPictureFuzzySetsConfigs()
      .then(res => {
        const pfsLst = res.data.data
        console.log(pfsLst);
        // setPfs(pfsLst);
        setLoading(false);
      })
      .catch(err => {
        setLoading(false);
        console.log(err);
        toastify.error("Error getting picture fuzzy sets");
      });
  }, [rerender]);

  const transformPFS = (pfsLst) => {
    let result = []
    pfsLst.forEach((e) => {
      const idx = pfsLst.findIndex(_element => _element.symptom === e.symptom)
      if (idx > -1) { // update
        // TODO:
        result[idx] = {
          ...result[idx],

        }
      } else { // push
        result.push({
          id: e.symptom,
          CHEST_PROBLEM: {
            positive: e.diagnose === 'CHEST_PROBLEM' ? e.pictureFuzzySet.positive : 0.0,
            neutral: e.diagnose === 'CHEST_PROBLEM' ? e.pictureFuzzySet.neutral : 0.0,
            negative: e.diagnose === 'CHEST_PROBLEM' ? e.pictureFuzzySet.negative : 0.0
          },
          FEVER: {
            positive: e.diagnose === 'FEVER' ? e.pictureFuzzySet.positive : 0.0,
            neutral: e.diagnose === 'FEVER' ? e.pictureFuzzySet.neutral : 0.0,
            negative: e.diagnose === 'FEVER' ? e.pictureFuzzySet.negative : 0.0
          },
          MALARIA: {
            positive: e.diagnose === 'MALARIA' ? e.pictureFuzzySet.positive : 0.0,
            neutral: e.diagnose === 'MALARIA' ? e.pictureFuzzySet.neutral : 0.0,
            negative: e.diagnose === 'MALARIA' ? e.pictureFuzzySet.negative : 0.0
          },
          STOMACH: {
            positive: e.diagnose === 'STOMACH' ? e.pictureFuzzySet.positive : 0.0,
            neutral: e.diagnose === 'STOMACH' ? e.pictureFuzzySet.neutral : 0.0,
            negative: e.diagnose === 'STOMACH' ? e.pictureFuzzySet.negative : 0.0
          },
          TYPHOID: {
            positive: e.diagnose === 'TYPHOID' ? e.pictureFuzzySet.positive : 0.0,
            neutral: e.diagnose === 'TYPHOID' ? e.pictureFuzzySet.neutral : 0.0,
            negative: e.diagnose === 'TYPHOID' ? e.pictureFuzzySet.negative : 0.0
          }
        })
      }
    });
    console.log(result);
  }

  // BEGIN:: Formik form config
  const savePfsConfigs = (values, setStatus, setSubmitting) => {}

  const initialValues = {};

  const Schema = Yup.object().shape({});

  const formik = useFormik({
    initialValues: initialValues,
    validationSchema: Schema,
    enableReinitialize: true,
    onSubmit: (values, {setStatus, setSubmitting}) => {
    },
  });
  // END:: Formik form config

  // BEGIN TABLE COLUMN CONFIG-----------------------------------------
  const configsTableColumns = [
    {
      dataField: "id",
      text: "Config",
    },
    {
      dataField: "CHEST_PROBLEM",
      text: "CHEST_PROBLEM",
    },
    {
      dataField: "FEVER",
      text: "FEVER",
    },
    {
      dataField: "MALARIA",
      text: "MALARIA",
    },
    {
      dataField: "STOMACH",
      text: "STOMACH",
    },
    {
      dataField: "TYPHOID",
      text: "TYPHOID"
    }
  ];
  // BEGIN TABLE COLUMN CONFIG-----------------------------------------

  return (
    <>
      <div className="flex-row-fluid mt-5">
        <form className="card card-custom h-100" onSubmit={formik.handleSubmit}>
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
              <button
                type="submit"
                className="btn btn-success mr-2"
                disabled={
                  formik.isSubmitting || (formik.touched && !formik.isValid)
                }
              >
                Save Changes
                {formik.isSubmitting}
              </button>
              <div
                className="btn btn-secondary"
                onClick={() => {
                  setRerender(!rerender);
                  formik.resetForm();
                }}
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
            </div>
          </div>
          {/* end::Form */}
        </form>
      </div>
    </>
  )
}

export default PictureFuzzySetConfig;