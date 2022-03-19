import "@testing-library/jest-dom"
import {screen, fireEvent, waitFor} from "@testing-library/react"
import {promiseOf, Render, createElementRender} from "../../../Util";
import {Test} from "../../../../../main/components/course/content/Test";
import {AnswerOption, Question} from "../../../../../main/services/Data";
import {service} from "../../../../../main/services/Dependencies";

const TOTAL_ANSWER_OPTION_COUNT = 4

async function getAllRadios(): Promise<NodeListOf<HTMLElement>> {
    let radios = document.querySelectorAll<HTMLElement>("input[type=radio]")
    await waitFor(() => {
        radios = document.querySelectorAll<HTMLElement>("input[type=radio]")
        expect(radios).toHaveLength(TOTAL_ANSWER_OPTION_COUNT)
    })
    return radios
}

describe("Test test", () => {
    let loadAnswerOptionsForQuestions: jest.SpyInstance
    let verifyAnswers: jest.SpyInstance
    let render: Render

    beforeEach(() => {
        loadAnswerOptionsForQuestions = jest.spyOn(service, "loadAnswerOptionsForQuestions")
            .mockReturnValueOnce(promiseOf([
                    [
                        new AnswerOption(1, 1, "A"),
                        new AnswerOption(2, 1, "B")],
                    [
                        new AnswerOption(3, 2, "C"),
                        new AnswerOption(4, 2, "D")
                    ]
                ]
            ))
        verifyAnswers = jest.spyOn(service, "verifyAnswers")
        render = createElementRender(
            <Test lessonId={1} questions={[
                new Question(1, "Q1?", 1),
                new Question(2, "Q2?", 4)
            ]}/>)
    })

    test("should have header", async () => {
        render()
        expect(await screen.findByText("Тест")).toBeInTheDocument()
    })

    test("should render all questions", async () => {
        render()
        expect(await screen.findByText("Q1?")).toBeInTheDocument()
        expect(await screen.findByText("Q2?")).toBeInTheDocument()
    })

    test("should load answers from service", async () => {
        render()
        await waitFor(() => {
            expect(loadAnswerOptionsForQuestions).toHaveBeenCalledWith([
                new Question(1, "Q1?", 1),
                new Question(2, "Q2?", 4)
            ])
        })
    })

    test("should render all answers", async () => {
        render()
        expect(await screen.findByText("A")).toBeInTheDocument()
        expect(await screen.findByText("B")).toBeInTheDocument()
        expect(await screen.findByText("C")).toBeInTheDocument()
        expect(await screen.findByText("D")).toBeInTheDocument()
        await waitFor(() => expect(document.querySelectorAll<HTMLElement>("input[type=radio]")).toHaveLength(4))
    })

    test("all radios for answer should not be picked after render", async () => {
        render()
        const radios = await getAllRadios()
        radios.forEach(radio => expect(radio).not.toBeChecked())
    })

    test("user can pick only one answer for question", async () => {
        render()
        const radios = await getAllRadios()
        radios.forEach(radio => fireEvent.click(radio))

        await waitFor(() => {
            expect(radios.item(0)).not.toBeChecked()
            expect(radios.item(1)).toBeChecked()
            expect(radios.item(2)).not.toBeChecked()
            expect(radios.item(3)).toBeChecked()
        })
    })

    test("should have submit button", async () => {
        render()
        expect(await screen.findByText("Отправить ответы")).toBeInTheDocument()
    })

    test("should call verify answers on service after submit", async () => {
        render()
        verifyAnswers.mockReturnValueOnce(promiseOf(false))
        const radios = await getAllRadios()
        radios.forEach(radio => fireEvent.click(radio))
        fireEvent.submit(await screen.findByText("Отправить ответы"))

        await waitFor(() => {
            expect(verifyAnswers).toHaveBeenCalledWith(1, [2, 4])
        })
    })

    test("should show popup with failure message on incorrect answers", async () => {
        render()
        verifyAnswers.mockReturnValueOnce(promiseOf(false))
        const radios = await getAllRadios()
        radios.forEach(radio => fireEvent.click(radio))
        fireEvent.submit(screen.getByText("Отправить ответы"))

        expect(await screen.findByText("Есть неверные ответы, попробуйте еще раз")).toBeInTheDocument()
    })

    test("should show popup with success message on correct answers", async () => {
        render()
        verifyAnswers.mockReturnValueOnce(promiseOf(true))
        const radios = await getAllRadios()
        radios.forEach(radio => fireEvent.click(radio))
        fireEvent.submit(screen.getByText("Отправить ответы"))

        expect(await screen.findByText("Тест успешно пройден")).toBeInTheDocument()
    })

    test("should be able to close popup", async () => {
        render()
        verifyAnswers.mockReturnValueOnce(promiseOf(true))
        fireEvent.submit(screen.getByText("Отправить ответы"))

        await waitFor(() => {
            const deleteButton = document.querySelector<HTMLElement>(".DeleteButton")
            expect(deleteButton).toBeInTheDocument()
            fireEvent.click(deleteButton!!)
            expect(screen.queryByText("Тест успешно пройден")).not.toBeInTheDocument()
        })
    })
})


