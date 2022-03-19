import "@testing-library/jest-dom"
import {promiseOf, renderNavigationElement} from "../../Util"
import {screen, fireEvent, waitFor} from "@testing-library/react"
import {User} from "../../../../main/services/Data";
import {service} from "../../../../main/services/Dependencies";
import {Registration} from "../../../../main/components/auth/Registration";
import {Route} from "react-router";


describe("Registration test", () => {
    let setUser: jest.Mock
    let register: jest.SpyInstance
    beforeEach(() => {
        setUser = jest.fn()
        register = jest.spyOn(service, "register");
        const element = (
            <>
                <Route path={"/"} element={<></>}/>
                <Route path={"/registration"} element={
                    <Registration setUser={setUser}/>}
                />
            </>)
        renderNavigationElement(element, "/registration")
    })

    test("should have correct header", () => {
        expect(screen.getByText("Регистрация")).toBeInTheDocument()
    })

    test("should have correct submit button text", () => {
        expect(screen.getByText("Зарегистрироваться")).toBeInTheDocument()
    })

    test("should have correct failure text", async () => {
        register.mockReturnValueOnce(promiseOf(null))

        fireEvent.submit(screen.getByText("Зарегистрироваться"))
        expect(await screen.findByText("Пользователь с таким логином уже существует")).toBeInTheDocument()
    })

    test("should call service and setUser on submit", async () => {
        const user = new User("login", "password")
        register.mockReturnValueOnce(promiseOf(user))

        fireEvent.submit(screen.getByText("Зарегистрироваться"))

        await waitFor(() => {
            expect(register).toHaveBeenCalled()
            expect(setUser).toHaveBeenCalledWith(user)
        })
    })
})