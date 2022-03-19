import "@testing-library/jest-dom"
import {renderNavigationElement} from "../../Util"
import {NavigationLink} from "../../../../main/components/generic/NavigationLink"
import {fireEvent, screen} from "@testing-library/react"
import { Route } from "react-router"

describe("NavigationLink test", () => {
    let isStartUrl: () => boolean
    let onClick: jest.Mock
    beforeEach(() => {
        onClick = jest.fn();
        [, isStartUrl] = renderNavigationElement(
            <>
                <Route path={"/"} element={<></>}/>
                <Route path="/link" element={
                    <NavigationLink to="/" onClick={onClick}>link</NavigationLink>
                }/>
            </>, "/link"
        )
    })

    test("link should has text", () => {
        expect(screen.getByText("link")).toBeInTheDocument()
    })

    test("link should use callback on click", () => {
        fireEvent.click(screen.getByText("link"))
        expect(onClick).toHaveBeenCalled()
    })

    test("link should redirect on click", () => {
        fireEvent.click(screen.getByText("link"))
        expect(isStartUrl()).toBeFalsy()
    })
})
