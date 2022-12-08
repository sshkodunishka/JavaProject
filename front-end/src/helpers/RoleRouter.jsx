import React from "react";
import { Route, Redirect } from "react-router-dom";

import AuthService from "../services/AuthService";

export default function RoleRoute({
  component: Component,
  allowed,
  toPath,
  ...rest
}) {
  return (
    <Route
      {...rest}
      render={(props) => 
        AuthService.getCurrentUser() &&
        (AuthService.getCurrentUser().roles.includes(allowed) 
        ||  AuthService.getCurrentUser().roles.includes('ROLE_ADMIN')) ? (
          <Component {...props} />
        ) : (
          <Redirect
            to={{
              pathname: "/" + toPath,
            }}
          />
        )
      }
    />
  );
}
