import {
    AppBar,
    Toolbar,
    Typography,
    makeStyles,
    Button,
    IconButton,
    Drawer,
    MenuItem,
} from "@material-ui/core";
import MenuIcon from "@material-ui/icons/Menu";
import React, { useState, useEffect, Component } from "react";
import pollIcon from '../../poll.png';
import { Link } from 'react-router-dom';

const useStyles = makeStyles(() => ({
    header: {
        backgroundColor: "#FFF",
        boxShadow: 'none',
    },
    logo: {
        fontFamily: "Black Han Sans",
        fontSize: 32,
        color: "#2496cf",
        textAlign: "left",
    },
    menuButton: {
        fontFamily: "Open Sans, sans-serif",
        fontWeight: 700,
    },
    toolbar: {
        display: "flex",
        border: 0,
        shadowRadius: 0,
        shadowOffset: {
            height: 0,
        },
        backgroundColor: "#fff",
        color: "#000",
    },
    drawerContainer: {
        padding: "20px 30px",
    },
}));



export default function Header() {
    const { header, logo, toolbar, drawerContainer } = useStyles();

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
            <Toolbar no-border className={toolbar} style={{ justifyContent: "space-between" }}>
                <Link to="/" style={{ textDecoration: 'inherit' }}>{pollyLogo}</Link>
                <div>

                    <Link to="/login" style={{ textDecoration: 'inherit' }}>
                        <Button color="primary" style={{ letterSpacing: "0.1em", fontWeight: 600, textTransform: "none", borderRadius: "4px", padding: "16px 20px", boxShadow: 'none' }} >
                            Login
                        </Button>
                    </Link>&nbsp;

                    <Button color="primary" style={{ letterSpacing: "0.1em", fontWeight: 600, backgroundColor: "#2496cf", textTransform: "none", borderRadius: "4px", padding: "16px 20px", boxShadow: 'none' }} variant="contained">Sign Up</Button>
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
            <Toolbar no-border className={toolbar} style={{ justifyContent: "center" }}>
                <IconButton
                    {...{
                        edge: "start",
                        onClick: handleDrawerOpen,
                    }}
                >
                    <MenuIcon />
                </IconButton>

                <Drawer
                    {...{
                        anchor: "left",
                        open: drawerOpen,
                        onClose: handleDrawerClose,
                    }}
                >
                    <div className={drawerContainer}>
                        <Link to="/login">
                            <MenuItem>Login</MenuItem>
                        </Link>
                    </div>
                </Drawer>

                <div>
                    <Link to="/">
                        {pollyLogo}
                    </Link>
                </div>
            </Toolbar>
        );
    };


    const pollyLogo = (
        <Typography variant="h6" component="h1" className={logo}>
            <img style={{ height: "50px", float: "left" }} src={pollIcon} alt="poll" /> Polly.
        </Typography>
    );

    return (
        <header>
            <AppBar position="static" >
                {mobileView ? displayMobile() : displayDesktop()}
            </AppBar>
        </header>
    );
}