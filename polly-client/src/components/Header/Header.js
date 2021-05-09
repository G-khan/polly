import {
    AppBar,
    Toolbar,
    Typography,
    Button,
    IconButton,
    Drawer,
    MenuItem,
} from "@material-ui/core";
import MenuIcon from "@material-ui/icons/Menu";
import React, { useState, useEffect, Component } from "react";
import pollIcon from '../../poll.png';
import { Link } from 'react-router-dom';
import './Header.css';


export default function Header() {

    const [state, setState] = useState({
        mobileView: false,
        drawerOpen: false,
    });

    const { mobileView, drawerOpen } = state;

    useEffect(() => {
        const setResponsiveness = () => {
            return window.innerWidth < 900
                ? setState((prevState) => ({ ...prevState, mobileView: true }))
                : setState((prevState) => ({ ...prevState, mobileView: false }));
        };

        setResponsiveness();

        window.addEventListener("resize", () => setResponsiveness());
    }, []);

    const displayDesktop = () => {
        return (
            <Toolbar no-border className="toolbar" style={{ WebkitBoxShadow: "none", justifyContent: "space-between" }}>
                <Link className="no-decoration" to="/">{pollyLogo}</Link>
                <div>

                    <Link to="/login" className="no-decoration">
                        <Button color="primary" className="menu-button" >
                            Login
                        </Button>
                    </Link>&nbsp;

                    <Button color="primary" className="menu-button" variant="contained">Sign Up</Button>
                </div>
            </Toolbar>
        );
    };


    const displayMobile = () => {
        const handleDrawerOpen = () =>
            setState((prevState) => ({ ...prevState, drawerOpen: true }));
        const handleDrawerClose = () =>
            setState((prevState) => ({ ...prevState, drawerOpen: false }));

        return (
            <Toolbar no-border className="toolbar" style={{ justifyContent: "center" }}>
                <IconButton
                    {...{
                        edge: "start",
                        onClick: handleDrawerOpen,
                    }}>
                    <MenuIcon />
                </IconButton>

                <Drawer
                    {...{
                        anchor: "left",
                        open: drawerOpen,
                        onClose: handleDrawerClose,
                    }}
                >
                    <div className="drawerContainer">
                        <Link to="/login" className="no-decoration">
                            <MenuItem>Login</MenuItem>
                        </Link>
                    </div>
                </Drawer>

                <div>
                    <Link to="/" className="no-decoration">
                        {pollyLogo}
                    </Link>
                </div>
            </Toolbar>
        );
    };


    const pollyLogo = (
        <Typography variant="h6" component="h1">
            <img className="logoImg" src={pollIcon} alt="poll" /> <span className="pollyLogo">Polly.</span>
        </Typography>
    );

    return (
        <header>
            <AppBar position="static" style={{ WebkitBoxShadow: "none", backgroundColor: "white" }}>
                {mobileView ? displayMobile() : displayDesktop()}
            </AppBar>
        </header>
    );
}