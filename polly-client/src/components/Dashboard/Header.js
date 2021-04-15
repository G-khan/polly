import {
    AppBar,
    Toolbar,
    Typography,
    makeStyles,
    Button,
    IconButton,
    Drawer,
    Link,
    MenuItem,
} from "@material-ui/core";
import MenuIcon from "@material-ui/icons/Menu";
import React, { useState, useEffect } from "react";
import { Link as RouterLink } from "react-router-dom";
import PollIcon from '@material-ui/icons/Poll';

const headersData = [
    {
        label: "Login",
        href: "/login",
        logged: false
    },
    {
        label: "Signup",
        href: "/signup",
        logged: false
    },/*
    {
        label: "My Account",
        href: "/account",
        logged: true

    },
    {
        label: "Polls",
        href: "/polls",
        logged: true

    },
    {
        label: "Log Out",
        href: "/logout",
        logged: true

    },*/
];


const useStyles = makeStyles(() => ({
    header: {
        backgroundColor: "#FFF",
        paddingRight: "79px",
        paddingLeft: "118px",
        "@media (max-width: 900px)": {
            paddingLeft: 0,
        },
        boxShadow:'none'
    
    },
    logo: {
        fontFamily: "Lemonada",
        fontWeight: 900,
        fontSize:32,
        color: "#2496cf",
        textAlign: "left",
    },
    menuButton: {
        fontFamily: "Open Sans, sans-serif",
        fontWeight: 700,
        size: "18px",
        marginLeft: "38px",
    },
    toolbar: {
        display: "flex",
        justifyContent: "space-between",
        border:0
        ,shadowRadius: 0,
        shadowOffset: {
            height: 0,
        },
    },
    drawerContainer: {
        padding: "20px 30px",
    },
}));


export default function Header() {
    const { header, logo, menuButton, toolbar, drawerContainer } = useStyles();

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
            <Toolbar  no-border  className={toolbar}>
                {pollyLogo}
                <div>{getMenuButtons()}</div>
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
                    <div className={drawerContainer}>{getDrawerChoices()}</div>
                </Drawer>

                <div>{pollyLogo}</div>
            </Toolbar>
        );
    };

    const getDrawerChoices = () => {
        return headersData.map(({ label, href, logged }) => {
            
                return logged === false ? (
                    <Link
                        {...{
                            component: RouterLink,
                            to: href,
                            color: "inherit",
                            style: { textDecoration: "none" },
                            key: label,
                        }}
                    >
                        <MenuItem>{label}</MenuItem>
                    </Link>
                ) : null
            });
    };

    const pollyLogo = (
        <Typography variant="h6" component="h1" className={logo}>
             <PollIcon /> Polly.
        </Typography>
    );

    const getMenuButtons = () => {
        return headersData.map(({ label, href }) => {
            return (
                <Button
                    {...{
                        key: label,
                        color: "black",
                        to: href,
                        className: menuButton,
                    }}
                >
                    {label}
                </Button>
            );
        });
    };

    return (
        <header>
            <AppBar  position="static" className={header}>
                {mobileView ? displayMobile() : displayDesktop()}
            </AppBar>
        </header>
    );
}