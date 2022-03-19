import "@testing-library/jest-dom"
import {screen, fireEvent} from "@testing-library/react"
import {Route} from "react-router";
import {isOnUrl, renderNavigationElement} from "../Util";
import {NavigationBar} from "../../../main/components/NavigationBar";


describe("NavigationBar test", () => {
    let render = (isLoggedIn: boolean, logOut: () => void) => {
        renderNavigationElement(
            <>
                <Route path="/" element={<></>}/>
                <Route path="/courses" element={<></>}/>
                <Route path="/profile" element={<></>}/>
                <Route path="/login" element={<></>}/>
                <Route path="/register" element={<></>}/>
                <Route path="/login" element={<></>}/>
                <Route path="/navigationBar" element={
                    <NavigationBar logOut={logOut} isLoggedIn={isLoggedIn}/>}
                />
            </>, "/navigationBar")
    }
    test("should show public options if user not logged in", () => {
        render(false, jest.fn())
        expect(screen.queryByText("Главная")).not.toBeInTheDocument()
        expect(screen.queryByText("Курсы")).not.toBeInTheDocument()
        expect(screen.queryByText("Профиль")).not.toBeInTheDocument()
        expect(screen.queryByText("Выход")).not.toBeInTheDocument()
        expect(screen.getByText("Регистрация")).toBeInTheDocument()
        expect(screen.getByText("Вход")).toBeInTheDocument()
    })

    test("should show private options if user logged in", () => {
        render(true, jest.fn())
        expect(screen.getByText("Главная")).toBeInTheDocument()
        expect(screen.getByText("Курсы")).toBeInTheDocument()
        expect(screen.getByText("Профиль")).toBeInTheDocument()
        expect(screen.getByText("Выход")).toBeInTheDocument()
        expect(screen.queryByText("Регистрация")).not.toBeInTheDocument()
        expect(screen.queryByText("Вход")).not.toBeInTheDocument()
    })

    test("should redirect to main", () => {
        render(true, jest.fn())
        fireEvent.click(screen.getByText("Главная"))
        expect(isOnUrl("/")).toBeTruthy()
    })

    test("should redirect to courses", () => {
        render(true, jest.fn())
        fireEvent.click(screen.getByText("Курсы"))
        expect(isOnUrl("/courses")).toBeTruthy()
    })

    test("should redirect to profile", () => {
        render(true, jest.fn())
        fireEvent.click(screen.getByText("Профиль"))
        expect(isOnUrl("/profile")).toBeTruthy()
    })

    test("should redirect to login from exit", () => {
        render(true, jest.fn())
        fireEvent.click(screen.getByText("Выход"))
        expect(isOnUrl("/login")).toBeTruthy()
    })

    test("should redirect to login", () => {
        render(false, jest.fn())
        fireEvent.click(screen.getByText("Вход"))
        expect(isOnUrl("/login")).toBeTruthy()
    })

    test("should redirect to registration", () => {
        render(false, jest.fn())
        fireEvent.click(screen.getByText("Регистрация"))
        expect(isOnUrl("/register")).toBeTruthy()
    })

    test("should use callback on logout", () => {
        const callback = jest.fn()
        render(true, callback)
        fireEvent.click(screen.getByText("Выход"))
        expect(callback).toHaveBeenCalled()
    })
})