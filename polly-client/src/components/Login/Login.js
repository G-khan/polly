import React from 'react';
import './Login.css';
import Header from '../Dashboard/Header';

import {
    Button,
    TextField,
    Card,
    CardContent,
    CardActions,
    CardHeader} from "@material-ui/core";

export default function Login() {
    return (
        <div className="login-wrapper">
            <Header />
            
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