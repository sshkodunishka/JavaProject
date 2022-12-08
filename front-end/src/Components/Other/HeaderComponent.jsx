import React, { Component } from "react";
import { Navbar, Nav } from "react-bootstrap";
import { LinkContainer } from "react-router-bootstrap";

class HeaderComponent extends Component {
  constructor(props) {
    super(props);
    this.state = {};
    this.logOut = this.logOut.bind(this);
  }
  logOut(e) {
    this.props.onLogOut();
    this.props.history.push("/login");
  }
  render() {
    return (
      <Navbar collapseOnSelect bg="light" expand="md" className="mb-3">
        <LinkContainer to="/">
          <Navbar.Brand className="font-weight-bold text-muted">
            Document
          </Navbar.Brand>
        </LinkContainer>
        <Navbar.Toggle />
        <Navbar.Collapse className="justify-content-end">
          {!this.props.user ? (
            <Nav activeKey={window.location.pathname}>
              <LinkContainer to="/register">
                <Nav.Link>Sign up</Nav.Link>
              </LinkContainer>
              <LinkContainer to="/login">
                <Nav.Link>Login</Nav.Link>
              </LinkContainer>
            </Nav>
          ) : (
            <Nav activeKey={window.location.pathname}>
              <LinkContainer to="/documents">
                <Nav.Link>List</Nav.Link>
              </LinkContainer>
              <LinkContainer to="/" onClick={this.logOut}>
                <Nav.Link>Logout</Nav.Link>
              </LinkContainer>
            </Nav>
          )}
        </Navbar.Collapse>
      </Navbar>
    );
  }
}

export default HeaderComponent;
