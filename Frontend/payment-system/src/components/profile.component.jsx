import React, { Component } from "react";
import AuthService from "../services/auth.service";
import PaymentService from "../services/payment.service";

import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";

const required = (value) => {
  if (!value) {
    return (
      <div className="alert alert-danger" role="alert">
        This field is required!
      </div>
    );
  }
};

export default class Profile extends Component {
  constructor(props) {
    super(props);

    this.handlePay = this.handlePay.bind(this);
    this.onChangeMagazine = this.onChangeMagazine.bind(this);

    this.state = {
      currentUser: AuthService.getCurrentUser(),
      magazine: 0,
      paymentType: "PAYPAL",
      loading: false,
      message: "",
    };
  }

  onChangeMagazine(e) {
    this.setState({
      magazine: e.target.value,
    });
  }

  handlePay(e) {
    e.preventDefault();

    this.setState({
      message: "",
      loading: true,
    });

    this.form.validateAll();

    if (this.checkBtn.context._errors.length === 0) {
      PaymentService.beginPaymentProcess(
        this.state.magazine,
        this.state.paymentType
      ).then(
        (response) => {
          console.log(response.data);
          window.location.assign(response.data);
          //this.props.history.push("/");
          //window.location.reload();
        },
        (error) => {
          const resMessage =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();

          this.setState({
            loading: false,
            message: resMessage,
          });
        }
      );
    } else {
      this.setState({
        loading: false,
      });
    }
  }

  render() {
    const { currentUser } = this.state;

    return (
      <div>
        <div className="container">
          <header className="jumbotron">
            <h3>
              <strong>{currentUser.username}</strong> Profile
            </h3>
          </header>
          <p>
            <strong>Token:</strong> {currentUser.accessToken.substring(0, 20)}{" "}
            ...{" "}
            {currentUser.accessToken.substr(
              currentUser.accessToken.length - 20
            )}
          </p>
          <p>
            <strong>Id:</strong> {currentUser.id}
          </p>
          <p>
            <strong>Email:</strong> {currentUser.email}
          </p>
          <strong>Authorities:</strong>
          <ul>
            {currentUser.roles &&
              currentUser.roles.map((role, index) => (
                <li key={index}>{role}</li>
              ))}
          </ul>
        </div>
        <div className="login-registration-page">
          <div className="card card-container">
            <Form
              onSubmit={this.handlePay}
              ref={(c) => {
                this.form = c;
              }}
            >
              <div className="form-group">
                <label htmlFor="magazine">Magazine</label>
                <Input
                  type="number"
                  className="form-control"
                  name="magazine"
                  value={this.state.magazine}
                  onChange={this.onChangeMagazine}
                  validations={[required]}
                />
              </div>

              <div className="form-group">
                <label htmlFor="paymentType">Payment Type</label>
                <Input
                  type="paymentType"
                  className="form-control"
                  name="paymentType"
                  value={this.state.paymentType}
                  validations={[required]}
                />
              </div>

              <div className="form-group">
                <button
                  className="btn btn-primary btn-block"
                  disabled={this.state.loading}
                >
                  {this.state.loading && (
                    <span className="spinner-border spinner-border-sm"></span>
                  )}
                  <span>Login</span>
                </button>
              </div>

              {this.state.message && (
                <div className="form-group">
                  <div className="alert alert-danger" role="alert">
                    {this.state.message}
                  </div>
                </div>
              )}
              <CheckButton
                style={{ display: "none" }}
                ref={(c) => {
                  this.checkBtn = c;
                }}
              />
            </Form>
          </div>
        </div>
      </div>
    );
  }
}
