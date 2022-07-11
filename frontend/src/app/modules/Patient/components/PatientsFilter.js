import React, { useCallback, useEffect } from "react";
import {Formik} from "formik";
import _debounce from "lodash/debounce";
import {debounce} from "../../../utils/debounce";

const prepareFilter = (queryParams, values) => {
  const { status, type, searchText } = values;
  const newQueryParams = { ...queryParams };
  const filter = {};
  // Filter by status
  filter.status = status !== "" ? +status : undefined;
  // Filter by type
  filter.type = type !== "" ? +type : undefined;
  // Filter by all fields
  filter.lastName = searchText;
  if (searchText) {
    filter.firstName = searchText;
    filter.email = searchText;
    filter.ipAddress = searchText;
  }
  newQueryParams.filter = filter;
  return newQueryParams;
};

// TODO: add debounce
function PatientsFilter({ query, setQuery }) {

  // on filter submit
  const applyFilter = (values) => {
    // queryParams, setQueryParams,
    setQuery({
      ...query,
      gen: values.gender ? values.gender : null,
      query: values.searchText
    })
  };

  return (
    <>
      <Formik
        initialValues={{
          gender: "",
          searchText: "",
        }}
        onSubmit={debounce((values) => {
          applyFilter(values);
        }, 500)}
      >
        {({
            values,
            handleSubmit,
            handleBlur,
            handleChange,
            setFieldValue,
          }) => (
          <form onSubmit={handleSubmit} className="form form-label-right">
            <div className="form-group row">
              <div className="col-lg-2">
                <select
                  className="form-control"
                  name="gender"
                  placeholder="Filter by Gender"
                  onChange={(e) => {
                    setFieldValue("gender", e.target.value);
                    handleSubmit();
                  }}
                  onBlur={handleBlur}
                  value={values.status}
                >
                  <option value="">All</option>
                  <option value="MALE">MALE</option>
                  <option value="FEMALE">FEMALE</option>
                </select>
                <small className="form-text text-muted">
                  <b>Filter</b> by Gender
                </small>
              </div>
              <div className="col-lg-2">
                <input
                  type="text"
                  className="form-control"
                  name="searchText"
                  placeholder="Search"
                  onBlur={handleBlur}
                  value={values.searchText}
                  onChange={(e) => {
                    setFieldValue("searchText", e.target.value);
                    handleSubmit();
                  }}
                />
                <small className="form-text text-muted">
                  <b>Search</b> in all fields
                </small>
              </div>
            </div>
          </form>
        )}
      </Formik>
    </>
  );
}

export default PatientsFilter;
