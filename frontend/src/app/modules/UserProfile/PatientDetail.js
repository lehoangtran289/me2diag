import React from 'react';
import { toAbsoluteUrl } from '../../../_metronic/_helpers';

function PatientDetail({ patient }) {

  const handleUploadAvatar = (e) => {
  };

  const removePic = () => {
  };

  return (
    <div className={'mt-5'}>
      <div className='form-group row'>
        <label className='col-xl-4 col-lg-3 col-form-label'>Avatar</label>
        <div className='col-lg-9 col-xl-8'>
          <div
            className='image-input image-input-outline'
            id='kt_profile_avatar'
            style={{
              backgroundImage: `url(${toAbsoluteUrl(
                '/media/users/blank.png'
              )}`
            }}
          >
            <div
              className='image-input-wrapper'
              style={{ backgroundImage: `url(${patient.avatarUrl})`}}
            />
            <label
              className='btn btn-xs btn-icon btn-circle btn-white btn-hover-text-primary btn-shadow'
              data-action='change'
              data-toggle='tooltip'
              title=''
              data-original-title='Change avatar'
            >
              <i className='fa fa-pen icon-sm text-muted' />
              <input
                type='file'
                name='file'
                accept='.png, .jpg, .jpeg'
                onChange={handleUploadAvatar}
                // {...formik.getFieldProps("pic")}
              />
              <input type='hidden' name='profile_avatar_remove' />
            </label>
            <span
              className='btn btn-xs btn-icon btn-circle btn-white btn-hover-text-primary btn-shadow'
              data-action='cancel'
              data-toggle='tooltip'
              title=''
              data-original-title='Cancel avatar'
            >
                      <i className='ki ki-bold-close icon-xs text-muted' />
                    </span>
            <span
              onClick={removePic}
              className='btn btn-xs btn-icon btn-circle btn-white btn-hover-text-primary btn-shadow'
              data-action='remove'
              data-toggle='tooltip'
              title=''
              data-original-title='Remove avatar'
            >
                      <i className='ki ki-bold-close icon-xs text-muted' />
                    </span>
          </div>
          <span className='form-text text-muted'>
                    Allowed file types: png, jpg, jpeg.
                  </span>
        </div>
      </div>

      <div className="form-group row">
        <label className='col-xl-4 col-lg-3 col-form-label'>Name</label>
        <div className='col-lg-9 col-xl-8'>
          <input
            type="text"
            disabled
            value={patient.name ? patient.name : ''}
            className={`form-control form-control-solid mr-0 ml-auto`}
          />
        </div>
      </div>

      <div className="form-group row">
        <label className='col-xl-4 col-lg-3 col-form-label'>PatientID</label>
        <div className='col-lg-9 col-xl-8'>
          <input
            type="text"
            disabled
            value={patient.id ? patient.id : ''}
            className={`form-control form-control-solid mr-0 ml-auto`}
          />
        </div>
      </div>

      <div className="form-group row">
        <label className='col-xl-4 col-lg-3 col-form-label'>Gender</label>
        <div className='col-lg-9 col-xl-8'>
          <input
            type="text"
            disabled
            value={patient.gender ? patient.gender : ''}
            className={`form-control form-control-solid mr-0 ml-auto`}
          />
        </div>
      </div>

      <div className="form-group row">
        <label className='col-xl-4 col-lg-3 col-form-label'>Date of birth</label>
        <div className='col-lg-9 col-xl-8'>
          <input
            type="text"
            disabled
            value={patient.birthDate ? patient.birthDate : ''}
            className={`form-control form-control-solid mr-0 ml-auto`}
          />
        </div>
      </div>
    </div>
  );
}

export default PatientDetail;
