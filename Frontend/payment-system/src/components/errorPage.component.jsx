import React, { Component } from "react";

class ErrorPage extends Component {
  state = {};
  render() {
    return (
      <div className="login-registration-page">
        <div className="card card-container">
          <h1>Payment process failed!</h1>
        </div>
      </div>
    );
  }
}

export default ErrorPage;
