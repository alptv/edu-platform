const BASE_URL = Cypress.env("host")

export function url(resource?: string) {
    const pathSeparator = resource?.startsWith("/") ? "" : "/"
    return BASE_URL + pathSeparator + (resource ?? "")
}

export function loginAsDefaultUser() {
    cy.visit(url("login"))
    cy.contains("Логин").parent().find(".Input").type("user")
    cy.contains("Пароль").parent().find(".Input").type("password")
    cy.contains("Войти").click()
}