import React from 'react';
import './App.css';

import {
  HashRouter as Router,
  Switch,
  Route,
} from "react-router-dom";

import {GamesListScreen} from './GamesListScreen';
import {AdminScreen} from './AdminScreen';

function App() {
  return (
    <div className="App">
      <Router>
        <Switch>
          <Route path="/" exact>
            <GamesListScreen/>
          </Route>
          <Route path="/admin">
            <AdminScreen/>
          </Route>
        </Switch>
      </Router>
    </div>
  );
}

export default App;
