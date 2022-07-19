import React from 'react';
import Slider from "@mui/material/Slider";

/**
 * duplicate with KDCHedgeConfigSlider
 */
export default function PFSHedgeConfigSlider({ formik, field, ...props }) {
  return (
    <div className="col-xl-9 col-lg-9">
      <div className="row">
        <div className="col-9">
          <Slider
            value={typeof formik.values[field] === "number" ? formik.values[field] : 0.0}
            onChange={(event, newValue) => {
              formik.setFieldValue(field, newValue);
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
            name={field}
            className={`form-control form-control-lg form-control-solid`}
            onChange={(event) => {
              formik.setFieldValue(field, event.target.value ? Number(event.target.value) : 0.0);
            }}
            {...formik.getFieldProps(field)}
          />
          {formik.touched[field] && formik.errors[field] ? (
            <div className="invalid-feedback">
              {formik.errors[field]}
            </div>
          ) : null}
        </div>
      </div>
    </div>
  );
}