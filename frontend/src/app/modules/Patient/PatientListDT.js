import React from "react";
import { useHistory } from "react-router-dom";
import { useEffect, useState } from "react";
import { getAllPatients } from "./_redux/patientCrud";
import { toast } from "react-toastify";
import { Card, CardBody, CardHeader, CardHeaderToolbar } from "../../../_metronic/_partials/controls";
import PatientsFilter from "./components/PatientsFilter";
import PatientsTable from "./components/PatientsTable";

function PatientListDt(props) {
  const history = useHistory();

  const [update, setUpdate] = useState(false);

  const [patients, setPatients] = useState([]);
  const [totalPages, setTotalPages] = useState(0);
  const [totalItems, setTotalItems] = useState(0);
  const [currentPage, setCurrentPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);

  useEffect(() => {
    getAllPatients()
      .then(r => {
        console.log(r);
        setPatients(r.data.data["items"]);
        setTotalPages(r.data.data["total_pages"]);
        setTotalItems(r.data.data["total_items"]);
        setCurrentPage(r.data.data["current_page"]);
      })
      .catch(error => {
        console.log("Error get all patients: " + error);
        toast.error("Cannot get patients", {
          position: "top-right",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true
        });
      });
  }, [update]);

  return (
    <Card>
      <CardHeader title={"Patient list"}>
        <CardHeaderToolbar>
          <button
            type="button"
            className="btn btn-primary"
            onClick={() => {
            }}
          >
            New Customer
          </button>
        </CardHeaderToolbar>
      </CardHeader>
      <CardBody>
        <PatientsFilter />
        <PatientsTable
          patients={patients}
          totalPages={totalPages}
          totalItems={totalItems}
          currentPage={currentPage}
          rowsPerPage={rowsPerPage}
        />
      </CardBody>
    </Card>
  );
}

export default PatientListDt;