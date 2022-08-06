import React, { useEffect, useState } from "react";
import { toAbsoluteUrl } from "../../../../../_metronic/_helpers";
import { NavLink, useParams } from "react-router-dom";
import SVG from "react-inlinesvg";
import { getPatientDetail } from "../../_redux/patientCrud";
import { toastify } from "../../../../utils/toastUtils";
import { getAge } from "../../../../utils/dateUtils";
import { Dropdown } from "react-bootstrap";
import {
  DropdownCustomToggler,
  DropdownMenu1, DropdownMenu2, DropdownMenu3,
  DropdownMenu4
} from "../../../../../_metronic/_partials/dropdowns";

function PatientCard({ ...props }) {
  const [patient, setPatient] = useState({});
  const [month, day, year] = patient.birthDate ? patient.birthDate.split('/') : [1, 1, 1970];

  const { patientId } = useParams();

  useEffect(() => {
    console.log(props);
    if (props.location.state && props.location.state.patient) {
      setPatient(props.location.state.patient);
    }
    else {
      getPatientDetail(patientId)
        .then(r => {
          console.log(r);
          setPatient(r.data.data)
        }).catch(err => {
          console.log(err);
          toastify.error(`Get detail of patient failed!`)
      })
    }
  }, []);

  return (
    <>
      <div
        className="col-lg-4 col-sm-12 mb-md-0 mb-sm-5"
      >
        <div className="card card-custom card-stretch">
          {/* begin::Body */}
          <div className="card-body pt-4">
            {/* begin::Toolbar */}
            <div className="d-flex justify-content-end">
              <Dropdown className="dropdown dropdown-inline" alignRight>
                <Dropdown.Toggle
                  className="btn btn-clean btn-hover-light-primary btn-sm btn-icon cursor-pointer"
                  variant="transparent"
                  id="dropdown-toggle-top-user-profile"
                  as={DropdownCustomToggler}
                >
                  <i className="ki ki-bold-more-hor"></i>
                </Dropdown.Toggle>
                <Dropdown.Menu className="dropdown-menu dropdown-menu-sm dropdown-menu-right">
                  <DropdownMenu4></DropdownMenu4>
                </Dropdown.Menu>
              </Dropdown>
            </div>
            {/* end::Toolbar */}

            {/* begin::Patient */}
            <div className="d-flex align-items-center">
              <div className="symbol symbol-60 symbol-xxl-100 mr-5 align-self-start align-self-xxl-center">
                {
                  patient.avatarUrl ?
                    <div
                      className="symbol-label"
                      style={{ backgroundImage: `url(${patient.avatarUrl})` }}
                    /> :
                    <div
                      className="symbol-label"
                      style={{
                        backgroundImage: `url(${toAbsoluteUrl(
                          "/media/users/blank.png"
                        )}`,
                      }}
                    />
                }
                <i className="symbol-badge bg-success"></i>
              </div>
              <div>
                <span
                  className="font-weight-bolder font-size-h5 text-dark-75 text-hover-primary ml-2"
                >
                  {patient.name}
                </span>
                <div className="font-size-lg mt-2 ml-2">{`Age: ${getAge(new Date(year, month, day))}`}</div>
              </div>
            </div>
            {/* end::Patient */}

            {/* begin::Info */}
            <div className="py-7 my-3">
              <div
                className="font-weight-bolder font-size-h5 text-dark-70 mb-4"
              >
                  Patient info
              </div>
              <div className="d-flex align-items-center justify-content-between mb-2">
                <span className="font-weight-bold mr-2">Patient ID:</span>
                <span className="text-muted text-hover-primary">{patient.id}</span>
              </div>
              <div className="d-flex align-items-center justify-content-between mb-2">
                <span className="font-weight-bold mr-2">Date of birth</span>
                <span className="text-muted text-hover-primary">{patient.birthDate}</span>
              </div>
              <div className="d-flex align-items-center justify-content-between mb-2">
                <span className="font-weight-bold mr-2">Phone number:</span>
                <span className="text-muted text-hover-primary">{patient.phoneNo}</span>
              </div>
              <div className="d-flex align-items-center justify-content-between mb-2">
                <span className="font-weight-bold mr-2">Current address:</span>
                <span className="text-muted">{patient.address}</span>
              </div>
              <div className="d-flex align-items-center justify-content-between mb-2">
                <span className="font-weight-bold mr-2">Email:</span>
                <span className="text-muted text-hover-primary">{patient.email}</span>
              </div>
            </div>
            {/* end::Info */}

            {/* begin::Nav*/}
            <div className="navi navi-bold navi-hover navi-active navi-link-rounded">
              <div className="navi-item mb-2">
                <NavLink
                  to={`/patients/${patient.id}/diagnose/pfs`}
                  className="navi-link py-4"
                  activeClassName="active"
                >
                <span className="navi-icon mr-2">
                  <span className="svg-icon">
                    <SVG
                      src={toAbsoluteUrl(
                        "/media/svg/icons/General/Heart.svg"
                      )}
                    ></SVG>{" "}
                  </span>
                </span>
                  <span className="navi-text font-size-lg">
                    Common diseases diagnosis
                  </span>
                </NavLink>
              </div>
              <div className="navi-item mb-2">
                <NavLink
                  to={`/patients/${patient.id}/diagnose/kdc`}
                  className="navi-link py-4"
                  activeClassName="active"
                >
                <span className="navi-icon mr-2">
                  <span className="svg-icon">
                    <SVG
                      src={toAbsoluteUrl(
                        "/media/svg/icons/General/Like.svg"
                      )}
                    ></SVG>{" "}
                  </span>
                </span>
                  <span className="navi-text font-size-lg">
                    Kidney diseases diagnosis
                  </span>
                </NavLink>
              </div>
              <div className="navi-item mb-2">
                <NavLink
                  to={`/patients/${patient.id}/exams`}
                  className="navi-link py-4"
                  activeClassName="active"
                >
                <span className="navi-icon mr-2">
                  <span className="svg-icon">
                    <SVG
                      src={toAbsoluteUrl(
                        "/media/svg/icons/Files/File.svg"
                      )}
                    ></SVG>{" "}
                  </span>
                </span>
                  <span className="navi-text font-size-lg">
                    Examinations history
                  </span>
                </NavLink>
              </div>
            </div>
            {/* end::Nav*/}
          </div>
          {/* end::Body */}
        </div>
      </div>
    </>
  );
}

export default PatientCard;