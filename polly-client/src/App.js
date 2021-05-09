import './App.css';
import Dashboard from './components/Dashboard/Dashboard';
import Preferences from './components/Preferences/Preferences';
import Header from './components/Header/Header';
import NewPoll from './components/Poll/NewPoll';
import React from 'react';
import { HashRouter as Router, Route, Redirect, Switch } from 'react-router-dom';
import { createMuiTheme, ThemeProvider } from '@material-ui/core/styles';

import Login from './components/Login/Login';

const pollyTheme = createMuiTheme({
  palette: {
    primary: {
      main: "#2496cf" ,
    },
  },
});

function App() {
  return (
    <main>
      <div className="wrapper">
        <ThemeProvider theme={pollyTheme}>
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
        </ThemeProvider>
      </div>

    </main>
  );
}


export default App;
