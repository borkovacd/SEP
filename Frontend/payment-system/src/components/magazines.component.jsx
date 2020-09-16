import React, { Component } from "react";
import AuthService from "../services/auth.service";
import PaymentService from "../services/payment.service";

import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";

import Select from "../components/controls/Select";

import { getMagazines } from "../utils/MagazinesUtil";

const required = (value) => {
  if (!value) {
    return (
      <div className="alert alert-danger" role="alert">
        This field is required!
      </div>
    );
  }
};

export default class Magazines extends Component {
  constructor(props) {
    super(props);

    this.handlePay = this.handlePay.bind(this);
    this.onChangeMagazine = this.onChangeMagazine.bind(this);

    this.state = {
      currentUser: AuthService.getCurrentUser(),
      magazine: 1,
      price: 99,
      paymentType: "PAYPAL",
      loading: false,
      message: "",
    };
  }

  onChangeMagazine(e) {
    let price = 0;
    if (e.target.value === 1) {
      price = 99;
    } else if (e.target.value === 2) {
      price = 150;
    } else {
      price = 49;
    }

    this.setState({
      magazine: e.target.value,
      price: price,
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
        this.state.price,
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
      <div className="login-registration-page">
        <div className="card card-container">
          <Form
            onSubmit={this.handlePay}
            ref={(c) => {
              this.form = c;
            }}
          >
            <div className="form-group">
              <label htmlFor="chooseMagazine">Magazine</label>
              <Select
                placeholder="Choose magazine"
                items={getMagazines()}
                selectedItem={this.state.magazine}
                onChange={this.onChangeMagazine}
                displayKey={"name"}
                valueKey={"value"}
              />
            </div>

            <div className="form-group">
              <label htmlFor="price">Price</label>
              <Input
                readOnly={true}
                type="number"
                className="form-control"
                name="price"
                value={this.state.price}
                validations={[required]}
              />
            </div>

            <div className="form-group">
              <label htmlFor="paymentType">Payment Type</label>
              <Input
                readOnly={true}
                type="paymentType"
                className="form-control"
                name="paymentType"
                value={this.state.paymentType}
                validations={[required]}
              />
            </div>

            <div className="form-group">
              <br />
              <button
                className="btn btn-primary btn-block"
                disabled={this.state.loading}
              >
                {this.state.loading && (
                  <span className="spinner-border spinner-border-sm"></span>
                )}

                <span>Proceed to payment</span>
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
    );
  }
}
