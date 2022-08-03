import React, { useEffect, useState } from "react";
import { BACKEND_ORIGIN } from "../../../config";
import axios from "axios";
import { toastify } from "../../../app/utils/toastUtils";
import {
  AdvanceTablesWidget1,
  MixedWidget6,
  TilesWidget1,
  TilesWidget11,
  TilesWidget13,
  TilesWidget3
} from "../widgets";
import { GeneralTilesWidget } from "../widgets/tiles/GeneralTilesWidget";

export const getDashboardData = () => {
  const GET_DASHBOARD_DATA = BACKEND_ORIGIN + `dashboard`;
  return axios.get(GET_DASHBOARD_DATA);
};

export function Demo4Dashboard() {
  const [isLoading, setIsLoading] = useState(false);
  const [data, setData] = useState({});

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
          <TilesWidget3 className="gutter-b" widgetHeight="150px" />
        </div>
        <div className="col-xl-4">
          <TilesWidget1 className="gutter-b card-stretch" chartColor="danger" />
        </div>
        <div className="col-xl-4">
          <MixedWidget6 className="gutter-b card-stretch" chartColor="danger" />
        </div>
      </div>
      {/* end::Row */}

      {/* begin::Row */}
      <div className="row">
        <div className="col-lg-6 col-xxl-6">
          <AdvanceTablesWidget1 className="card-stretch gutter-b" />
        </div>
        <div className="col-lg-6 col-xxl-6">
          <AdvanceTablesWidget1 className="card-stretch gutter-b" />
        </div>
      </div>
      {/* end::Row */}
    </>
  );
}
