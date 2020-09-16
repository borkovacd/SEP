import React, { Component } from "react";
import UserService from "../services/user.service";

class Home extends Component {
  constructor(props) {
    super(props);

    this.state = {
      content: "",
    };
  }

  componentDidMount() {
    UserService.getPublicContent().then(
      (response) => {
        this.setState({
          content: response.data,
        });
      },
      (error) => {
        this.setState({
          content:
            (error.response && error.response.data) ||
            error.message ||
            error.toString(),
        });
      }
    );
  }

  render() {
    return (
      <div className="container login-registration-page">
        <header className="jumbotron">
          <h1>Welcome</h1>
          <h2>
            <i>Sing in to pick magazine of your choice</i>
          </h2>
        </header>
      </div>
    );
  }
}

export default Home;
