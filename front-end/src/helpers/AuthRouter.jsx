import React from "react";
import { Route, Redirect } from "react-router-dom";

export default function AuthRouter({ component: Component, user, ...rest }) {
  return (
    <Route
      {...rest}
      render={(props) =>
        !user ? (
          <Component {...props} />
        ) : (
          <Redirect
            to={{
              pathname: "/",
              state: {
                from: props.location,
              },
            }}
          />
        )
      }
    />
  );
}
