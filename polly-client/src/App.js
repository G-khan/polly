import './App.css';
import Dashboard from './components/Dashboard/Dashboard';
import Preferences from './components/Preferences/Preferences';
import Header from './components/Dashboard/Header';
import NewPoll from './components/Poll/NewPoll';
import { withRouter, Route, Switch } from 'react-router-dom';
import React, { Component } from 'react';

import Login from './components/Login/Login';


class App extends Component {

  render() {
    return (
      <main>
        <Header />
        <div className="wrapper">
            <Switch>
              <Route exact path="/">
                <Dashboard />
              </Route>
              <Route path="/preferences">
                <Preferences />
              </Route>
              <Route  component={Login} path="/login">
                <Login />
              </Route>
              <Route path="/new">
                <NewPoll />
              </Route>
            </Switch>
        </div>

      </main>
    );
  }
}

export default withRouter(App);
