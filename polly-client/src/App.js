import './App.css';
import Dashboard from './components/Dashboard/Dashboard';
import Preferences from './components/Preferences/Preferences';
import Header from './components/Dashboard/Header';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import {
  Button,
  withStyles,
} from "@material-ui/core";
import AddBoxIcon from '@material-ui/icons/AddBox';


function App() {
  const ColorButton = withStyles(() => ({
    root: {
      fontFamily: "Ubuntu",
      fontSize: "22px",
      textTransform: "none",
      fontWeight: "600",
      color: "white",
      letterSpacing: "-0.36px",
      textAlign: "center",
      marginTop: "2em",
      backgroundColor: "#2496cf",
      '&:hover': {
        color: "white",
        backgroundColor: "#47A9D8",
      },
    },
  }))(Button);

  return (
    <main>
      <Header />
      <div className="wrapper">
        <div style={{ color: "black", fontWeight: "600" }}>
          <div class="slogan">
            <div className="mainWords">
              Create instant, real-time <br />
              <span style={{ fontWeight: "900", color: "#2496cf" }}>polls</span> for
          <div class="changing-keywords" id="change">
                <div className="changable">
                  <u class="hidden"><br />free</u><br />
                  <u class="hidden"><br />fun</u><br />
                  <u class="hidden"><br />fans</u><br />
                  <u class="hidden"><br />decision</u>&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              </div>
              </div>
            </div>
            <div className="createPoll">
              <ColorButton variant="outlined" size="large" startIcon={<AddBoxIcon />} color="primary" type="button">Create a poll</ColorButton >
            </div>
            <div className="altSlogan">Capture powerful feedback instantly during virtual meetings, classes, events, and more.</div>
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
