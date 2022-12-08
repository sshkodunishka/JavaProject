import axios from "axios";
import authHeader from "../helpers/AuthHeader";

const DOCUMENTS_API_BASE_URL = "http://localhost:8080/api/v1/documents";

class DocumentService {
  getDocuments(query) {
    if(query){
      return axios.post(DOCUMENTS_API_BASE_URL + `/search?search=${query}`, {}, {
        headers: { Authorization: authHeader() },
      });
    }
    return axios.get(DOCUMENTS_API_BASE_URL, {
      headers: { Authorization: authHeader() },
    });
  }
  getDocumentById(id) {
    return axios.get(DOCUMENTS_API_BASE_URL + `/${id}`, {
      headers: { Authorization: authHeader() },
    });
  }
  createDocument(file, title, description) {
    let bodyFormData = new FormData();
    bodyFormData.append("title", title);
    bodyFormData.append("description", description);
    bodyFormData.append("file", file);
    return axios({
      method: "post",
      url: DOCUMENTS_API_BASE_URL,
      data: bodyFormData,
      headers: {
        Authorization: authHeader(),
        "Content-Type": "multipart/form-data",
      },
    });
  }
  updateDocument(id, document) {
    return axios.put(DOCUMENTS_API_BASE_URL + `/${id}`, document, {
      headers: { Authorization: authHeader() },
    });
  }
  deleteDocument(id) {
    return axios.delete(DOCUMENTS_API_BASE_URL + `/${id}`, {
      headers: { Authorization: authHeader() },
    });
  }
  downloadDocument(fileName) {
    return axios.get(DOCUMENTS_API_BASE_URL + "/downloadFile/" + fileName, {
      responseType: "blob", // important
      headers: { Authorization: authHeader() },
    });
  }
  addCommentDocument(id, commentMsg) {
    return axios.post(
      `${DOCUMENTS_API_BASE_URL}/${id}/comments`,
      { commentMsg },
      {
        headers: { Authorization: authHeader() },
      }
    );
  }
}

export default new DocumentService();
