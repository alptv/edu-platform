import {ReactElement} from "react";
import {Navigate, To} from "react-router"


export const ProtectedElement = (props: {
    redirectTo: To
    children: ReactElement
    open: boolean
}) => {
    if (props.open) {
        return props.children
    } else {
        return <Navigate to={props.redirectTo}/>
    }
}