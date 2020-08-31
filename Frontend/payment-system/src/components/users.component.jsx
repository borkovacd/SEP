import React, { Component } from "react";
import UserService from "../services/user.service";
import { Spinner, Button } from "reactstrap";
import { Link } from "react-router-dom";
import Table from "./Table";
import { usersTableConstants } from "../tableConstants/usersTableConstants";

class Users extends Component {
  constructor(props) {
    super(props);
    this.state = { users: [], isLoading: true };
  }

  componentDidMount() {
    this.setState({ isLoading: true });
    this.retrieveUsers();
  }

  retrieveUsers() {
    UserService.getAll()
      .then(response => {
        this.setState({
          users: response.data,
          isLoading: false
        });
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  }

  //naming convention HANDE-something
  handleBlock = item => () => {
    console.log("Handle Block ---> " + item.id);
    UserService.block(item.id, item)
      .then(response => {
        console.log(response.data);
        this.refreshView();
      })
      .catch(e => {
        console.log(e);
      });
  };

  handleDelete = item => () => {
    console.log("Handle Delete ---> " + item.id);
    UserService.delete(item.id)
      .then(response => {
        console.log(response.data);
        this.refreshView();
      })
      .catch(e => {
        console.log(e);
      });
  };

  refreshView() {
    this.retrieveUsers();
  }

  renderUsers() {
    const { users, isLoading } = this.state;

    if (isLoading) {
      return (
        <>
          <b>Loading...</b>
          <Spinner size="sm" color="dark" />
        </>
      );
    }

    if (users.length === 0)
      return (
        <div className="row ml-md-2 mt-3">
          <div className="col-10">
            <h3>There are no existing users at the moment.</h3>
          </div>
          <div className="col">
            <Button
              color="success"
              className="mr-auto ml-auto"
              tag={Link}
              to="/edit-user/new"
            >
              Add User
            </Button>
          </div>
        </div>
      );
    return (
      <div>
        <div className="row ml-md-2 mt-3">
          <div className="col-10">
            <h3>Users:</h3>
          </div>
          <div className="col">
            <Button
              color="success"
              className="mr-auto ml-auto"
              tag={Link}
              to="/edit-user/new"
            >
              Add User
            </Button>
          </div>
        </div>

        <Table
          cols={usersTableConstants(this.handleBlock, this.handleDelete)}
          data={users}
          isDark
          hoverable
        />
      </div>
    );
  }

  render() {
    return <div>{this.renderUsers()}</div>;
  }
}

export default Users;
