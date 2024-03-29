/* eslint-disable no-script-url,jsx-a11y/anchor-is-valid */
import React from "react";
import {useLocation} from "react-router";
import {NavLink} from "react-router-dom";
// import SVG from "react-inlinesvg";
import {checkIsActive} from "../../../../_helpers";
import {shallowEqual, useSelector} from "react-redux";
import { ROLE_ADMIN, ROLE_EXPERT, ROLE_USER } from "../../../../../constants";

export function HeaderMenu({layoutProps}) {
  const location = useLocation();
  const getMenuItemActive = (url) => {
    return checkIsActive(location, url) ? "menu-item-active" : "";
  }

  const roles = useSelector((state) => state.auth.payload.roles, shallowEqual);

  return <div
    id="kt_header_menu"
    className={`header-menu header-menu-mobile ${layoutProps.ktMenuClasses}`}
    {...layoutProps.headerMenuAttributes}
  >
    {/*begin::Header Nav*/}
    <ul className={`menu-nav ${layoutProps.ulClasses}`}>
      {/*begin::1 Level*/}
      <li className={`menu-item menu-item-rel ${getMenuItemActive('/dashboard')}`}>
        <NavLink className="menu-link" to="/dashboard">
          <span className="menu-text">Dashboard</span>
          {layoutProps.rootArrowEnabled && (<i className="menu-arrow"/>)}
        </NavLink>
      </li>
      {/*end::1 Level*/}

      {/*begin::1 Level*/}
      {/*<li className={`menu-item menu-item-rel ${getMenuItemActive('/my-page')}`}>*/}
      {/*  <NavLink className="menu-link" to="/my-page">*/}
      {/*    <span className="menu-text">My Page</span>*/}
      {/*    {layoutProps.rootArrowEnabled && (<i className="menu-arrow"/>)}*/}
      {/*  </NavLink>*/}
      {/*</li>*/}
      {/*end::1 Level*/}

      {/*begin::1 Level*/}
      {
        roles && roles.includes(ROLE_USER) &&
          <>
            <li className={`menu-item menu-item-rel ${getMenuItemActive('/patients')}`}>
              <NavLink className="menu-link" to="/patients">
                <span className="menu-text">Patient list</span>
                {layoutProps.rootArrowEnabled && (<i className="menu-arrow"/>)}
              </NavLink>
            </li>
            <li className={`menu-item menu-item-rel ${getMenuItemActive('/examinations')}`}>
              <NavLink className="menu-link" to="/examinations">
                <span className="menu-text">Examination list</span>
                {layoutProps.rootArrowEnabled && (<i className="menu-arrow"/>)}
              </NavLink>
            </li>
          </>
      }
      {
        roles && roles.includes(ROLE_ADMIN) &&
        <>
          <li className={`menu-item menu-item-rel ${getMenuItemActive('/accounts')}`}>
            <NavLink className="menu-link" to="/accounts">
              <span className="menu-text">Account management</span>
              {layoutProps.rootArrowEnabled && (<i className="menu-arrow"/>)}
            </NavLink>
          </li>
        </>
      }
      {
        roles && roles.includes(ROLE_EXPERT) &&
        <>
          <li className={`menu-item menu-item-rel ${getMenuItemActive('/config/pfs')}`}>
            <NavLink className="menu-link" to="/config/pfs">
              <span className="menu-text">PFS Configs</span>
              {layoutProps.rootArrowEnabled && (<i className="menu-arrow"/>)}
            </NavLink>
          </li>
          <li className={`menu-item menu-item-rel ${getMenuItemActive('/config/kdc')}`}>
            <NavLink className="menu-link" to="/config/kdc">
              <span className="menu-text">KDC Configs</span>
              {layoutProps.rootArrowEnabled && (<i className="menu-arrow"/>)}
            </NavLink>
          </li>
        </>
      }

      {/*begin::1 Level*/}
      <li className={`menu-item menu-item-rel ${getMenuItemActive('/user-profile')}`}>
        <NavLink className="menu-link" to="/user-profile">
          <span className="menu-text">User Profile</span>
          {layoutProps.rootArrowEnabled && (<i className="menu-arrow"/>)}
        </NavLink>
      </li>
      {/*end::1 Level*/}

    </ul>
    {/*end::Header Nav*/}
  </div>;
}
