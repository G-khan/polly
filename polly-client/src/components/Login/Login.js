import React from 'react';
import './Login.css';
import {
    Button,
    TextField,
    Card,
    CardContent,
    CardActions,
    CardHeader,
} from "@material-ui/core";

export default function Login() {
    return (

        <div className="login-wrapper">
            <form>
                <Card >
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
                        <div className="login-button">
                            <Button variant="contained" color="primary" type="submit">Login</Button >
                        </div>
                    </CardActions>
                </Card>
            </form>
        </div>
    )
}