import React, { Component } from "react";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";
import DocumentService from "../../services/DocumentService";
import CommentBoxComponent from "./Comments/CommentBoxComponent";
class ViewDocumentComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      id: this.props.match.params.id,
      document: {},
      comments: [],
    };
    this.downloadDocument = this.downloadDocument.bind(this);
  }

  componentDidMount() {
    DocumentService.getDocumentById(this.state.id).then((res) => {
      this.setState({ document: res.data });
      this.setState({ comments: res.data.comments });
    });
  }

  downloadDocument() {
    let fileName = this.state.document.fileName;
    let nameToSave =
      this.state.document.title + "." + fileName.split(".").pop();
    console.log(nameToSave);
    DocumentService.downloadDocument(fileName).then((res) => {
      const url = window.URL.createObjectURL(new Blob([res.data]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", nameToSave);
      document.body.appendChild(link);
      link.click();
    });
  }

  render() {
    return (
      <div>
        <br></br>
        <Card className="card col-md-10 offset-md-1">
          <Card.Body style={{ margin: "0 auto" }}>
            <Card.Title className="text-center">
              {this.state.document.title}
            </Card.Title>
            <Card.Text className="text-center">
              {this.state.document.description}
            </Card.Text>
            <Button onClick={() => this.downloadDocument()} variant="primary">
              Download
            </Button>
          </Card.Body>
        </Card>

        <CommentBoxComponent
          comments={this.state.comments}
          id={this.state.document.id}
        />
      </div>
    );
  }
}

export default ViewDocumentComponent;
