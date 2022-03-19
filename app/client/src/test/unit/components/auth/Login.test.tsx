import "@testing-library/jest-dom"
import {promiseOf, renderNavigationElement} from "../../Util"
import {screen, fireEvent, waitFor} from "@testing-library/react"
import {User} from "../../../../main/services/Data";
import {service} from "../../../../main/services/Dependencies";
import {Login} from "../../../../main/components/auth/Login";
import {Route} from "react-router";


describe("Login test", () => {
    let setUser: jest.Mock
    let enter: jest.SpyInstance
    beforeEach(() => {
        setUser = jest.fn()
        enter = jest.spyOn(service, "enter");
        const element = (
            <>
                <Route path={"/"} element={<></>}/>
                <Route path={"/login"} element={
                    <Login setUser={setUser}/>}
                />
            </>)
        renderNavigationElement(element, "/login")
    })

    test("should have correct header", () => {
        expect(screen.getByText("Вход")).toBeInTheDocument()
    })

    test("should have correct submit button text", () => {
        expect(screen.getByText("Войти")).toBeInTheDocument()
    })

    test("should have correct failure text", async () => {
        enter.mockReturnValueOnce(promiseOf(null))

        fireEvent.submit(screen.getByText("Войти"))
        expect(await screen.findByText("Неверный логин или пароль")).toBeInTheDocument()
    })

    test("should call service and setUser on submit", async () => {
        const user = new User("login", "password")
        enter.mockReturnValueOnce(promiseOf(user))

        fireEvent.submit(screen.getByText("Войти"))

        await waitFor(() => {
            expect(enter).toHaveBeenCalled()
            expect(setUser).toHaveBeenCalledWith(user)
        })
    })
})