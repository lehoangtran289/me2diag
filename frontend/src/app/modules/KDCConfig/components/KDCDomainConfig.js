import React, { useEffect, useState } from "react";
import { toastify } from "../../../utils/toastUtils";
import { getAllKDCDomainConfigs, saveKDCDomainConfigs } from "../_redux/KDCConfigCrud";
import KDCDomainConfigFormatter from "../formatter/KDCDomainConfigFormatter";
import { ModalProgressBar } from "../../../../_metronic/_partials/controls";
import BootstrapTable from "react-bootstrap-table-next";
import { NoRecordsFoundMessage, PleaseWaitMessage } from "../../../../_metronic/_helpers";

function KdcDomainConfig(props) {
  const [rerender, setRerender] = useState(false);
  const [loading, setLoading] = useState(false);
  const [domainConfigs, setDomainConfigs] = useState([]);

  useEffect(() => {
    setLoading(true);
    getAllKDCDomainConfigs()
      .then(res => {
        setDomainConfigs(res.data.data);
        setLoading(false);
      })
      .catch(err => {
        setLoading(false);
        console.log(err);
        toastify.error("Error getting domain configs");
      });
  }, [rerender]);

  const saveKDCDomain = () => {
    saveKDCDomainConfigs(domainConfigs)
      .then(r => {
        toastify.success("Save KDC domain configs succeed!");
        setRerender(!rerender);
      })
      .catch(err => {
        console.log(err);
        toastify.error("Save KDC domain configs failed!");
      });
  }

  useEffect(() => {
    console.log(domainConfigs);
  }, [domainConfigs]);

  // BEGIN TABLE COLUMN CONFIG-----------------------------------------
  const setData = (idx, field, data) => {
    let newDomainData = [...domainConfigs];
    newDomainData[idx][field] = data;
    setDomainConfigs(newDomainData);
  };

  const configsTableColumns = [
    {
      dataField: "name",
      text: "Measurement",
      align: "center",
      headerAlign: "center",
      headerStyle: (colum, colIndex) => {
        return { width: '20em' };
      },
    },
    {
      dataField: "description",
      text: "Name",
      align: "center",
      headerAlign: "center",
      headerStyle: (colum, colIndex) => {
        return { width: '40em' };
      },
    },
    {
      dataField: "max",
      text: "Max value",
      formatter: KDCDomainConfigFormatter,
      formatExtraData: {
        setData: setData,
        field: "max"
      },
      headerAlign: "center"
    },
    {
      dataField: "min",
      text: "Min value",
      formatter: KDCDomainConfigFormatter,
      formatExtraData: {
        setData: setData,
        field: "min"
      },
      headerAlign: "center"
    }
  ];
  // BEGIN TABLE COLUMN CONFIG-----------------------------------------

  return (
    <>
      <div className="flex-row-fluid mt-2">
        <form className="card card-custom">
          {loading && <ModalProgressBar />}

          {/* begin::Header */}
          <div className="card-header py-3">
            <div className="card-title align-items-start flex-column">
              <h3 className="card-label text-dark">
                2. KDC Domain settings
              </h3>
              <span className="text-muted font-weight-bold font-size-sm mt-1">
                Change kidney diseases measurement's maximum and minimum values
              </span>
            </div>
            <div className="card-toolbar">
              <div
                className="btn btn-success mr-2"
                onClick={saveKDCDomain}
              >
                Save Changes
              </div>
              <div
                className="btn btn-secondary"
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
                keyField={"name"}
                data={domainConfigs ? domainConfigs : []}
                columns={configsTableColumns}
              >
                <PleaseWaitMessage entities={domainConfigs} />
                <NoRecordsFoundMessage entities={domainConfigs} />
              </BootstrapTable>
              {/*  end::Table */}
            </div>
          </div>
          {/* end::Form */}
        </form>
      </div>
    </>
  );
}

export default KdcDomainConfig;