import React from 'react';
import './App.css';

import {
  HashRouter as Router,
  Switch,
  Route,
} from "react-router-dom";

import {GamesListScreen} from './GamesListScreen';
import {AdminScreen} from './AdminScreen';
import {ProtectedRoute} from './auth/ProtectedRoute';
import {UserPreferencesScreen} from './user/UserPreferencesScreen';
import {LoginScreen} from './auth/LoginScreen';

function App() {
  return (
    <div className="App">
      <Router>
        <Switch>
          <Route path="/" exact>
            <GamesListScreen/>
          </Route>

          <Route path="/login" exact>
            <LoginScreen/>
          </Route>

          <Route path="/admin">
            <AdminScreen/>
          </Route>

          <ProtectedRoute path="/user" component={UserPreferencesScreen}/>
        </Switch>
      </Router>
    </div>
  );
}

export default App;
