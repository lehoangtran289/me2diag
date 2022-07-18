import React, { useEffect, useState } from "react";
import {getAllKDCHedgeConfigs, getAllKDCLinguisticDomainConfigs, saveKDCHedgeConfigs} from "./_redux/KDCConfigCrud";
import { toastify } from "../../utils/toastUtils";
import { Card, CardBody, CardHeader, ModalProgressBar } from "../../../_metronic/_partials/controls";
import { useHistory } from "react-router-dom";
import BootstrapTable from "react-bootstrap-table-next";
import {
  headerSortingClasses,
  NoRecordsFoundMessage,
  PleaseWaitMessage,
  sortCaret
} from "../../../_metronic/_helpers";
import { useDispatch } from "react-redux";
import * as Yup from "yup";
import { useFormik } from "formik";
import Slider from "@mui/material/Slider";
import {useSubheader} from "../../../_metronic/layout";

function KdcConfigPage(props) {
  const subheader = useSubheader();
  subheader.setTitle("KDC problem settings");

  const dispatch = useDispatch();
  const history = useHistory();
  const [rerender, setRerender] = useState(false);
  const [loading, setloading] = useState(false);

  const [hedgeConfigs, setHedgeConfigs] = useState({
    neutral_theta: 0.0,
    LITTLE: 0.0,
    POSSIBLE: 0.0,
    MORE: 0.0,
    VERY: 0.0
  });
  const [linguisticDomain, setLinguisticDomain] = useState([]);

  useEffect(() => {
    // get linguistic domain configs
    getAllKDCLinguisticDomainConfigs()
      .then(res => {
        setLinguisticDomain(res.data.data);
      })
      .catch(err => {
        console.log(err);
        toastify.error("Error getting linguistic domain configs");
      });

    // get hedge configs
    setloading(true);
    getAllKDCHedgeConfigs()
      .then(res => {
        const configs = res.data.data
        let curHedgeConfig = {
          neutral_theta: 0.0,
          LITTLE: 0.0,
          POSSIBLE: 0.0,
          MORE: 0.0,
          VERY: 0.0
        }
        configs.forEach((config) => {
          switch(config['hedge_algebra_enum']) {
            case 'LITTLE':
              curHedgeConfig["LITTLE"] = config['fm_value']
              break;
            case 'POSSIBLE':
              curHedgeConfig["POSSIBLE"] = config['fm_value']
              break;
            case 'MEDIUM':
              curHedgeConfig["neutral_theta"] = config['fm_value']
              break;
            case 'MORE':
              curHedgeConfig["MORE"] = config['fm_value']
              break;
            case 'VERY':
              curHedgeConfig["VERY"] = config['fm_value']
              break;
          }
        });
        setHedgeConfigs(curHedgeConfig);
        setloading(false);
      })
      .catch(err => {
        setloading(false);
        console.log(err);
        toastify.error("Error getting linguistic domain configs");
      });
  }, [rerender]);

  // BEGIN TABLE COLUMN CONFIG-----------------------------------------
  const configsTableColumns = [
    {
      dataField: "linguistic_domain_element",
      text: "Linguistic domain element",
      sort: true,
      sortCaret: sortCaret,
      headerSortingClasses
    },
    {
      dataField: "fm_value",
      text: "f_m value",
      sort: true,
      sortCaret: sortCaret,
      headerSortingClasses
    },
    {
      dataField: "vvalue",
      text: "v value",
      sort: true,
      sortCaret: sortCaret,
      headerSortingClasses
    },
    {
      dataField: "linguistic_order",
      text: "Order",
      sort: true,
      sortCaret: sortCaret,
      headerSortingClasses
    }
  ];
  // BEGIN TABLE COLUMN CONFIG-----------------------------------------

  // BEGIN FORMIK FORM CONFIG-----------------------------------------
  const initialValues = hedgeConfigs;

  const saveConfigs = (values, setStatus, setSubmitting) => {
    if (curHedgeSum() !== 1.0) {
      toastify.error("Error setting KDC hedge configs configs, sum hedge configs != 1.0");
      return;
    }
    console.log(values);
    setloading(true);
    saveKDCHedgeConfigs(values)
      .then(res => {
        setloading(false);
        setRerender(!rerender);
        toastify.success("set KDC hedge configs succeed!");
      })
      .catch(err => {
        setloading(false);
        console.log(err);
        toastify.error("Error setting KDC hedge configs configs");
      });
  };

  const getInputClasses = (fieldname) => {
    if (formik.touched[fieldname] && formik.errors[fieldname]) {
      return "is-invalid";
    }
    if (formik.touched[fieldname] && !formik.errors[fieldname]) {
      return "is-valid";
    }
    return "";
  };


  const Schema = Yup.object().shape({
    neutral_theta: Yup.number().min(0.0).max(1.0),
    LITTLE: Yup.number().min(0.0).max(1.0),
    POSSIBLE: Yup.number().min(0.0).max(1.0),
    MORE: Yup.number().min(0.0).max(1.0),
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
    return formik.values.LITTLE + formik.values.POSSIBLE + formik.values.MORE + formik.values.VERY;
  };
  // END FORMIK FORM CONFIG-----------------------------------------

  return (
    <div className="d-flex flex-row">
      <div
        className="flex-row-auto offcanvas-mobile w-550px w-xxl-650px"
      >
        <form className="card card-custom" onSubmit={formik.handleSubmit}>
          {loading && <ModalProgressBar />}

          {/* begin::Header */}
          <div className="card-header py-3">
            <div className="card-title align-items-start flex-column">
              <h3 className="card-label text-dark">
                Hedge Algebra settings
              </h3>
              <span className="text-muted font-weight-bold font-size-sm mt-1">
                Change KDC model's hedge algebra settings
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
                <div className="col-xl-9 col-lg-9">
                  <div className="row">
                    <div className="col-9">
                      <Slider
                        value={typeof formik.values.neutral_theta === "number" ? formik.values.neutral_theta : 0.0}
                        onChange={(event, newValue) => {
                          formik.setFieldValue("neutral_theta", newValue);
                        }}
                        min={0.0}
                        max={1.0}
                        step={0.01}
                        aria-labelledby="input-slider"
                      />
                    </div>
                    <div className="col-3">
                      <input
                        type="number" min={0.0} max={1.0} step={0.01}
                        name="neutral_theta"
                        className={`form-control form-control-lg form-control-solid`}
                        onChange={(event) => {
                          formik.setFieldValue("neutral_theta", event.target.value ? Number(event.target.value) : 0.0);
                        }}
                        {...formik.getFieldProps("neutral_theta")}
                      />
                      {formik.touched.neutral_theta && formik.errors.neutral_theta ? (
                        <div className="invalid-feedback">
                          {formik.errors.neutral_theta}
                        </div>
                      ) : null}
                    </div>
                  </div>
                </div>
              </div>
              <div className="separator separator-dashed my-5"></div>
              {/* begin::Form Group ----------------------------------------------------*/}
              <div className="row">
                <label className="col-xl-3"></label>
                <div className="col-lg-9 col-xl-6">
                  <h5 className="font-weight-bold mb-6">Hedge Configs:</h5>
                </div>
              </div>
              <div className="form-group row">
                <label className="col-xl-3 col-lg-3 col-form-label">
                  LITTLE
                </label>
                <div className="col-xl-9 col-lg-9">
                  <div className="row">
                    <div className="col-9">
                      <Slider
                        value={typeof formik.values.LITTLE === "number" ? formik.values.LITTLE : 0.0}
                        onChange={(event, newValue) => {
                          formik.setFieldValue("LITTLE", newValue);
                        }}
                        min={0.0}
                        max={1.0}
                        step={0.01}
                        aria-labelledby="input-slider"
                      />
                    </div>
                    <div className="col-3">
                      <input
                        type="number" min={0.0} max={1.0} step={0.01}
                        name="LITTLE"
                        className={`form-control form-control-lg form-control-solid`}
                        onChange={(event) => {
                          formik.setFieldValue("LITTLE", event.target.value ? Number(event.target.value) : 0.0);
                        }}
                        {...formik.getFieldProps("LITTLE")}
                      />
                      {formik.touched.LITTLE && formik.errors.LITTLE ? (
                        <div className="invalid-feedback">
                          {formik.errors.LITTLE}
                        </div>
                      ) : null}
                    </div>
                  </div>
                </div>
              </div>
              <div className="form-group row">
                <label className="col-xl-3 col-lg-3 col-form-label">
                  POSSIBLE
                </label>
                <div className="col-xl-9 col-lg-9">
                  <div className="row">
                    <div className="col-9">
                      <Slider
                        value={typeof formik.values.POSSIBLE === "number" ? formik.values.POSSIBLE : 0.0}
                        onChange={(event, newValue) => {
                          formik.setFieldValue("POSSIBLE", newValue);
                        }}
                        min={0.0}
                        max={1.0}
                        step={0.01}
                        aria-labelledby="input-slider"
                      />
                    </div>
                    <div className="col-3">
                      <input
                        type="number" min={0.0} max={1.0} step={0.01}
                        name="POSSIBLE"
                        className={`form-control form-control-lg form-control-solid`}
                        onChange={(event) => {
                          formik.setFieldValue("POSSIBLE", event.target.value ? Number(event.target.value) : 0.0);
                        }}
                        {...formik.getFieldProps("POSSIBLE")}
                      />
                      {formik.touched.POSSIBLE && formik.errors.POSSIBLE ? (
                        <div className="invalid-feedback">
                          {formik.errors.POSSIBLE}
                        </div>
                      ) : null}
                    </div>
                  </div>
                </div>
              </div>
              <div className="form-group row">
                <label className="col-xl-3 col-lg-3 col-form-label">
                  MORE
                </label>
                <div className="col-xl-9 col-lg-9">
                  <div className="row">
                    <div className="col-9">
                      <Slider
                        value={typeof formik.values.MORE === "number" ? formik.values.MORE : 0.0}
                        onChange={(event, newValue) => {
                          formik.setFieldValue("MORE", newValue);
                        }}
                        min={0.0}
                        max={1.0}
                        step={0.01}
                        aria-labelledby="input-slider"
                      />
                    </div>
                    <div className="col-3">
                      <input
                        type="number" min={0.0} max={1.0} step={0.01}
                        name="MORE"
                        className={`form-control form-control-lg form-control-solid`}
                        onChange={(event) => {
                          formik.setFieldValue("MORE", event.target.value ? Number(event.target.value) : 0.0);
                        }}
                        {...formik.getFieldProps("MORE")}
                      />
                      {formik.touched.MORE && formik.errors.MORE ? (
                        <div className="invalid-feedback">
                          {formik.errors.MORE}
                        </div>
                      ) : null}
                    </div>
                  </div>
                </div>
              </div>
              <div className="form-group row">
                <label className="col-xl-3 col-lg-3 col-form-label">
                  VERY
                </label>
                <div className="col-xl-9 col-lg-9">
                  <div className="row">
                    <div className="col-9">
                      <Slider
                        value={typeof formik.values.VERY === "number" ? formik.values.VERY : 0.0}
                        onChange={(event, newValue) => {
                          formik.setFieldValue("VERY", newValue);
                        }}
                        min={0.0}
                        max={1.0}
                        step={0.01}
                        aria-labelledby="input-slider"
                      />
                    </div>
                    <div className="col-3">
                      <input
                        type="number" min={0.0} max={1.0} step={0.01}
                        name="VERY"
                        className={`form-control form-control-lg form-control-solid`}
                        onChange={(event) => {
                          formik.setFieldValue("VERY", event.target.value ? Number(event.target.value) : 0.0);
                        }}
                        {...formik.getFieldProps("VERY")}
                      />
                      {formik.touched.VERY && formik.errors.VERY ? (
                        <div className="invalid-feedback">
                          {formik.errors.VERY}
                        </div>
                      ) : null}
                    </div>
                  </div>
                </div>
              </div>
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
      <div className="flex-row-fluid ml-lg-8">
        <Card>
          <CardHeader title={"All KDC model settings"}>
          </CardHeader>
          <CardBody>
            <BootstrapTable
              wrapperClasses="table-responsive"
              bordered={false}
              classes="table table-head-custom table-vertical-center overflow-hidden"
              bootstrap4
              keyField="linguistic_domain_element"
              data={linguisticDomain ? linguisticDomain : []}
              columns={configsTableColumns}
            >
              <PleaseWaitMessage entities={linguisticDomain} />
              <NoRecordsFoundMessage entities={linguisticDomain} />
            </BootstrapTable>
          </CardBody>
        </Card>
      </div>
    </div>
  );
}

export default KdcConfigPage;