import React, {useEffect, useState} from "react";
import {BACKEND_ORIGIN} from "../../../config";
import axios from "axios";
import {toastify} from "../../../app/utils/toastUtils";
import {
  AdvanceTablesWidget1, MixedWidget14,
  MixedWidget6,
  TilesWidget1,
  TilesWidget11,
  TilesWidget13,
  TilesWidget3
} from "../widgets";
import {GeneralTilesWidget} from "../widgets/tiles/GeneralTilesWidget";
import AdvancedTableWidget from "../widgets/advance-tables/AdvancedTableWidget";
import {formatDate, getAge, timeSince} from "../../../app/utils/dateUtils";
import {NameColumnFormatter} from "../../../app/modules/Patient/column-formatters/NameColumnFormatter";
import DoctorNameColumnFormatter from "../../../app/modules/Examination/components/formatter/DoctorNameColumnFormatter";
import {useHistory} from "react-router-dom";
import ExamActionsColumnFormatter
  from "../../../app/modules/Examination/components/formatter/ExamActionsColumnFormatter";
import SVG from "react-inlinesvg";
import {headerSortingClasses, sortCaret, toAbsoluteUrl} from "../../_helpers";

export const getDashboardData = () => {
  const GET_DASHBOARD_DATA = BACKEND_ORIGIN + `dashboard`;
  return axios.get(GET_DASHBOARD_DATA);
};

export function Demo4Dashboard() {
  const [isLoading, setIsLoading] = useState(false);
  const [data, setData] = useState({});
  const history = useHistory();

  useEffect(() => {
    setIsLoading(true);
    getDashboardData()
      .then((r) => {
        setData(r.data.data);
        setIsLoading(false);
      })
      .catch(er => {
        setIsLoading(false);
        console.log(er);
        toastify.error("Cannot get dashboard data");
      });
  }, []);

  const recentExamsColumns = [
    {
      dataField: "appId",
      text: "Exam type",
      align: "center",
      headerAlign: "center",
      headerStyle: (colum, colIndex) => {
        return {width: '10em'};
      },
      formatter: (cellContent, row, rowIndex) => {
        return (
          <div className={`font-weight-bolder font-size-lg text-${cellContent === "PFS" ? "success" : "info"}`}>
            {`${cellContent}`}
          </div>
        );
      }
    },
    {
      dataField: "patientName",
      text: "Patient",
      formatter: (cellContent, row, rowIndex) => {
        return (
          <>
            <div className={"font-weight-bolder"}>{`${cellContent}`}</div>
            <div>{`ID: ${row["patientId"]}`}</div>
          </>
        );
      }
    },
    {
      dataField: "birthDate",
      text: "Age",
      formatter: (cellContent) => {
        return <span>{`${getAge(new Date(cellContent))}`}</span>
      }
    },
    {
      dataField: "userFullName",
      text: "Doctor in charge",
      formatter: DoctorNameColumnFormatter,
      headerStyle: (colum, colIndex) => {
        return {width: '17em'};
      },
    },
    {
      dataField: "result",
      text: "Result",
      align: "center",
      headerAlign: "center",
      formatter: (cellContent, row, rowIndex) => {
        return <div className={"font-weight-bold"}>{`${cellContent}`}</div>
      }
    },
    {
      dataField: "createdAt",
      text: "Date",
      formatter: (cellContent, row, rowIndex) => {
        return <span>{`${timeSince(new Date(cellContent))}`}</span>
      }
    },
    {
      dataField: "id",
      text: "Detail",
      formatter: (cellContent, row, rowIndex, {openViewExamDetails}) => {
        const handleClick = () => {
          openViewExamDetails(row.id);
        }
        return (
          <a
            title="View patient detail"
            className="btn btn-icon btn-light btn-hover-primary btn-sm"
            onClick={handleClick}
          >
        <span className="svg-icon svg-icon-md svg-icon-primary">
          <SVG
            src={toAbsoluteUrl("/media/svg/icons/General/Visible.svg")}
          />
        </span>
          </a>
        )
      },
      formatExtraData: {
        openViewExamDetails: (id) => {
          history.push(`/examinations/${id}`)
        }
      }
    },
  ]

  const recentPatientsColumns = [
    {
      dataField: "id",
      text: "Patient ID",
    },
    {
      dataField: "name",
      text: "Patient name",
      formatter: NameColumnFormatter,
      formatExtraData: {
        openViewPatientDetails: () => {
        }
      }
    },
    {
      dataField: "birthDate",
      text: "Age",
      formatter: (cellContent) => {
        const [month, day, year] = cellContent.split("/");
        return <span>{`${getAge(new Date(year, month, day))}`}</span>
      }
    },
    {
      dataField: "gender",
      text: "Gender"
    },
    {
      dataField: "createdAt",
      text: "Created",
      formatter: (cellContent, row, rowIndex) => {
        return <span>{`${timeSince(new Date(cellContent))}`}</span>
      }
    },
  ]

  const topUsersColumns = [
    {
      dataField: "user.id",
      text: "Doctor Info",
      formatter: (cellContent, row, rowIndex) => {
        return (
          <>
            <div className={"container-fluid px-0 mx-0"}>
              <div className={"row px-0 mx-0"}>
                <div className={"col-lg-3 pr-0 pl-0 ml-0"}>
                  <div className="symbol mr align-self-start">
                    <div
                      className="symbol-label"
                      style={{backgroundImage: `url(${row.user.avatarUrl})`}}
                    ></div>
                  </div>
                </div>
                <div className={"col-lg-9 pr-0 flex align-items-center"}>
                  <div>
                    <span className={"font-weight-bolder"}>Name:</span>
                    {` ${row.user.firstName ? row.user.firstName : ""} ${row.user.lastName ? row.user.lastName : ""}`}
                  </div>
                  <div>
                    <span className={"font-weight-bolder"}>Email:</span>
                    {` ${row.user.email ? row.user.email : ""}`}
                  </div>
                </div>
              </div>
            </div>
          </>
        );
      }
    },
    {
      dataField: "totalExams",
      text: "Total exams conducted",
      headerAlign: "center",
      headerStyle: (colum, colIndex) => {
        return {width: '12em'};
      },
      align: "center",
      formatter: (cellContent) => {
        return <span className={"font-weight-bolder text-info font-size-lg"}>{`${cellContent}`}</span>
      }
    },
  ]

  return (
    <>
      {/* begin::Row */}
      <div className="row">
        <div className="col-xl-4">
          <div className={"row"}>
            <div className={"col-xl-6"}>
              <GeneralTilesWidget title={"Total Examinations"}
                                  value={(+data["totalPFSExams"] + (+data["totalKDCExams"]))}
                                  className="gutter-b" baseColor="primary" widgetHeight="150px"
              />
            </div>
            <div className={"col-xl-6"}>
              <GeneralTilesWidget title={"Total patients"}
                                  value={data["totalMalePatients"] + data["totalFemalePatients"]}
                                  className="gutter-b" baseColor="success" widgetHeight="150px"
              />
            </div>
          </div>
          <div className={"row"}>
            <div className={"col-xl-6"}>
              <GeneralTilesWidget title={"Total doctors"} value={data["totalUsers"]}
                                  className="gutter-b" baseColor="danger" widgetHeight="150px"
              />
            </div>
            <div className={"col-xl-6"}>
              <GeneralTilesWidget title={"Total experts"} value={data["totalExperts"]}
                                  className="gutter-b" baseColor="info" widgetHeight="150px"
              />
            </div>
          </div>
          <TilesWidget3 className="gutter-b" widgetHeight="150px"/>
        </div>
        <div className="col-xl-4">
          <TilesWidget1 className="gutter-b card-stretch" chartColor="danger"/>
        </div>
        <div className="col-xl-4">
          <MixedWidget14 className="gutter-b card-stretch"/>
        </div>
      </div>
      {/* end::Row */}

      {/* begin::Row */}
      <div className="row">
        <div className="col-lg-8 col-xxl-8">
          <AdvancedTableWidget className="card-stretch gutter-b"
                               data={data["recentExams"]}
                               title={"Recent examinations"}
                               columns={recentExamsColumns}/>
        </div>
        <div className="col-lg-4 col-xxl-4">
          <AdvancedTableWidget className="card-stretch gutter-b"
                               data={data["topUsers"]}
                               title={"Top doctors"}
                               columns={topUsersColumns}/>
        </div>
      </div>
      {/* end::Row */}
    </>
  );
}
