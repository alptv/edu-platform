import {loginAsDefaultUser, url} from "../Util"

describe("Logout spec", () => {
    afterEach(() => {
        cy.clearCookies()
    })

    it("should not be able to access pages after logout ", () => {
        loginAsDefaultUser()
        cy.contains("Выход").click()
        cy.visit(url("/courses"))
        cy.url().should('eq', url("/login"))
        cy.visit(url("/"))
        cy.url().should('eq', url("/login"))
        cy.contains("Вход").click()
        cy.contains("Регистрация").click()
    })

})


export {}