import React from "react"
import {NavLink} from "react-router-dom"
import type {To} from "react-router";
import "./NavigationBar.css"

class NavigationBar extends React.Component {
    render() {
        return (
            <div className="NavigationBar">
                <NavigationItem to="/" text="Home"/>
                <NavigationItem to="/courses" text="Courses"/>
                <NavigationItem to="/profile" text="Profile"/>
            </div>
        );
    }
}

class NavigationItem extends React.Component<{ to: To, text: String }> {
    render() {
        return <NavLink className="NavigationItem" to={this.props.to}>{this.props.text}</NavLink>
    }
}

export default NavigationBar;
