import React, { Component } from "react";
import AuthService from "../../services/AuthService";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "../styles/Login.css";

class LoginComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      username: "",
      password: "",
    };
    this.changeUsernameHandler = this.changeUsernameHandler.bind(this);
    this.changePasswordHandler = this.changePasswordHandler.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.validateForm = this.validateForm.bind(this);
  }

  changeUsernameHandler = (event) => {
    this.setState({ username: event.target.value });
  };

  changePasswordHandler = (event) => {
    this.setState({ password: event.target.value });
  };

  handleSubmit(e) {
    e.preventDefault();
    console.log(this.state.username);
    AuthService.login(this.state.username, this.state.password).then((res) => {
      this.props.onLogIn();
      this.props.history.push("/documents");
    });
  }
  validateForm() {
    return this.state.username.length > 0 && this.state.password.length > 0;
  }

  render() {
    return (
      <div className="Login">
        <Form onSubmit={this.handleSubmit}>
          <Form.Group size="lg" controlId="username">
            <Form.Label>Username</Form.Label>
            <Form.Control
              autoFocus
              type="text"
              value={this.username}
              onChange={this.changeUsernameHandler}
            />
          </Form.Group>
          <Form.Group size="lg" controlId="password">
            <Form.Label>Password</Form.Label>
            <Form.Control
              type="password"
              value={this.password}
              onChange={this.changePasswordHandler}
            />
          </Form.Group>
          <Button block size="lg" type="submit" disabled={!this.validateForm()}>
            Login
          </Button>
        </Form>
      </div>
    );
  }
}

export default LoginComponent;
