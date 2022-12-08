import React, { Component } from "react";
import CommentList from "./CommentListComponent";
import CommentFormComponent from "./CommentFormComponent";
class CommentBoxComponent extends Component {
  constructor(props) {
    super(props);
    this.state = { comments: [] };
  }

  handleCommentSubmit(newComment) {
    let comments = this.state.comments;
    let newComments = comments.concat([newComment]);
    this.setState({ comments: newComments });
  }

  render() {
    return (
      <div className="container">
        <div className="commentBox panel panel-default">
          <div className="panel-body">
            <h1>Comment Box</h1>
            <CommentFormComponent
              id={this.props.id}
              onCommentSubmit={this.handleCommentSubmit.bind(this)}
            />
            <br></br>
            <CommentList comments={this.props.comments} />
          </div>
        </div>
      </div>
    );
  }
}
export default CommentBoxComponent;
