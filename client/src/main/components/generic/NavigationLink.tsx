import {NavLink} from "react-router-dom"
import {ReactElement, MouseEventHandler} from "react";
import {To} from "react-router"

export const NavigationLink = (props: {
    to: To
    children: ReadonlyArray<ReactElement | string> | ReactElement | string
    onClick?: MouseEventHandler<HTMLAnchorElement>
}) => (
    <NavLink onClick={props.onClick} className="NavigationLink" to={props.to}>{props.children}</NavLink>
)