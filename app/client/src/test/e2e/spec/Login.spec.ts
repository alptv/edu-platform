import { url } from "../Util"

describe("Login spec", () => {
    beforeEach(() => {
        cy.visit(url("login"))
    })

    afterEach(() => {
        cy.clearCookies()
    })

    it("should enter", () => {
        cy.contains("Логин").parent().find(".Input").type("user")
        cy.contains("Пароль").parent().find(".Input").type("password")
        cy.contains("Войти").click()
        cy.url().should('eq', url())
        cy.contains("Образовательная платформа")
    })

    it("should show popup on incorrect login", () => {
        cy.contains("Логин").parent().find(".Input").type("user")
        cy.contains("Пароль").parent().find(".Input").type("incorrect_password")
        cy.contains("Войти").click()

        cy.contains("Неверный логин или пароль")
        cy.get(".DeleteButton").click();
        cy.contains("Логин").parent().find(".Input").should('have.value', "")
        cy.contains("Пароль").parent().find(".Input").should('have.value', "")
        cy.url().should('eq', url("/login"))
    })

    it("should redirect on login page if not logged in", () => {
        cy.visit(url("courses"))

        cy.url().should('eq', url("/login"))
    })

})


export {}