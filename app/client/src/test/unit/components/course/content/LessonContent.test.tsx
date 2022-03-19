import "@testing-library/jest-dom"
import {screen, waitFor} from "@testing-library/react"
import {service} from "../../../../../main/services/Dependencies";
import {Question} from "../../../../../main/services/Data";
import {
    createNavigationElementRender, promiseOf, RenderNavigationElement
} from "../../../Util";
import {LessonContent} from "../../../../../main/components/course/content/LessonContent";
import {Route} from "react-router";

describe("LessonContent test", () => {
    let loadQuestionsForLesson: jest.SpyInstance
    let render: RenderNavigationElement

    beforeEach(() => {
        loadQuestionsForLesson = jest.spyOn(service, "loadQuestionsForLesson").mockReturnValueOnce(
            promiseOf([
                new Question(1, "Q1?", 2),
                new Question(2, "Q2?", 4),
            ]))
        jest.spyOn(service, "loadAnswerOptionsForQuestions").mockReturnValueOnce(promiseOf([[], []]))
        render = createNavigationElementRender(
            <Route path="/courses/:courseId/lesson/:lessonId" element={<LessonContent/>}/>,
            "/courses/1/lesson/1"
        )
    })

    test("should load questions for lesson in url", async () => {
        render()
        await waitFor(() => {
            expect(loadQuestionsForLesson).toHaveBeenCalledWith(1)
        })
    })

    test("should be a test with given questions", async () => {
        render()
        expect(await screen.findByText("Тест")).toBeInTheDocument()
        expect(await screen.findByText("Q1?")).toBeInTheDocument()
        expect(await screen.findByText("Q2?")).toBeInTheDocument()
    })
})
