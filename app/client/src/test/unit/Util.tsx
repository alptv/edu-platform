import {render} from "@testing-library/react"
import {ReactElement} from "react"
import {BrowserRouter, Route, Routes} from "react-router-dom"

export type Render = () => HTMLElement

export function createElementRender(reactElement: ReactElement): Render {
    return () => renderElement(reactElement)
}

export function renderElement(reactElement: ReactElement): HTMLElement {
    const {container} = render(reactElement)
    return container.firstElementChild as HTMLElement
}

export type RenderNavigationElement = () => [HTMLElement, () => boolean]

export function createNavigationElementRender(
    reactElement: ReactElement,
    maybeStartUrl?: string
): RenderNavigationElement {
    return () => renderNavigationElement(reactElement, maybeStartUrl)
}


export function renderNavigationElement(
    reactElement: ReactElement,
    maybeStartUrl?: string
): [HTMLElement, () => boolean] {

    const startUrl = maybeStartUrl ?? "/"
    const fullStartUrl = "http://localhost" + startUrl
    window.history.pushState({}, "", fullStartUrl);

    const component = renderElement(
        <BrowserRouter>
            <Routes>
                {reactElement}
            </Routes>
        </BrowserRouter>
    )
    const isStartUrl = () => window.location.href === fullStartUrl
    return [component, isStartUrl]
}

export function isOnUrl(url: String) {
    return window.location.href === "http://localhost" + url
}

export const Element = (props: { text?: string }) => <div className="Element">{props.text}</div>


export function promiseOf<T>(value: T) {
    return new Promise<T>((resolve, _) => resolve(value))
}
