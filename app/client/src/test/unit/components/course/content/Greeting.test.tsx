import "@testing-library/jest-dom"
import {screen, waitFor} from "@testing-library/react"
import {Course} from "../../../../../main/services/Data";
import {service} from "../../../../../main/services/Dependencies";
import {
    createNavigationElementRender, promiseOf, RenderNavigationElement
} from "../../../Util";
import {Route} from "react-router";
import {Greeting} from "../../../../../main/components/course/content/Greeting";

describe("Greeting test", () => {

    let loadCourseById: jest.SpyInstance
    let render: RenderNavigationElement

    beforeEach(() => {
        const course = new Course(1, "Course", "CourseDescription")
        loadCourseById = jest.spyOn(service, "loadCourseById").mockReturnValueOnce(promiseOf(course))
        render = createNavigationElementRender(
            <Route path="/courses/:courseId" element={<Greeting/>}/>, "/courses/1"
        )
    })

    test("should has greeting for course", async () => {
        render()
        await waitFor(() => {
            expect(loadCourseById).toHaveBeenCalledWith(1)
            expect(screen.getByText("Добро пожаловать на курс \"Course\"")).toBeInTheDocument()
        })
    })
})
