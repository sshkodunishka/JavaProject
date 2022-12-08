import React, { Component } from "react";
import AuthService from "../../services/AuthService";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "../styles/Login.css";

class RegisterComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      username: "",
      firstName: "",
      lastName: "",
      email: "",
      confirmPassword: "",
      password: "",
    };
    this.changeUsernameHandler = this.changeUsernameHandler.bind(this);
    this.changeFirstNameHandler = this.changeFirstNameHandler.bind(this);
    this.changeLastNameHandler = this.changeLastNameHandler.bind(this);
    this.changeEmailHandler = this.changeEmailHandler.bind(this);
    this.changePasswordHandler = this.changePasswordHandler.bind(this);
    this.changeConfirmPasswordHandler = this.changeConfirmPasswordHandler.bind(
      this
    );
    this.handleSubmit = this.handleSubmit.bind(this);
    this.validateForm = this.validateForm.bind(this);
  }

  changeUsernameHandler = (event) => {
    this.setState({ username: event.target.value });
  };
  changeFirstNameHandler = (event) => {
    this.setState({ firstName: event.target.value });
  };
  changeLastNameHandler = (event) => {
    this.setState({ lastName: event.target.value });
  };
  changeEmailHandler = (event) => {
    this.setState({ email: event.target.value });
  };
  changePasswordHandler = (event) => {
    this.setState({ password: event.target.value });
  };
  changeConfirmPasswordHandler = (event) => {
    this.setState({ confirmPassword: event.target.value });
  };

  handleSubmit(e) {
    e.preventDefault();
    AuthService.register(
      this.state.username,
      this.state.email,
      this.state.firstName,
      this.state.lastName,
      this.state.password
    ).then((response) => {
      this.props.history.push("/login");
    });
  }
  validateForm() {
    return (
      this.state.username.length > 0 &&
      this.state.password.length > 0 &&
      this.state.firstName.length > 0 &&
      this.state.lastName.length > 0 &&
      this.state.confirmPassword.length > 0 &&
      this.state.email.length > 0
    );
  }

  render() {
    return (
      <div className="Register">
        <Form onSubmit={this.handleSubmit}>
          <Form.Group controlId="username">
            <Form.Label>Username</Form.Label>
            <Form.Control
              autoFocus
              type="text"
              value={this.username}
              onChange={this.changeUsernameHandler}
            />
          </Form.Group>
          <Form.Group controlId="firstName">
            <Form.Label>First Name</Form.Label>
            <Form.Control
              autoFocus
              type="text"
              value={this.firstName}
              onChange={this.changeFirstNameHandler}
            />
          </Form.Group>
          <Form.Group controlId="lastName">
            <Form.Label>Last Name</Form.Label>
            <Form.Control
              autoFocus
              type="text"
              value={this.lastName}
              onChange={this.changeLastNameHandler}
            />
          </Form.Group>
          <Form.Group controlId="email">
            <Form.Label>Email</Form.Label>
            <Form.Control
              autoFocus
              type="email"
              value={this.email}
              onChange={this.changeEmailHandler}
            />
          </Form.Group>
          <Form.Group controlId="password">
            <Form.Label>Password</Form.Label>
            <Form.Control
              type="password"
              value={this.password}
              onChange={this.changePasswordHandler}
            />
          </Form.Group>
          <Form.Group controlId="confirmPassword">
            <Form.Label>Confirm Password</Form.Label>
            <Form.Control
              type="password"
              value={this.confirmPassword}
              onChange={this.changeConfirmPasswordHandler}
            />
          </Form.Group>
          <Button block type="submit" disabled={!this.validateForm()}>
            Sign up
          </Button>
        </Form>
      </div>
    );
  }
}

export default RegisterComponent;
