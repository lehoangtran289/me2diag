import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getExamination } from "./_redux/examinationCrud";
import { toastify } from "../../utils/toastUtils";
import { Dropdown } from "react-bootstrap";
import { DropdownCustomToggler, DropdownMenu4 } from "../../../_metronic/_partials/dropdowns";
import { NoRecordsFoundMessage, PleaseWaitMessage, toAbsoluteUrl } from "../../../_metronic/_helpers";
import { getAge } from "../../utils/dateUtils";
import BootstrapTable from "react-bootstrap-table-next";
import PFSResultFormatter from "../Patient/column-formatters/PFSResultFormatter";
import { capitalizeFirstLetter } from "../../utils/common";
import KDCResultFormatter from "../Patient/column-formatters/KDCResultFormatter";
import { icdData } from "../Patient/components/diagnosis-page/KDCDiagnosis";

function ExaminationDetailPage({ ...props }) {
  const { examinationId } = useParams();
  const [appId, setAppId] = useState();

  const [patient, setPatient] = useState({});
  const [month, day, year] = patient.birthDate ? patient.birthDate.split("/") : [1, 1, 1970];

  const [examDate, setExamDate] = useState();
  const [pfsExam, setPfsExam] = useState({});
  const [kdcExam, setKdcExam] = useState({
    date: "",
    measures: [],
    result: { id: "" }
  });
  const [user, setUser] = useState({});

  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    console.log(examinationId);
    console.log(props);
    setIsLoading(true);
    getExamination(examinationId)
      .then(res => {
        const data = res.data.data;
        if (data["applicationId"] === "PFS") {
          console.log(convertPfsSymptoms(data));
          setPfsExam({
            result: convertPfsResult(data),
            symptoms: convertPfsSymptoms(data)
          });
        } else {
          setKdcExam({
            measures: convertKdcMeasures(data["result"]),
            result: convertKdcResult(data["result"])
          })
        }
        setExamDate(data["date"]);
        setAppId(data["applicationId"]);
        setPatient({
          id: data["patientId"],
          name: data["patientName"],
          dob: data["patientBirthDate"],
          phone: data["patientPhoneNo"],
          address: data["patientAddress"],
          email: data["patientEmail"]
        });
        setUser({
          name: data["userFullName"],
          email: data["userEmail"]
        });
        setIsLoading(false);
      })
      .catch(err => {
        setIsLoading(false);
        console.log(err);
        toastify.error("Cannot get examination detail!");
      });
  }, []);

  const convertPfsSymptoms = (data) => {
    let res = []
    data["symptoms"].forEach((e) => {
      for (let k in e) {
        res.push({
          symptom: capitalizeFirstLetter(k.toLowerCase().replace("_", " ")),
          ...e[k]
        })
      }
    })
    return res;
  }

  const convertPfsResult = (data) => {
    let res = {
      id: data["examinationId"]
    };
    data["result"].forEach((e, key) => {
      for (let k in e) {
        const field = capitalizeFirstLetter(k.toLowerCase().replace("_", " "));
        res[field] = Number(e[k] * 100).toFixed(2);
      }
    });
    return res;
  };

  const convertKdcMeasures = (data) => {
    let {result, ...measures} = data;
    let res = []
    for (let k in measures) {
      res.push({
        field: k.toUpperCase(),
        value: measures[k]
      })
    }
    return res;
  }

  const convertKdcResult = (data) => {
    return {
      id: examinationId,
      ...icdData.find(e => e.code === data["result"])
    };
  }

  const pfsSymptomTableColumns = [
    {
      dataField: "symptom",
      text: "Symptom",
    },
    {
      dataField: "positive",
      text: "Positive",
      headerAlign: "center",
      align: "center"
    },
    {
      dataField: "neutral",
      text: "Neutral",
      headerAlign: "center",
      align: "center"
    },
    {
      dataField: "negative",
      text: "Negative",
      headerAlign: "center",
      align: "center"
    }
  ];

  const pfsResultTableColumns = [
    {
      dataField: "id",
      text: "Examination ID",
      headerStyle: (colum, colIndex) => {
        return { width: '22em' };
      },
    },
    {
      dataField: "Fever",
      text: "Fever",
      headerAlign: "center",
      align: "center",
      headerStyle: (colum, colIndex) => {
        return { width: '13.5em' };
      },
      formatter: PFSResultFormatter,
    },
    {
      dataField: "Malaria",
      text: "Malaria",
      headerAlign: "center",
      align: "center",
      headerStyle: (colum, colIndex) => {
        return { width: '13.5em' };
      },
      formatter: PFSResultFormatter,
    },
    {
      dataField: "Typhoid",
      text: "Typhoid",
      headerAlign: "center",
      align: "center",
      headerStyle: (colum, colIndex) => {
        return { width: '13.5em' };
      },
      formatter: PFSResultFormatter,
    },
    {
      dataField: "Stomach",
      text: "Stomach",
      headerAlign: "center",
      align: "center",
      headerStyle: (colum, colIndex) => {
        return { width: '13.5em' };
      },
      formatter: PFSResultFormatter,
    },
    {
      dataField: "Chest problem",
      text: "Chest problem",
      headerAlign: "center",
      align: "center",
      formatter: PFSResultFormatter,
      headerStyle: (colum, colIndex) => {
        return { width: '16.5em' };
      },
    },
  ]

  const kdcSymptomsTableColumns = [
    {
      dataField: "field",
      text: "Measurement",
      headerAlign: "center",
      align: "center",
      headerStyle: (colum, colIndex) => {
        return { width: '13em' };
      },
      formatter: (cellContent) => {
        return <span className={"font-weight-bolder"}>{`${cellContent}`}</span>
      }
    },
    {
      dataField: "value",
      text: "Value",
      headerAlign: "center",
      align: "center",
      headerStyle: (colum, colIndex) => {
        return { width: '13em' };
      },
    },
  ]

  const kdcResultTableColumns = [
    {
      dataField: "id",
      text: "Examination ID",
      headerStyle: (colum, colIndex) => {
        return { width: '22em' };
      },
    },
    {
      dataField: "code",
      text: "ICD Code",
      headerStyle: (colum, colIndex) => {
        return { width: '10em' };
      },
      headerAlign: "center",
      align: "center",
      formatter: KDCResultFormatter
    },
    {
      dataField: "name",
      text: "Disease name",
      headerAlign: "center",
      align: "center",
      headerStyle: (colum, colIndex) => {
        return { width: '13em' };
      }
    },
    {
      dataField: "description",
      text: "Description",
      headerStyle: (colum, colIndex) => {
        return { width: '22em' };
      }
    }
  ]

  return (
    !isLoading &&
    <div className="container-fluid px-0">
      <div className={"row px-0"}>
        <div className="col-lg-4 col-sm-12 mb-md-0 mb-sm-5">
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
                          )}`
                        }}
                      />
                  }
                  <i className="symbol-badge bg-success"></i>
                </div>
                <div>
                  <span
                    className="font-size-h5 text-dark-75 text-hover-primary ml-2"
                  >
                    Patient:
                  </span>
                  <span className={"font-size-h5 text-dark-75 font-weight-bolder ml-2"}>
                    {patient.name}
                  </span>
                  <div className="font-size-lg mt-2 ml-2">{`Age: ${getAge(new Date(year, month, day))}`}</div>
                </div>
              </div>
              {/* end::Patient */}

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
                  <span className="text-muted text-hover-primary">{patient.dob}</span>
                </div>
                <div className="d-flex align-items-center justify-content-between mb-2">
                  <span className="font-weight-bold mr-2">Phone number:</span>
                  <span className="text-muted text-hover-primary">{patient.phone}</span>
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
              <hr />
              <div className="py-7 my-3">
                <div
                  className="font-weight-bolder font-size-h5 text-dark-70 mb-4"
                >
                  Examination Info
                </div>
                <div className="d-flex align-items-center justify-content-between mb-2">
                  <span className="font-weight-bold mr-2">Doctor in charge:</span>
                  <span className="text-muted text-hover-primary">{user.name}</span>
                </div>
                <div className="d-flex align-items-center justify-content-between mb-2">
                  <span className="font-weight-bold mr-2">Doctor's email</span>
                  <span className="text-muted text-hover-primary">{user.email}</span>
                </div>
                <div className="d-flex align-items-center justify-content-between mb-2">
                  <span className="font-weight-bold mr-2">Examination date</span>
                  <span className="text-muted text-hover-primary">{examDate}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="col-lg-8 col-sm-12">
          {
            appId === "PFS" ?
              <>
                <form className="card card-custom">
                  {/* begin::Header */}
                  <div className="card-header py-3">
                    <div className="card-title align-items-start flex-column">
                      <h3 className="card-label font-weight-bolder text-dark">
                        Common diseases diagnosis result
                      </h3>
                      <span className="text-muted font-weight-bold font-size-sm mt-1">
                        Diagnose result of patient based on regular symptoms
                      </span>
                    </div>
                  </div>
                  {/* end::Header */}
                  {/* begin::Form */}
                  <div className="form">
                    <div className="card-body">
                      <BootstrapTable
                        wrapperClasses="table-responsive"
                        bordered={false}
                        classes="table table-head-custom table-vertical-center overflow-hidden"
                        bootstrap4
                        keyField={"symptom"}
                        data={pfsExam ? pfsExam.symptoms : []}
                        columns={pfsSymptomTableColumns}
                      >
                        <PleaseWaitMessage entities={pfsExam.symptoms} />
                        <NoRecordsFoundMessage entities={pfsExam.symptoms} />
                      </BootstrapTable>
                    </div>
                  </div>
                  {/* end::Form */}
                </form>
                <div className="card card-custom mt-5">
                  {/* begin::Header */}
                  <div className="card-header py-3">
                    <div className="card-title align-items-start flex-column">
                      <h3 className="card-label font-weight-bolder text-dark">
                        Diagnosis result
                      </h3>
                      <span className="text-muted font-weight-bold font-size-sm mt-1">
                      Affection probability on 5 common diseases
                    </span>
                    </div>
                    <div className="card-toolbar mt-3">
                      <div
                        className="btn btn-success mr-2"
                        onClick={() => {
                        }}
                      >
                        Print result
                      </div>
                    </div>
                  </div>
                  {/* end::Header */}
                  {/* begin::Body*/}
                  <div className="card-body">
                    <BootstrapTable
                      wrapperClasses="table-responsive"
                      classes="table table-head-custom table-vertical-center overflow-hidden"
                      bootstrap4
                      keyField={"id"}
                      data={pfsExam ? [pfsExam.result] : []}
                      columns={pfsResultTableColumns}
                    >
                      <PleaseWaitMessage entities={[pfsExam.result]} />
                      <NoRecordsFoundMessage entities={[pfsExam.result]} />
                    </BootstrapTable>
                  </div>
                  {/* end::Body*/}
                </div>
              </>
              :
              <>
                <form className="card card-custom">
                  {/* begin::Header */}
                  <div className="card-header py-3">
                    <div className="card-title align-items-start flex-column">
                      <h3 className="card-label font-weight-bolder text-dark">
                        Kidney diseases diagnosis result
                      </h3>
                      <span className="text-muted font-weight-bold font-size-sm mt-1">
                        Patient diagnosis result of kidney-related disease based on given measurements
                      </span>
                    </div>
                  </div>
                  {/* end::Header */}
                  {/* begin::Form */}
                  <div className="form">
                    <div className="card-body">
                      <BootstrapTable
                        wrapperClasses="table-responsive"
                        classes="table table-head-custom table-vertical-center overflow-hidden"
                        bootstrap4
                        bordered={true}
                        keyField={"field"}
                        data={kdcExam ? kdcExam.measures : []}
                        columns={kdcSymptomsTableColumns}
                      >
                        <PleaseWaitMessage entities={kdcExam.measures} />
                        <NoRecordsFoundMessage entities={kdcExam.measures} />
                      </BootstrapTable>
                    </div>
                  </div>
                  {/* end::Form */}
                </form>
                <div className="card card-custom mt-5">
                  {/* begin::Header */}
                  <div className="card-header py-3">
                    <div className="card-title align-items-start flex-column">
                      <h3 className="card-label font-weight-bolder text-dark">
                        Diagnosis result
                      </h3>
                      <span className="text-muted font-weight-bold font-size-sm mt-1">
                        Kidney diseases diagnosis possible result
                      </span>
                    </div>
                    <div className="card-toolbar mt-3">
                      <div
                        className="btn btn-success mr-2"
                        onClick={() => {}}
                      >
                        Print result
                      </div>
                    </div>
                  </div>
                  {/* end::Header */}
                  {/* begin::Body*/}
                  <div className="card-body">
                    <BootstrapTable
                      wrapperClasses="table-responsive"
                      classes="table table-head-custom table-vertical-center overflow-hidden"
                      bootstrap4
                      keyField={"id"}
                      data={kdcExam ? [kdcExam.result] : []}
                      columns={kdcResultTableColumns}
                    >
                      <PleaseWaitMessage entities={[kdcExam.result]} />
                      <NoRecordsFoundMessage entities={[kdcExam.result]} />
                    </BootstrapTable>
                  </div>
                  {/* end::Body*/}
                </div>
              </>
          }
        </div>
      </div>
    </div>
  );
}

export default ExaminationDetailPage;