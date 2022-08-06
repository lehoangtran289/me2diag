import React, { useEffect } from "react";
import { Redirect, Route, Switch } from "react-router-dom";
import { useSubheader } from "../../../_metronic/layout";
import AccountInformation from "./AccountInformation";
// import { ProfileOverview } from "./ProfileOverview";
import ChangePassword from "./ChangePassword";
import PersonaInformation from "./PersonaInformation";
// import EmailSettings from "./EmailSettings";
import { ProfileCard } from "./components/ProfileCard";

export default function UserProfilePage() {
  const subheader = useSubheader();

  useEffect(() => {
    subheader.setTitle("User profile");
  })

  return (
    <div className={"container-fluid px-0"}>
      <div className="row px-0">
        <ProfileCard/>
        <div className="col-lg-8 col-sm-12">
          <Switch>
            <Redirect
              from="/user-profile"
              exact={true}
              to="/user-profile/personal-information"
            />
            {/*<Route*/}
            {/*  path="/user-profile/profile-overview"*/}
            {/*  component={ProfileOverview}*/}
            {/*/>*/}
            <Route
              path="/user-profile/account-information"
              component={AccountInformation}
            />
            <Route
              path="/user-profile/change-password"
              component={ChangePassword}
            />
            {/*<Route*/}
            {/*  path="/user-profile/email-settings"*/}
            {/*  component={EmailSettings}*/}
            {/*/>*/}
            <Route
              path="/user-profile/personal-information"
              component={PersonaInformation}
            />
          </Switch>
        </div>
      </div>
    </div>
  );
}
