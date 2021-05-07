import './Login.css';
import Header from '../Dashboard/Header';
import React, { Component } from 'react';
import ReactDOM from 'react-dom';

import {
    Button,
    TextField,
    Card,
    CardContent,
    CardActions,
    CardHeader} from "@material-ui/core";

class Login extends Component{
    render() {
        return LoginFun
    }

}    
export default function LoginFun() {
    return (
        <div className="login-wrapper">
            
            <form>
                <Card>
                    <CardHeader title="Login App" />

                    <CardContent>
                        <label>
                            <TextField
                                fullWidth
                                id="username"
                                type="email"
                                label="Username"
                                placeholder="Username"
                                margin="normal" />
                        </label>
                        <label>
                            <TextField fullWidth
                                id="password"
                                type="password"
                                label="Password"
                                placeholder="Password"
                                margin="normal" />
                        </label>
                    </CardContent>
                    <CardActions>
                        <Button fullWidth variant="contained" size="large" color="primary" className="login-button" type="submit">Login</Button >
                    </CardActions>
                </Card>
            </form>
        </div>
    )
}

ReactDOM.render(<Login />, document.getElementById('root'));

