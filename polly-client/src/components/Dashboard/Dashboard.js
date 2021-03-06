import React from 'react';
import {
  Button,
  withStyles,
} from "@material-ui/core";
import AddBoxIcon from '@material-ui/icons/AddBox';
import TextLoop from "react-text-loop";
import { Link } from 'react-router-dom';

export default function Dashboard() {
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
    <div style={{ color: "black", fontWeight: "600" }}>
      <div className="slogan">
        <div className="mainWords">
          Create instant, real-time <br />
          <span style={{ fontWeight: "900", color: "#2496cf" }}>polls</span> for &nbsp;
          <mark>
            <TextLoop>
              <span>free</span>
              <span>fun</span>
              <span>fans</span>
            </TextLoop>
          </mark>
        </div>
        <div className="createPoll">
          <Link to="/new">
            <ColorButton variant="outlined" size="large" startIcon={<AddBoxIcon />} color="primary" type="button">Create a poll</ColorButton >
          </Link>
        </div>
        <div className="altSlogan">Capture powerful feedback instantly during virtual meetings, classes, events, and more.</div>
      </div>

    </div>);
}