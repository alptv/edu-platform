import {loginAsDefaultUser, url} from "../Util";

describe("NavigationBar spec", () => {
    afterEach(() => {
        cy.clearCookies()
    })

    it("user should be able to use private tabs", () => {
        loginAsDefaultUser()
        cy.contains("Главная").click()
        cy.contains("Образовательная платформа")
        cy.url().should('eq', url())

        cy.contains("Курсы").click()
        cy.url().should('eq', url("/courses"))

        cy.contains("Профиль").click()
        cy.contains("Ваш профиль")
        cy.url().should('eq', url("/profile"))

        cy.contains("Выход").click()
        cy.url().should('eq', url("/login"))

    })

    it("user should be able to use public tabs", () => {
        cy.contains("Вход").click()
        cy.url().should('eq', url("/login"))

        cy.contains("Регистрация").click()
        cy.url().should('eq', url("/register"))
    })

})
