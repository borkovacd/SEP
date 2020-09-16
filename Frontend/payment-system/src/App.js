import React, { Component } from "react";
import { BrowserRouter as Router, Route, Switch, Link } from "react-router-dom";
import "./App.css";

import AuthService from "./services/auth.service";

import Home from "./components/home.component";
import Login from "./components/login.component";
import Register from "./components/register.component";
import Magazines from "./components/magazines.component";
import SuccessPage from "./components/successPage.component";
import ErrorPage from "./components/errorPage.component";
import CancelPage from "./components/cancelPage.component";

class App extends Component {
  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);

    this.state = {
      showAdminBoard: false,
      currentUser: undefined,
    };
  }

  componentDidMount() {
    const user = AuthService.getCurrentUser();

    if (user) {
      this.setState({
        currentUser: user,
        showAdminBoard: user.roles.includes("ROLE_ADMIN"),
      });
    }
  }

  logOut() {
    AuthService.logout();
    window.location.reload();
  }

  render() {
    const { currentUser, showAdminBoard } = this.state;

    return (
      <Router>
        <div id="header">
          <div className="brand">
            <div className="header-brand-title">
              <Link className="header-brand-title" to={"/"}>
                Payment System
              </Link>
            </div>
          </div>
          <div className="menu">
            {!currentUser && (
              <React.Fragment>
                <div className="btn-login">
                  <Link to={"/login"}>Sign In</Link>
                </div>
                <div className="btn-register">
                  <Link to={"/register"}>Sign Up</Link>
                </div>
              </React.Fragment>
            )}
            {currentUser && (
              <React.Fragment>
                <div className="btn-login">
                  <Link to={"/magazines"}>Magazines</Link>
                </div>
                <div className="btn-register" onClick={() => this.logOut()}>
                  <a>Logout</a>
                </div>
              </React.Fragment>
            )}
          </div>
        </div>

        <div className="container-md mt-3">
          <Switch>
            <Route exact path={["/", "/home"]} component={Home} />
            <Route exact path="/login" component={Login} />
            <Route exact path="/register" component={Register} />
            <Route exact path="/magazines" component={Magazines} />
            <Route exact path="/payment/success" component={SuccessPage} />
            <Route exact path="/payment/error" component={ErrorPage} />
            <Route exact path="/payment/cancel" component={CancelPage} />
          </Switch>
        </div>
      </Router>
    );
  }
}

export default App;
