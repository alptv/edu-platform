import {loginAsDefaultUser, url} from "../Util"

describe("Login spec", () => {
    beforeEach(() => {
        loginAsDefaultUser()
    })

    afterEach(() => {
        cy.clearCookies()
    })

    it("should solve test and get popup", () => {
        cy.contains("Курсы").click()
        cy.contains("Английский язык для начинающих").click()
        cy.contains("Времена года").click()

        cy.contains("Как будет зима на английском?")
            .parent()
            .contains("winter")
            .parent().find("input").click()

        cy.contains("Как будет лето на английском?")
            .parent()
            .contains("summer")
            .parent().find("input").click()

        cy.contains("Что означает March?")
            .parent()
            .contains("март")
            .parent().find("input").click()

        cy.contains("Что означает October?")
            .parent()
            .contains("октябрь")
            .parent().find("input").click()

        cy.contains("Отправить ответы").click()

        cy.contains("Тест успешно пройден")
        cy.get(".DeleteButton").click();


    })

    it("should fail test and get popup", () => {
        cy.contains("Курсы").click()
        cy.contains("Английский язык для начинающих").click()
        cy.contains("Дни недели").click()

        cy.contains("Как будет понедельник на английском?")
            .parent()
            .contains("thursday")
            .parent().find("input").click()

        cy.contains("Как будет четверг на английском?")
            .parent()
            .contains("monday")
            .parent().find("input").click()

        cy.contains("Что означает Wednesday?")
            .parent()
            .contains("среда")
            .parent().find("input").click()

        cy.contains("Что означает Saturday?")
            .parent()
            .contains("суббота")
            .parent().find("input").click()

        cy.contains("Отправить ответы").click()

        cy.contains("Есть неверные ответы, попробуйте еще раз")
        cy.get(".DeleteButton").click();
    })

})


export {}