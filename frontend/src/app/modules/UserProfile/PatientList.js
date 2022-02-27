import React, { useEffect, useState } from 'react';
import { getAllPatients } from '../Dashboard/_redux/patients/PatientsCrud';
import { Button } from 'react-bootstrap';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from '@material-ui/core';
import TablePagination from '@mui/material/TablePagination';
import CreatePatientModal from './CreatePatientModal';
import { useHistory } from 'react-router-dom';

function PatientList(props) {
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
        setPatients(r.data['items']);
        setTotalPages(r.data['total_pages']);
        setTotalItems(r.data['total_items']);
        setCurrentPage(r.data['current_page']);
      })
      .catch(error => {
        console.log('Error get all patients: ' + error);
      });
  }, [update]);

  const handleChangePage = (event, newPage) => {
    setCurrentPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    console.log(event.target.value);
    setRowsPerPage(+event.target.value);
    setCurrentPage(0);
  };

  const columns = [
    { id: 'id', label: 'Patient ID', minWidth: 170 },
    { id: 'name', label: 'Name', minWidth: 170 },
    {
      id: 'birthDate',
      label: 'Date of birth',
      minWidth: 170
    },
    { id: 'gender', label: 'Gender', minWidth: 170 }
  ];

  const handleDiagnoseButtonClick = (patientId) => {
    let path = `/user-profile/patient/${patientId}/diagnose`;
    history.push({
      pathname: path,
      state: {
        patient: patients.find(p => p.id === patientId)
      }
    });
  };

  const [openPatientCreateModal, setOpenPatientCreateModal] = useState(false);
  const handleCreatePatientModalOpen = () => {
    setOpenPatientCreateModal(true);
  };
  const handlePatientCreateModalClose = () => {
    setOpenPatientCreateModal(false);
  }

  return (
    <div className={'card card-custom'}>
      {/* begin::Header */}
      <div className='card-header py-3'>
        <div className='card-title align-items-start flex-column'>
          <h3 className='card-label font-weight-bolder text-dark'>
            Patient list
          </h3>
          <span className='text-muted font-weight-bold font-size-sm mt-1'>
            Display all patients in the system
          </span>
        </div>
        <div className='card-title align-items-end flex-column'>
          <Button
            onClick={handleCreatePatientModalOpen}
            variant='primary'
            style={{ justifyContent: 'flex-start' }}
          >
            Create new patient
          </Button>
        </div>
      </div>
      {/* end::Header */}
      <CreatePatientModal openModal={openPatientCreateModal} handlePatientCreateModalClose={handlePatientCreateModalClose} update={update} setUpdate={setUpdate}/>
      <div className='card-body'>
        <div>
          {/* begin::filter */}
          <div>
          {/*TODO: add name filter*/}
          </div>
          {/* end::filter */}
          {/* begin::table */}
          <TableContainer sx={{ maxHeight: 440 }}>
            <Table aria-label='sticky table'>
              <TableHead>
                <TableRow>
                  {columns.map((column) => (
                    <TableCell
                      key={column.id}
                      align={'left'}
                      style={{ minWidth: column.minWidth }}
                    >
                      <span className='card-label font-size-lg text-dark'>
                        {column.label}
                      </span>
                    </TableCell>
                  ))}
                  <TableCell
                    key={'actions'}
                    align={'left'}
                    style={{ minWidth: 170 }}
                  >
                    <span className='card-label font-size-lg text-dark'>
                       Actions
                    </span>
                  </TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {patients
                  .slice(currentPage * rowsPerPage, currentPage * rowsPerPage + rowsPerPage)
                  .map((patient) => {
                    return (
                      <TableRow hover role='checkbox' tabIndex={-1} key={patient.id}>
                        {columns.map((column) => {
                          return (
                            <TableCell key={column.id} align={'left'}>
                              <span className='card-label font-size-lg text-dark'>
                                {patient[column.id]}
                              </span>
                            </TableCell>
                          );
                        })}
                        <TableCell
                          key={'action'}
                          align={'left'}
                        >
                          <Button
                            onClick={() => handleDiagnoseButtonClick(patient.id)}
                            variant='secondary'
                            style={{ justifyContent: 'flex-start' }}
                          >
                            Diagnose
                          </Button>
                        </TableCell>
                      </TableRow>
                    );
                  })}
              </TableBody>
            </Table>
          </TableContainer>
          <TablePagination
            rowsPerPageOptions={[5, 10, 15]}
            component='div'
            count={patients.length}
            rowsPerPage={rowsPerPage}
            page={currentPage}
            onPageChange={handleChangePage}
            onRowsPerPageChange={handleChangeRowsPerPage}
          />
        </div>
      </div>
    </div>
  );
}

export default PatientList;