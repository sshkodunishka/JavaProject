import axios from "axios";

const AUTH_API_BASE_URL = "http://localhost:8080/api/v1/auth";

class AuthService {
  login(username, password) {
    return axios
      .post(AUTH_API_BASE_URL + "/login", {
        username,
        password,
      })
      .then((response) => {
        if (response.data.token) {
          localStorage.setItem("user", JSON.stringify(response.data));
        }
        return response.data;
      });
  }
  logout() {
    localStorage.removeItem("user");
  }
  register(username, email, firstName, lastName, password) {
    return axios.post(AUTH_API_BASE_URL + "/register", {
      username,
      email,
      firstName,
      lastName,
      password,
    });
  }

  getCurrentUser() {
    const user = JSON.parse(localStorage.getItem("user"));
    console.log(user)
    return user
  }
}

export default new AuthService();
