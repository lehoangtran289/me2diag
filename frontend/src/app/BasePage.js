import React, {Suspense, lazy} from "react";
import {Redirect, Switch, Route} from "react-router-dom";
import {LayoutSplashScreen, ContentRoute} from "../_metronic/layout";
import {BuilderPage} from "./pages/BuilderPage";
import {MyPage} from "./pages/MyPage";
import {DashboardPage} from "./pages/DashboardPage";
import {shallowEqual, useSelector} from "react-redux";
import {ROLE_ADMIN, ROLE_EXPERT, ROLE_USER} from "../constants";
import PatientPage from "./modules/Patient/PatientPage";
import ExaminationPage from "./modules/Examination/ExaminationPage";

// const GoogleMaterialPage = lazy(() =>
//   import("./modules/GoogleMaterialExamples/GoogleMaterialPage")
// );
// const ReactBootstrapPage = lazy(() =>
//   import("./modules/ReactBootstrapExamples/ReactBootstrapPage")
// );
// const ECommercePage = lazy(() =>
//   import("./modules/ECommerce/pages/eCommercePage")
// );
const UserProfilePage = lazy(() =>
  import("./modules/UserProfile/UserProfilePage")
);

/**
 * Any updates on Route/ContentRoute here also requires update on these files:
 * AsideMenuList.js
 * HeaderMenu.js
 */
export default function BasePage() {
  // useEffect(() => {
  //   console.log('Base page');
  // }, [])

  const roles = useSelector((state) => state.auth.payload.roles, shallowEqual);

  return (
    <Suspense fallback={<LayoutSplashScreen/>}>
      <Switch>
        {
          /* Redirect from root URL to /dashboard. */
          <Redirect exact from="/" to="/dashboard"/>
        }

        {/* CONTENT ROUTE */}
        <ContentRoute path="/dashboard" component={DashboardPage}/>
        {
          roles.includes(ROLE_USER) ?
            <>
              <ContentRoute path="/examinations" component={ExaminationPage}/>
              <ContentRoute path="/patients" component={PatientPage}/>
            </>
            : ""
        }

        {/*<ContentRoute path="/my-page" component={MyPage}/>*/}
        {/*<ContentRoute path="/builder" component={BuilderPage}/>*/}
        {/*<Route path="/google-material" component={GoogleMaterialPage} />*/}
        {/*<Route path="/react-bootstrap" component={ReactBootstrapPage} />*/}
        {/*<Route path="/e-commerce" component={ECommercePage} />*/}

        {/* ROUTE */}
        <Route path="/user-profile" component={UserProfilePage}/>
        <Redirect to="error/error-v1"/>
      </Switch>
    </Suspense>
  );
}
