import React, { Component } from "react";
import {
  Collapse,
  Navbar,
  NavbarToggler,
  NavbarBrand,
  Nav,
  NavItem,
  NavLink,
  UncontrolledDropdown,
  DropdownToggle,
  DropdownMenu,
  DropdownItem
} from "reactstrap";
import { Link } from "react-router-dom";

export default class AppNavbar extends Component {
  constructor(props) {
    super(props);
    this.state = { isOpen: false, isLoggedIn: false };
    this.toggle = this.toggle.bind(this);
  }

  toggle() {
    this.setState({
      isOpen: !this.state.isOpen
    });
  }

  render() {
    const isLoggedIn = this.state.isLoggedIn;
    console.log("Is logged in: " + isLoggedIn);
    return (
      <Navbar color="dark" dark expand="md">
        <NavbarBrand tag={Link} to="/">
          Rent & Drive
        </NavbarBrand>
        <NavbarToggler onClick={this.toggle} />
        <Collapse isOpen={this.state.isOpen} navbar>
          <Nav className="mr-auto" navbar>
            <NavItem>
              <NavLink href="/vehicles/">Vehicles</NavLink>
            </NavItem>
            <NavItem>
              <NavLink href="/users">Users</NavLink>
            </NavItem>
            <NavItem>
              <NavLink href="/rentals/">Rentals</NavLink>
            </NavItem>
            <UncontrolledDropdown nav inNavbar>
              <DropdownToggle nav caret>
                About me
              </DropdownToggle>
              <DropdownMenu right>
                <DropdownItem>
                  <a href="https://www.facebook.com/dragan.borkovac.7">
                    Facebook
                  </a>
                </DropdownItem>
                <DropdownItem>
                  <a href="https://github.com/borkovacd/RentAndDrive-V2">
                    GitHub
                  </a>
                </DropdownItem>
              </DropdownMenu>
            </UncontrolledDropdown>
          </Nav>
          <Nav className="ml-auto" navbar>
            <NavItem>
              {isLoggedIn ? (
                <NavLink href="/logout/">Log Out</NavLink>
              ) : (
                <NavLink href="/login/">Log In</NavLink>
              )}
            </NavItem>
            <NavItem>
              <NavLink href="/register/">Sing Up</NavLink>
            </NavItem>
          </Nav>
        </Collapse>
      </Navbar>
    );
  }
}
