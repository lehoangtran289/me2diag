import React from 'react';
import {Card, CardBody, CardHeader} from "../../../../../_metronic/_partials/controls";
import BootstrapTable from "react-bootstrap-table-next";
import {
  headerSortingClasses,
  NoRecordsFoundMessage,
  PleaseWaitMessage,
  sortCaret
} from "../../../../../_metronic/_helpers";
import {useEffect, useState} from "react";
import {getAllPFSLinguisticDomainConfigs} from "../../_redux/PFSConfigCrud";
import {toastify} from "../../../../utils/toastUtils";

function PFSLinguisticDomainTable({ rerender, loading, setLoading, ...props }) {
  const [linguisticDomain, setLinguisticDomain] = useState([]);

  useEffect(() => {
    // get linguistic domain configs
    setLoading(true);
    getAllPFSLinguisticDomainConfigs()
      .then(res => {
        setLinguisticDomain(res.data.data);
        setLoading(false);
      })
      .catch(err => {
        setLoading(false);
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

  return (
    <div className="col-lg-6 col-sm-12 mt-lg-0 mt-sm-5">
      <Card className={"mb-0"}>
        <CardHeader title={"1. All PFS linguistic domain settings"}>
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
            defaultSorted={[{dataField: "linguistic_order", order: "asc"}]}
          >
            <PleaseWaitMessage entities={linguisticDomain} />
            <NoRecordsFoundMessage entities={linguisticDomain} />
          </BootstrapTable>
        </CardBody>
      </Card>
    </div>
  );
}

export default PFSLinguisticDomainTable;