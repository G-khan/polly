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
import PollIcon from '@material-ui/icons/Poll';
import React, { useState, useEffect } from "react";

const useStyles = makeStyles(() => ({
    header: {
        backgroundColor: "#FFF",
        paddingRight: "79px",
        paddingLeft: "118px",
        "@media (max-width: 900px)": {
            paddingLeft: 0,
        },
        boxShadow: 'none'

    },
    logo: {
        fontFamily: "Lemonada",
        fontWeight: 900,
        fontSize: 32,
        color: "#2496cf",
        textAlign: "left",
    },
    menuButton: {
        fontFamily: "Open Sans, sans-serif",
        fontWeight: 700,
        size: "18px",
        margin: "5"
    },
    toolbar: {
        display: "flex",
        justifyContent: "space-between",
        border: 0,
        shadowRadius: 0,
        shadowOffset: {
            height: 0,
        },
        letterSpacing: "1em",
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
            <Toolbar no-border className={toolbar}>
                {pollyLogo}
                <div>

                    <Button color="primary" style={{ letterSpacing: "0.1em", fontWeight: 600, textTransform: "none", borderRadius: "4px", padding: "16px 20px", boxShadow: 'none' }} >Login</Button>&nbsp;
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
            <Toolbar>
                <IconButton
                    {...{
                        edge: "start",
                        color: "inherit",
                        "aria-label": "menu",
                        "aria-haspopup": "true",
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


                        <MenuItem>Login</MenuItem>

                        <MenuItem>Register</MenuItem>
                    </div>
                </Drawer>

                <div>{pollyLogo}</div>
            </Toolbar>
        );
    };


    const pollyLogo = (
        <Typography variant="h6" component="h1" className={logo}>
            <PollIcon /> Polly.
        </Typography>
    );



    return (
        <header>
            <AppBar position="static" className={header}>
                {mobileView ? displayMobile() : displayDesktop()}
            </AppBar>
        </header>
    );
}