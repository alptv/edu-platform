import "@testing-library/jest-dom"
import {promiseOf, renderNavigationElement} from "../../Util"
import {screen, fireEvent, waitFor} from "@testing-library/react"
import {UserCredentials} from "../../../../main/components/auth/UserCredentials";
import {User} from "../../../../main/services/Data";
import {Route} from "react-router";


describe("UserCredentials test", () => {
    let setUser: jest.Mock
    let submit: jest.Mock
    let isStartUrl: () => boolean
    beforeEach(() => {
        setUser = jest.fn()
        submit = jest.fn()
        const element = (
            <>
                <Route path={"/"} element={<></>}/>
                <Route path={"/userCredentials"} element={
                    <UserCredentials headerText="header"
                                     submitText="submit"
                                     failureText="failure"
                                     setUser={setUser}
                                     submit={submit}/>}
                />
            </>);
        [, isStartUrl] = renderNavigationElement(element, "/userCredentials")
    })

    test("should has header text", () => {
        expect(screen.getByText("header")).toBeInTheDocument()
    })

    test("should have login field", () => {
        const login = document.querySelector(".Input[type=text]")
        expect(login).toBeInTheDocument()
    })

    test("should have password field", () => {
        const password = document.querySelector(".Input[type=password]")
        expect(password).toBeInTheDocument()
    })

    test("should use onSubmit on click submit", async () => {
        submit.mockReturnValueOnce(promiseOf(null))
        fireEvent.submit(screen.getByText("submit"))
        await waitFor(() => expect(submit).toHaveBeenCalled())
    })

    test("should show popup after failed submit", async () => {
        submit.mockReturnValueOnce(promiseOf(null))
        fireEvent.submit(screen.getByText("submit"))

        expect(await screen.findByText("failure")).toBeInTheDocument()
    })
    test("should be able to close popup on failed submit and fields should be cleared", async () => {
        submit.mockReturnValueOnce(promiseOf(null))
        fireEvent.submit(screen.getByText("submit"))

        await waitFor(() => {
            const deleteButton = document.querySelector<HTMLElement>(".DeleteButton")
            expect(deleteButton).toBeInTheDocument()
            fireEvent.click(deleteButton!!)
            expect(screen.queryByText("failure")).not.toBeInTheDocument()
        })
        await waitFor(() => {
            expect(document.querySelector<HTMLInputElement>(".Input[type=text]")!!.value).toBe("")
            expect(document.querySelector<HTMLInputElement>(".Input[type=password]")!!.value).toBe("")
        })
    })


    test("should redirect after success submit", async () => {
        const user = new User("login", "password")
        submit.mockReturnValueOnce(promiseOf(user))
        const login = document.querySelector(".Input[type=text]")!!
        const password = document.querySelector(".Input[type=password]")!!

        fireEvent.change(login, {target: {value: "login"}})
        fireEvent.change(password, {target: {value: "password"}})
        fireEvent.submit(screen.getByText("submit"))

        await waitFor(() => {
            expect(submit).toHaveBeenCalledWith("login", "password")
            expect(setUser).toHaveBeenCalledWith(user)
            expect(isStartUrl()).toBeFalsy()
        })
    })
})


