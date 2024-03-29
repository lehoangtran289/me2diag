import React, {useEffect, useState} from "react";
import {useHistory} from "react-router-dom";
import {getAllPatients} from "./_redux/patientCrud";
import {Card, CardBody, CardHeader, CardHeaderToolbar} from "../../../_metronic/_partials/controls";
import PatientsFilter from "./components/table/PatientsFilter";
import PatientsTable from "./components/table/PatientsTable";

function PatientListPage(props) {
  const history = useHistory();

  const [patients, setPatients] = useState([]);
  const [listLoading, setListLoading] = useState(false);

  const [paging, setPaging] = useState({
    totalPages: 0,
    currentPage: 1,
    totalItems: 0,
    rowsPerPage: 0,
  });

  // TODO: add sorting
  const [query, setQuery] = useState({
    query: "",
    page: 1,
    gen: null,
    size: 10,
    sort: ""
  });

  useEffect(() => {
    props.setRerenderFlag(false);
  }, [props.rerenderFlag]);

  useEffect(() => {
    console.log(query);
    setListLoading(true);
    getAllPatients(query)
      .then(r => {
        setPatients(r.data.data["items"]);
        setPaging({
          ...paging,
          totalPages: r.data.data["total_pages"],
          totalItems: r.data.data["total_items"],
          currentPage: r.data.data["current_page"],
          rowsPerPage: r.data.data["page_size"]
        });
        setListLoading(false);
      })
      .catch(error => {
        setListLoading(false);
        console.log("Error get all patients: " + error);
        alert("Cannot get patients");
      });
  }, [query, props.rerenderFlag]);

  const newCustomerButtonClick = () => {
    console.log("newCustomerButtonClick")
    history.push("/patients/new");
  }

  return (
    <Card>
      <CardHeader title={"Patient list"}>
        <CardHeaderToolbar>
          <button
            type="button"
            className="btn btn-primary"
            onClick={newCustomerButtonClick}
          >
            New Patient
          </button>
        </CardHeaderToolbar>
      </CardHeader>
      <CardBody>
        <PatientsFilter query={query} setQuery={setQuery}/>
        <PatientsTable
          patients={patients}
          query={query}
          paging={paging}
          setQuery={setQuery}
          listLoading={listLoading}
          setListLoading={setListLoading}
        />
      </CardBody>
    </Card>
  );
}

export default PatientListPage;