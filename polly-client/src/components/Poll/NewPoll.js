import './NewPoll.css';
import React, { Component } from 'react';
import {
    TextField,
    Button,
    withStyles,
  } from "@material-ui/core";
  import AddBoxIcon from '@material-ui/icons/AddBox';

class NewPoll extends Component {

    render() {

        return (
            <div className="new-poll-container">
                <div className="head">
                    <h2>Create a Poll</h2>
                    <TextField
                        id="filled-multiline-static"
                        label="Poll Question"
                        multiline
                        rows={5}
                        placeholder="What is your favourite X?"
                        variant="outlined"
                        style={{ minWidth: "30em", backgroundColor: "white" }}
                    />
                    <div style={{ marginTop: "1em"}}>
                        <TextField
                            id="filled-multiline-static"
                            label="Poll Option"
                            placeholder="What is your favourite X?"
                            variant="outlined"
                            style={{ minWidth: "30em", backgroundColor: "white" }}
                        />
                    </div>
                    <div style={{ marginTop: "1em"}}>
                        <TextField
                            id="filled-multiline-static"
                            label="Poll Option"
                            placeholder="What is your favourite X?"
                            variant="outlined"
                            style={{ minWidth: "30em", backgroundColor: "white" }}
                        />
                    </div>
                    <div style={{ marginTop: "1em"}}>
                        <TextField
                            id="filled-multiline-static"
                            label="Poll Option"
                            placeholder="What is your favourite X?"
                            variant="outlined"
                            style={{ minWidth: "30em", backgroundColor: "white" }}
                        />
                    </div>
                    <div style={{ marginTop: "1em"}}>

                    <Button variant="outlined" size="large" startIcon={<AddBoxIcon />} color="primary" type="button">Add another poll option</Button >
                    </div>
                </div>
            </div>
        )
    }
}

export default NewPoll;