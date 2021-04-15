import React, { useState } from 'react';
import './App.css';
import Dashboard from './components/Dashboard/Dashboard';
import Preferences from './components/Preferences/Preferences';
import Header from './components/Dashboard/Header';
import { BrowserRouter, Route, Switch } from 'react-router-dom';

function App() {


  return (
    <main>
      <Header />
      <div className="wrapper">
      <div style={{ height: "100vh", background: "#f8f8f8", color: "black" }}>
          <div class="slogan">
          Create instant, real-time polls for <div style={{ textDecoration: 'underline'}} class="changing-keywords" id="change">
              <strong>
                <u class="hidden">free</u><br />
                <u class="hidden">fun</u><br />
                <u class="hidden">fans</u><br />
                <u class="hidden">decision</u>
              </strong>
            </div>
          </div>
        </div>
      </div>
      <BrowserRouter>
        <Switch>
          <Route path="/dashboard">
            <Dashboard />
          </Route>
          <Route path="/preferences">
            <Preferences />
          </Route>
        </Switch>
      </BrowserRouter>
    </main>
  );
}

export default App;
