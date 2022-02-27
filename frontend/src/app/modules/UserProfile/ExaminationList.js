import React, { useEffect, useState } from 'react';
import { getAllExaminations } from '../Dashboard/_redux/patients/PatientsCrud';
import { Button } from 'react-bootstrap';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from '@material-ui/core';
import TablePagination from '@mui/material/TablePagination';
import ExaminationDetailModal from './ExaminationDetailModal';

function ExaminationList(props) {

  const [examinations, setExaminations] = useState([]);
  const [currentExamination, setCurrentExamination] = useState();

  const [totalItems, setTotalItems] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [currentPage, setCurrentPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);

  useEffect(() => {
    getAllExaminations()
      .then(r => {
        console.log(r);
        setExaminations(r.data['items']);
        setTotalPages(r.data['total_pages']);
        setTotalItems(r.data['total_items']);
        setCurrentPage(r.data['current_page']);
      })
      .catch(error => {
        console.log('Error get all examinations: ' + error);
      });
  }, []);

  const handleChangePage = (event, newPage) => {
    setCurrentPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    console.log(event.target.value);
    setRowsPerPage(+event.target.value);
    setCurrentPage(0);
  };

  const columns = [
    { id: 'examination_id', label: 'Examination ID', minWidth: 170 },
    { id: 'username', label: 'PIC', minWidth: 170 },
    { id: 'patient_id', label: 'Patient ID', minWidth: 170 },
    { id: 'patient_name', label: 'Patient Name', minWidth: 170 },
    { id: 'date', label: 'Date examined', minWidth: 170 }
  ];

  const [openPatientExamModal, setOpenPatientExamModal] = useState(false);
  const handlePatientExamModalOpen = (examinationId) => {
    setCurrentExamination(examinations.find(e => e.id === examinationId));
    setOpenPatientExamModal(true);
  };
  const handlePatientExamModalClose = () => {
    setOpenPatientExamModal(false);
  }

  return (
    <div className={'card card-custom'}>
      {/* begin::Header */}
      <div className='card-header py-3'>
        <div className='card-title align-items-start flex-column'>
          <h3 className='card-label font-weight-bolder text-dark'>
            Examinations list
          </h3>
          <span className='text-muted font-weight-bold font-size-sm mt-1'>
            Display all examinations in the system
          </span>
        </div>
      </div>
      {/* end::Header */}
      <ExaminationDetailModal openModal={openPatientExamModal} handlePatientExamModalClose={handlePatientExamModalClose} examination={currentExamination}/>
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
                       Action
                    </span>
                  </TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {examinations
                  .slice(currentPage * rowsPerPage, currentPage * rowsPerPage + rowsPerPage)
                  .map((examination) => {
                    return (
                      <TableRow hover role='checkbox' tabIndex={-1} key={examination.id}>
                        {columns.map((column) => {
                          return (
                            <TableCell key={column.id} align={'left'}>
                              <span className='card-label font-size-lg text-dark'>
                                {examination[column.id]}
                              </span>
                            </TableCell>
                          );
                        })}
                        <TableCell
                          key={'action'}
                          align={'left'}
                        >
                          <Button
                            onClick={() => handlePatientExamModalOpen(examination.id)}
                            variant='secondary'
                            style={{ justifyContent: 'flex-start' }}
                          >
                            View Details
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
            count={totalItems}
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

export default ExaminationList;
