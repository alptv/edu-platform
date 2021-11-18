import React, {ReactNode} from "react";

export const List = <P extends any>(props: {
    type: "o" | "u"
    items: Array<P>,
    element: (props: P) => ReactNode
}) => {
    const componentsItems = props.items.map(item => <li className="ListItem">{props.element(item)}</li>)
    if (props.type == "o") {
        return <ol className="List">{componentsItems}</ol>
    } else {
        return <ul className="List">{componentsItems}</ul>
    }
}





