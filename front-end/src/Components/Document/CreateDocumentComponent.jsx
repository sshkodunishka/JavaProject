import React, { Component } from "react";
import DocumentService from "../../services/DocumentService";

class CreateDocumentComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      // step 2
      id: this.props.match.params.id,
      title: "",
      description: "",
      file: null,
    };
    this.changeTitleHandler = this.changeTitleHandler.bind(this);
    this.changeDescriptionHandler = this.changeDescriptionHandler.bind(this);
    this.changeFileHandler = this.changeFileHandler.bind(this);
    this.saveOrUpdateDocument = this.saveOrUpdateDocument.bind(this);
  }

  componentDidMount() {
    if (this.state.id === "_add") {
      return;
    } else {
      DocumentService.getDocumentById(this.state.id).then((res) => {
        let document = res.data;
        this.setState({
          title: document.title,
          description: document.description,
        });
      });
    }
  }
  saveOrUpdateDocument = (e) => {
    e.preventDefault();

    if (this.state.id === "_add") {
      DocumentService.createDocument(
        this.state.file,
        this.state.title,
        this.state.description
      ).then((res) => {
        this.props.history.push("/documents");
      });
    } else {
      let document = {
        title: this.state.title,
        description: this.state.description,
      };
      console.log("document => " + JSON.stringify(document));
      DocumentService.updateDocument(this.state.id, document).then((res) => {
        this.props.history.push("/documents");
      });
    }
  };

  changeTitleHandler = (event) => {
    this.setState({ title: event.target.value });
  };

  changeDescriptionHandler = (event) => {
    this.setState({ description: event.target.value });
  };

  changeFileHandler = (event) => {
    this.setState({ file: event.target.files[0] });
  };

  cancel() {
    this.props.history.push("/documents");
  }

  getTitle() {
    if (this.state.id === "_add") {
      return <h3 className="text-center">Add Document</h3>;
    } else {
      return <h3 className="text-center">Update Document</h3>;
    }
  }
  getFile() {
    if (this.state.id === "_add") {
      return (
        <div className="form-group">
          <label> File: </label>
          <input
            type="file"
            name="file"
            className="form-control-file"
            onChange={this.changeFileHandler}
          />
        </div>
      );
    } else {
      return;
    }
  }
  render() {
    return (
      <div>
        <br></br>
        <div className="container">
          <div className="row">
            <div className="card col-md-6 offset-md-3 offset-md-3">
              {this.getTitle()}
              <div className="card-body">
                <form>
                  <div className="form-group">
                    <label> Title: </label>
                    <input
                      placeholder="Title"
                      name="title"
                      className="form-control"
                      value={this.state.title}
                      onChange={this.changeTitleHandler}
                    />
                  </div>
                  <div className="form-group">
                    <label> Description: </label>
                    <input
                      placeholder="Description"
                      name="description"
                      className="form-control"
                      value={this.state.description}
                      onChange={this.changeDescriptionHandler}
                    />
                  </div>
                  {this.getFile()}
                  <button
                    className="btn btn-success"
                    onClick={this.saveOrUpdateDocument}
                  >
                    Save
                  </button>
                  <button
                    className="btn btn-danger"
                    onClick={this.cancel.bind(this)}
                    style={{ marginLeft: "10px" }}
                  >
                    Cancel
                  </button>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default CreateDocumentComponent;
