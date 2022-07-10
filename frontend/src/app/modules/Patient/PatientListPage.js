import React from "react";
import { useHistory } from "react-router-dom";
import { useEffect, useState } from "react";
import { getAllPatients } from "./_redux/patientCrud";
import { toast } from "react-toastify";
import { Card, CardBody, CardHeader, CardHeaderToolbar } from "../../../_metronic/_partials/controls";
import PatientsFilter from "./components/PatientsFilter";
import PatientsTable from "./components/PatientsTable";

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
  }, [query]);

  const newCustomerButtonClick = () => {
  //  history.push("/patients/new");
    console.log("newCustomerButtonClick")
  }

  return (
    <Card>
      <CardHeader title={"Patient list"}>
        <CardHeaderToolbar>
          <button
            type="button"
            className="btn btn-primary"
            onClick={newCustomerButtonClick} // TODO: add create new patient modal
          >
            New Customer
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