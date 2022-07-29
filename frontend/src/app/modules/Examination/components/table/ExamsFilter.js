import React from "react";
import { Formik } from "formik";
import { debounce } from "../../../../utils/debounce";

function ExamsFilter({ query, setQuery }) {
  // on filter submit
  const applyFilter = (values) => {
    // queryParams, setQueryParams,
    setQuery({
      ...query,
      appId: values.appId ? values.appId : null,
      query: values.searchText
    })
  };

  return (
    <>
      <Formik
        initialValues={{
          appId: "",
          searchText: "",
        }}
        onSubmit={debounce((values) => {
          applyFilter(values);
        }, 250)}
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
                  name="appId"
                  placeholder="Filter by Application ID"
                  onChange={(e) => {
                    setFieldValue("appId", e.target.value);
                    handleSubmit();
                  }}
                  onBlur={handleBlur}
                  value={values.status}
                >
                  <option value="">All</option>
                  <option value="KDC">KDC</option>
                  <option value="PFS">PFS</option>
                </select>
                <small className="form-text text-muted">
                  <b>Filter</b> by Application ID
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
                  <b>Search</b> in patient's ID and name
                </small>
              </div>
            </div>
          </form>
        )}
      </Formik>
    </>
  );
}

export default ExamsFilter;