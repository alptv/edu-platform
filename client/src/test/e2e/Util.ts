import {Service, ServiceImpl} from "../../main/services/Service";
import {RestClient} from "../../main/util/RestClient";

const BASE_URL = "http://localhost:3000"
const e2eService: Service = new ServiceImpl(new RestClient("http://localhost:8080"))

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