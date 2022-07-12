import React from "react";
import { Formik } from "formik";
import { debounce } from "../../../utils/debounce";

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

function AccountsFilter({ query, setQuery }) {
  // on filter submit
  const applyFilter = (values) => {
    // queryParams, setQueryParams,
    setQuery({
      ...query,
      isEnable: values.isEnable ? values.isEnable : null,
      roles: values.roles ? values.roles : null,
      query: values.searchText
    })
  };

  return (
    <>
      <Formik
        initialValues={{
          isEnable: "",
          roles: '',
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
                  name="roles"
                  placeholder="Filter by Role"
                  onChange={(e) => {
                    setFieldValue("roles", e.target.value);
                    handleSubmit();
                  }}
                  onBlur={handleBlur}
                  value={values.status}
                >
                  <option value="">All</option>
                  <option value="USER">USER</option>
                  <option value="EXPERT">EXPERT</option>
                  <option value="ADMIN">ADMIN</option>
                </select>
                <small className="form-text text-muted">
                  <b>Filter</b> by Role
                </small>
              </div>
              <div className="col-lg-2">
                <select
                  className="form-control"
                  name="isEnable"
                  placeholder="Filter by Status"
                  onChange={(e) => {
                    setFieldValue("isEnable", e.target.value);
                    handleSubmit();
                  }}
                  onBlur={handleBlur}
                  value={values.status}
                >
                  <option value="">All</option>
                  <option value="True">Active</option>
                  <option value="False">Inactive</option>
                </select>
                <small className="form-text text-muted">
                  <b>Filter</b> by Status
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

export default AccountsFilter;