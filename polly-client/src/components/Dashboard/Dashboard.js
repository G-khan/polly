import React from 'react';
import {
  Button,
} from "@material-ui/core";
import AddBoxIcon from '@material-ui/icons/AddBox';
import TextLoop from "react-text-loop";
import { Link } from 'react-router-dom';

export default function Dashboard() {

  return (
    <div>
      <div className="slogan">
        <div className="mainWords">
          Create instant, real-time <br />
          <span style={{ color: "#2496cf" }}>polls</span> for &nbsp;
          <mark>
            <TextLoop>
              <span>free</span>
              <span>fun</span>
              <span>fans</span>
            </TextLoop>
          </mark>
        </div>
        <div className="createPoll">
          <Link to="/new" className="no-decoration">
            <Button variant="contained" size="large" startIcon={<AddBoxIcon />} color="primary" type="button">Create a poll</Button >
          </Link>
        </div>
        <div className="altSlogan">Capture powerful feedback instantly during virtual meetings, classes, events, and more.</div>
      </div>

    </div>);
}