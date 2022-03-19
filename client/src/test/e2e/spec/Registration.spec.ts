import {url} from "../Util";

describe("Registration spec", () => {
    beforeEach(() => {
        cy.visit(url("/register"))
    })

    afterEach(() => {
        cy.clearCookies()
    })

    it("should show popup if user already exists enter", () => {
        cy.contains("Логин").parent().find(".Input").type("user")
        cy.contains("Пароль").parent().find(".Input").type("password")
        cy.contains("Зарегистрироваться").click()

        cy.contains("Пользователь с таким логином уже существует")
        cy.get(".DeleteButton").click();
        cy.contains("Логин").parent().find(".Input").should('have.value', "")
        cy.contains("Пароль").parent().find(".Input").should('have.value', "")
        cy.url().should('eq', url("/register"))

    })

    it("should show popup on incorrect login", () => {
        cy.contains("Логин").parent().find(".Input").type("new_user")
        cy.contains("Пароль").parent().find(".Input").type("password")
        cy.contains("Зарегистрироваться").click()

        cy.url().should('eq', url())
        cy.contains("Образовательная платформа")
    })

})
