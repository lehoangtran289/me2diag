import React, {useEffect, useState} from 'react';
import {ModalProgressBar} from "../../../../../_metronic/_partials/controls";
import {toastify} from "../../../../utils/toastUtils";
import {getAllPFSHedgeConfigs, savePFSHedgeConfigs} from "../../_redux/PFSConfigCrud";
import * as Yup from "yup";
import {useFormik} from "formik";
import KDCHedgeConfigSlider from "../../../KDCConfig/components/KDCHedgeConfigSlider";

function PFSHedgeConfigCard({ loading, setLoading, rerender, setRerender, ...props }) {
  const [hedgeConfigs, setHedgeConfigs] = useState({
    neutral_theta: 0.0,
    SLIGHTLY: 0.0,
    VERY: 0.0
  });

  useEffect(() => {
    // get hedge configs
    setLoading(true);
    getAllPFSHedgeConfigs()
      .then(res => {
        const configs = res.data.data
        let curHedgeConfig = {
          neutral_theta: 0.0,
          SLIGHTLY: 0.0,
          VERY: 0.0
        }
        configs.forEach((config) => {
          switch(config['hedge_algebra_enum']) {
            case 'SLIGHTLY':
              curHedgeConfig["SLIGHTLY"] = config['fm_value']
              break;
            case 'MEDIUM':
              curHedgeConfig["neutral_theta"] = config['fm_value']
              break;
            case 'VERY':
              curHedgeConfig["VERY"] = config['fm_value']
              break;
          }
        });
        setHedgeConfigs(curHedgeConfig);
        setLoading(false);
      })
      .catch(err => {
        setLoading(false);
        console.log(err);
        toastify.error("Error getting linguistic domain configs");
      });
  }, [rerender]);

  // BEGIN FORMIK FORM CONFIG-----------------------------------------
  const initialValues = hedgeConfigs;

  const saveConfigs = (values, setStatus, setSubmitting) => {
    if (curHedgeSum() !== 1.0) {
      toastify.error("Error setting PFS hedge configs configs, sum hedge configs != 1.0");
      return;
    }
    console.log(values);
    setLoading(true);
    savePFSHedgeConfigs(values)
      .then(res => {
        setLoading(false);
        setRerender(!rerender);
        toastify.success("set PFS hedge configs succeed!");
      })
      .catch(err => {
        setLoading(false);
        console.log(err);
        toastify.error("Error setting PFS hedge configs configs");
      });
  };

  const Schema = Yup.object().shape({
    neutral_theta: Yup.number().min(0.0).max(1.0),
    SLIGHTLY: Yup.number().min(0.0).max(1.0),
    VERY: Yup.number().min(0.0).max(1.0)
  });

  const formik = useFormik({
    initialValues: initialValues,
    validationSchema: Schema,
    enableReinitialize: true,
    onSubmit: (values, { setStatus, setSubmitting }) => {
      saveConfigs(values, setStatus, setSubmitting);
    },
  });

  const curHedgeSum = () => {
    return formik.values.SLIGHTLY + formik.values.VERY;
  };
  // END FORMIK FORM CONFIG-----------------------------------------

  const hedgeConfigList = [
    {
      label: "SLIGHTLY",
      field: "SLIGHTLY"
    },
    {
      label: "VERY",
      field: "VERY"
    }
  ]

  return (
    <div
      className="flex-row-auto offcanvas-mobile w-550px w-xxl-650px"
    >
      <form className="card card-custom h-100" onSubmit={formik.handleSubmit}>
        {loading && <ModalProgressBar />}

        {/* begin::Header */}
        <div className="card-header py-3">
          <div className="card-title align-items-start flex-column">
            <h3 className="card-label text-dark">
              1. Hedge Algebra settings
            </h3>
            <span className="text-muted font-weight-bold font-size-sm mt-1">
                Change PFS model's hedge algebra settings
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
            {/* begin::Heading */}
            <div className="row">
              <label className="col-xl-3"></label>
              <div className="col-lg-9 col-xl-6">
                <h5 className="font-weight-bold mb-6">Neutral Theta:</h5>
              </div>
            </div>
            {/* begin::Form Group */}
            <div className="form-group row">
              <label className="col-xl-3 col-lg-3 col-form-label">Theta</label>
              <KDCHedgeConfigSlider formik={formik} field={"neutral_theta"}/>
            </div>
            <div className="separator separator-dashed my-5"></div>
            {/* ----------------------------------------------------*/}
            {/* begin::Heading */}
            <div className="row">
              <label className="col-xl-3"></label>
              <div className="col-lg-9 col-xl-6">
                <h5 className="font-weight-bold mb-6">Hedge Configs:</h5>
              </div>
            </div>
            {/* begin::Form Group */}
            {
              hedgeConfigList.map((element, key) => {
                return (
                  <div className="form-group row" key={key}>
                    <label className="col-xl-3 col-lg-3 col-form-label">{element.label}</label>
                    <KDCHedgeConfigSlider formik={formik} field={element.field}/>
                  </div>
                )
              })
            }
            {
              curHedgeSum() !== 1.0 &&
                <span className="text-danger font-weight-bold font-size-sm mt-1">
                  {`Note: Sum of all hedge configs must equals 1. Current sum = ${Number((curHedgeSum()).toFixed(2))}`}
                </span>
            }
          </div>
        </div>
        {/* end::Form */}
      </form>
    </div>
  );
}

export default PFSHedgeConfigCard;