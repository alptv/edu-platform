import "@testing-library/jest-dom"
import {renderElement} from "../Util";
import {Main} from "../../../main/components/Main";
import {screen} from "@testing-library/react"

describe("Main test", () => {
    beforeEach(() => {
        renderElement(<Main/>)
    })

    test("should have correct header", () => {
        expect(screen.getByText("Образовательная платформа")).toBeInTheDocument()
    })

    test("should have purpose", () => {
        expect(screen.getByText("Назначение")).toBeInTheDocument()
        expect(screen.getByText("Данная платформа предназначена для организации процесса обучения и выполнения заданий."
            + " В каталоге можно выбрать любой из понравившихся курсов и получать знания через прохождение соответствующих уроков."))
            .toBeInTheDocument()
    })

    test("should have correct functionality description", () => {
        expect(screen.getByText("Функционал")).toBeInTheDocument()
        expect(screen.getByText("На данный момент поддерживаются курсы, разбитые на уроки."
            + " Урок содержит в себе тест, с вопросами по пройденной теме."
            + " Тест считается пройденным, если все ответы в нем оказались верными."))
            .toBeInTheDocument()
    })
})