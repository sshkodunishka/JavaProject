import "./App.css";
import React from "react";
import { Switch, Route } from "react-router-dom";

import ListDocumentsComponent from "./Components/Document/ListDocumentsComponent";
import HeaderComponent from "./Components/Other/HeaderComponent";
import HomeComponent from "./Components/Other/HomeComponent";
import NotFound from "./Components/Other/NotFound";
import CreateDocumentComponent from "./Components/Document/CreateDocumentComponent";
import ViewDocumentComponent from "./Components/Document/ViewDocumentComponent";
import LoginComponent from "./Components/Auth/LoginComponent";
import RegisterComponent from "./Components/Auth/RegisterComponent";
import AuthService from "./services/AuthService";
import RoleRoute from "./helpers/RoleRouter";
import AuthRoute from "./helpers/AuthRouter";

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      currentUser: undefined,
      isAdmin: false,
    };
    this.logOut = this.logOut.bind(this);
    this.logIn = this.logIn.bind(this);
  }
  componentDidMount() {
    const user = AuthService.getCurrentUser();

    if (user) {
      this.setState({
        currentUser: user,
        isAdmin: user.roles.includes("ROLE_ADMIN"),
      });
    }
  }
  logOut() {
    AuthService.logout();
    this.setState({
      currentUser: undefined,
      isAdmin: false,
    });
  }
  logIn() {
    const user = AuthService.getCurrentUser();
    if (user) {
      this.setState({
        currentUser: user,
        isAdmin: user.roles.includes("ROLE_ADMIN"),
      });
    }
  }

  render() {
    const WrappedLogin = (props, logIn) => {
      return <LoginComponent {...props} onLogIn={this.logIn} />;
    };
    const WrappedRegister = (props, logIn) => {
      return <RegisterComponent {...props} onLogIn={this.logIn} />;
    };
    return (
      <div>
        <HeaderComponent user={this.state.currentUser} onLogOut={this.logOut} />
        <div className="container">
          <Switch>
            <Route path="/" exact component={HomeComponent}></Route>
            <AuthRoute
              path="/login"
              component={WrappedLogin}
              user={this.state.currentUser}
            ></AuthRoute>
            <AuthRoute
              path="/register"
              component={WrappedRegister}
              user={this.state.currentUser}
            ></AuthRoute>
            <RoleRoute
              path="/documents"
              component={ListDocumentsComponent}
              allowed="ROLE_USER"
              toPath="login"
            ></RoleRoute>
            <RoleRoute
              path="/view-document/:id"
              component={ViewDocumentComponent}
              allowed="ROLE_USER"
              toPath="login"
            ></RoleRoute>
            <RoleRoute
              path="/add-document/:id"
              component={CreateDocumentComponent}
              allowed="ROLE_ADMIN"
              toPath="documents"
            ></RoleRoute>
            <Route path="*" component={NotFound}></Route>
          </Switch>
        </div>
        {/* <FooterComponent /> */}
      </div>
    );
  }
}

export default App;
