import React, { Component } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
class CommentComponent extends Component {

  render() {
    return (
      <div className="comment panel panel-default">
        <div className="panel-heading">
          {/* <h4>{this.props.author}</h4> */}
          <h4>User</h4>
        </div>
        <div className="panel-body">{this.props.children}</div>
      </div>
    );
  }
}
export default CommentComponent;
