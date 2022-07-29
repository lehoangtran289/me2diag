import React, { useEffect, useState } from "react";
import { getAllExaminations } from "../../../Examination/_redux/examinationCrud";
import ExamsTable from "../../../Examination/components/table/ExamsTable";

function PatientExamsPage({ patientId, ...props }) {

  const [exams, setExams] = useState([]);
  const [listLoading, setListLoading] = useState(false);

  const [paging, setPaging] = useState({
    totalPages: 0,
    currentPage: 1,
    totalItems: 0,
    rowsPerPage: 0,
  });

  const [query, setQuery] = useState({
    page: 1,
    query: patientId,
    appId: "",
    size: 10,
    sort: ""
  });

  useEffect(() => {
    setListLoading(true);
    getAllExaminations(query)
      .then(r => {
        setExams(r.data.data["items"]);
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
        console.log("Error get all examinations: " + error);
        alert("Cannot get examinations");
      });
  }, [query, props.rerenderFlag]);

  useEffect(() => {
    props.setRerenderFlag(false);
  }, [props.rerenderFlag]);

  return (
    <div className="card card-custom">
      {/* begin::Header */}
      <div className="card-header py-3">
        <div className="card-title align-items-start flex-column">
          <h3 className="card-label font-weight-bolder text-dark">
            Patient examinations history
          </h3>
          <span className="text-muted font-weight-bold font-size-sm mt-1">
            Records of all patient's examination history
          </span>
        </div>
        <div className={"card-body mx-0 px-0"}>
          <ExamsTable
            exams={exams}
            query={query}
            paging={paging}
            setQuery={setQuery}
            listLoading={listLoading}
            setListLoading={setListLoading}
            isPatientPage={true}
          />
        </div>
      </div>
    </div>
  );
}

export default PatientExamsPage;