/* eslint-disable jsx-a11y/role-supports-aria-props */
/* eslint-disable no-script-url,jsx-a11y/anchor-is-valid */
import React from "react";
import {useLocation} from "react-router";
import {NavLink} from "react-router-dom";
import SVG from "react-inlinesvg";
import {toAbsoluteUrl, checkIsActive} from "../../../../_helpers";
import {shallowEqual, useSelector} from "react-redux";
import {ROLE_EXPERT, ROLE_USER} from "../../../../../constants";

export function AsideMenuList({layoutProps}) {
  const location = useLocation();
  const getMenuItemActive = (url, hasSubmenu = false) => {
    return checkIsActive(location, url)
      ? ` ${!hasSubmenu &&
      "menu-item-active"} menu-item-open menu-item-not-hightlighted`
      : "";
  };

  const roles = useSelector((state) => state.auth.payload.roles, shallowEqual);

  return (
    <>
      {/* begin::Menu Nav */}
      <ul className={`menu-nav ${layoutProps.ulClasses}`}>
        {/*begin::1 Level*/}
        <li
          className={`menu-item ${getMenuItemActive("/dashboard", false)}`}
          aria-haspopup="true"
        >
          <NavLink className="menu-link" to="/dashboard">
            <span className="svg-icon menu-icon">
              <SVG src={toAbsoluteUrl("/media/svg/icons/Design/Layers.svg")}/>
            </span>
            <span className="menu-text">Dashboard</span>
          </NavLink>
        </li>
        {/*end::1 Level*/}

        {/*begin::1 Level*/}
        {/*<li*/}
        {/*  className={`menu-item ${getMenuItemActive("/my-page", false)}`}*/}
        {/*  aria-haspopup="true"*/}
        {/*>*/}
        {/*  <NavLink className="menu-link" to="/my-page">*/}
        {/*    <span className="svg-icon menu-icon">*/}
        {/*      <SVG src={toAbsoluteUrl("/media/svg/icons/Design/Layers.svg")}/>*/}
        {/*    </span>*/}
        {/*    <span className="menu-text">My Page</span>*/}
        {/*  </NavLink>*/}
        {/*</li>*/}
        {/*end::1 Level*/}

        {/*begin::1 Level*/}
        {/* end:: section */}
        {
          roles && roles.includes(ROLE_USER) ?
            <>
              <li className="menu-section ">
                <h4 className="menu-text">Functionalities</h4>
                <i className="menu-icon flaticon-more-v2"></i>
              </li>
              <li
                className={`menu-item ${getMenuItemActive("/patients", false)}`}
                aria-haspopup="true"
              >
                <NavLink className="menu-link" to="/patients">
                  <span className="svg-icon menu-icon">
                    <SVG src={toAbsoluteUrl("/media/svg/icons/Communication/Group.svg")}/>
                  </span>
                  <span className="menu-text">Patient list</span>
                </NavLink>
              </li>
              <li
                className={`menu-item ${getMenuItemActive("/examinations", false)}`}
                aria-haspopup="true"
              >
                <NavLink className="menu-link" to="/examinations">
                  <span className="svg-icon menu-icon">
                    <SVG src={toAbsoluteUrl("/media/svg/icons/Communication/Clipboard-list.svg")}/>
                  </span>
                  <span className="menu-text">Examination list</span>
                </NavLink>
              </li>
            </>
            : ""
        }
        {/*end::1 Level*/}

        {/*begin::1 Level*/}
        <li className="menu-section ">
          <h4 className="menu-text">Profiles</h4>
          <i className="menu-icon flaticon-more-v2"></i>
        </li>
        <li
          className={`menu-item ${getMenuItemActive("/user-profile", false)}`}
          aria-haspopup="true"
        >
          <NavLink className="menu-link" to="/user-profile">
            <span className="svg-icon menu-icon">
              <SVG
                src={toAbsoluteUrl(
                  "/media/svg/icons/Communication/Add-user.svg"
                )}
              />
            </span>
            <span className="menu-text">User Profile</span>
          </NavLink>
        </li>
        {/*end::1 Level*/}
      </ul>

      {/* end::Menu Nav */}
    </>
  );
}
