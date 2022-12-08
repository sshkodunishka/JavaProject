import React, { Component } from "react";
import Comment from "./CommentComponent";
import "bootstrap/dist/css/bootstrap.min.css";
class CommentListComponent extends Component {

  render() {
    let commentNodes = this.props.comments.map(function (comment, index) {
      return (
        //author={comment.author}
        <Comment key={index}>{comment.commentMsg}</Comment>
      );
    });
    return <div className="commentList">{commentNodes}</div>;
  }
}

export default CommentListComponent;
