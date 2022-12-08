import React, { Component } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import DocumentService from "../../../services/DocumentService";
import AuthService from "../../../services/AuthService";

import "bootstrap/dist/css/bootstrap.min.css";
class CommentFormComponent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      comment: "",
    };

    this.handleSubmit = this.handleSubmit.bind(this);
    this.changeCommentHandler = this.changeCommentHandler.bind(this);
  }
  componentDidMount() {
    const user = AuthService.getCurrentUser();
    if (user) {
      this.setState({
        username: user.username,
      });
    }
  }

  changeCommentHandler = (event) => {
    this.setState({ comment: event.target.value });
  };
  handleSubmit(e) {
    e.preventDefault();
    DocumentService.addCommentDocument(this.props.id, this.state.comment).then(
      (res) => {
        this.props.onCommentSubmit({
          author: this.state.username,
          text: this.state.comment,
        });
      }
    );
  }

  render() {
    return (
      <Form className="form" onSubmit={this.handleSubmit.bind(this)}>
        <Form.Group size="lg" controlId="username">
          <Form.Label>Comment</Form.Label>
          <Form.Control
            autoFocus
            type="text"
            value={this.comment}
            onChange={this.changeCommentHandler}
          />
        </Form.Group>
        <Button variant="primary" type="submit">
          Post
        </Button>
      </Form>
    );
  }
}

export default CommentFormComponent;
