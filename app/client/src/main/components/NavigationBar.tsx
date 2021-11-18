import React from "react"
import "./NavigationBar.css"
import {NavigationLink} from "./generic/NavigationLink"

export const NavigationBar = (props: {
    logOut: () => void,
    isLoggedIn: boolean
}) => (
    <div className="NavigationBar">
        <div className="Left">
            {props.isLoggedIn && <NavigationLink to="/">Главная</NavigationLink>}
            {props.isLoggedIn && <NavigationLink to="/courses">Курсы</NavigationLink>}
            {props.isLoggedIn && <NavigationLink to="/profile">Профиль</NavigationLink>}
        </div>
        <div className="Right">
            {props.isLoggedIn && <NavigationLink to="/login" onClick={_ => props.logOut()}>Выход</NavigationLink>}
            {!props.isLoggedIn && <NavigationLink to="/register">Регистрация</NavigationLink>}
            {!props.isLoggedIn && <NavigationLink to="/login">Вход</NavigationLink>}
        </div>
    </div>
)