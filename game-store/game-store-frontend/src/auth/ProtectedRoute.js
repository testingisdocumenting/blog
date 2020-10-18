import React from 'react'
import {Redirect, useLocation} from 'react-router-dom'
import {hasAuthToken} from './authTokens';

export function ProtectedRoute({component: Component}) {
  const location = useLocation()
  console.log('location', location)

  const isAuthenticated = hasAuthToken();

  return isAuthenticated ? (
    <Component/>
  ) : (
    <Redirect to={{pathname: '/login', state: { from: location }}}/>
  );
}

