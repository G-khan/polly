import './App.css';
import Dashboard from './components/Dashboard/Dashboard';
import Preferences from './components/Preferences/Preferences';
import Header from './components/Dashboard/Header';
import NewPoll from './components/Poll/NewPoll';
import React from 'react';
import {  HashRouter as Router, Route, Redirect, Switch } from 'react-router-dom';

import Login from './components/Login/Login';


function App() {
  return (
    <main>
      <div className="wrapper">
        <Router>
          <Header />
          <Switch>
            <Route exact path="/">
              <Dashboard />
            </Route>
            <Route path="/preferences">
              <Preferences />
            </Route>
            <Route component={Login} path="/login">
              <Login />
            </Route>
            <Route path="/new">
              <NewPoll />
            </Route>
            <Redirect to="/" />
          </Switch>

        </Router>
      </div>

    </main>
  );
}


export default App;
