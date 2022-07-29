import React, { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import { Card, CardBody, CardHeader } from "../../../_metronic/_partials/controls";
import { getAllExaminations } from "./_redux/examinationCrud";
import ExamsFilter from "./components/table/ExamsFilter";
import ExamsTable from "./components/table/ExamsTable";

function ExaminationListPage({ ...props }) {
  const history = useHistory();

  const [exams, setExams] = useState([]);
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
    appId: "",
    size: 10,
    sort: ""
  });

  useEffect(() => {
    console.log(query);
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
    <Card>
      <CardHeader title={"All examinations"}/>
      <CardBody>
        <ExamsFilter query={query} setQuery={setQuery}/>
        <ExamsTable
          exams={exams}
          query={query}
          paging={paging}
          setQuery={setQuery}
          listLoading={listLoading}
          setListLoading={setListLoading}
          isPatientPage={false}
        />
      </CardBody>
    </Card>
  );
}

export default ExaminationListPage;
