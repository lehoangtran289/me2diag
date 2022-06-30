import React, {Component} from "react";
import {connect} from "react-redux";
import {Redirect} from "react-router-dom";
import {LayoutSplashScreen} from "../../../../_metronic/layout";
import * as auth from "../_redux/authRedux";

class Logout extends Component {
  componentDidMount() {
    this.props.logout();
  }

  render() {
    const { hasAccessToken } = this.props;
    return hasAccessToken ? <LayoutSplashScreen /> : <Redirect to="/auth/login" />;
  }
}

export default connect(
  ({ auth }) => ({ hasAccessToken: Boolean(auth.accessToken) }),
  auth.actions
)(Logout);
