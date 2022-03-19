import "@testing-library/jest-dom"
import {screen, fireEvent} from "@testing-library/react"
import {Course} from "../../../../main/services/Data";
import {service} from "../../../../main/services/Dependencies";
import {
    createNavigationElementRender, isOnUrl, promiseOf, RenderNavigationElement
} from "../../Util";
import {Route} from "react-router";
import {CoursesCatalog} from "../../../../main/components/course/CoursesCatalog";

describe("CoursesCatalog test", () => {
    let loadCourses: jest.SpyInstance
    let render: RenderNavigationElement

    beforeEach(() => {
        let courses = [
            new Course(1, "Course1", "CourseDescription1"),
            new Course(2, "Course2", "CourseDescription2")
        ]
        loadCourses = jest.spyOn(service, "loadCourses").mockReturnValueOnce(promiseOf(courses))
        render = createNavigationElementRender(
            <>
                <Route path="/courses" element={
                    <CoursesCatalog/>}>
                    <Route path=":courseId" element={<></>}/>
                </Route>
            </>, "/courses"
        )
    })

    test("should show all courses with descriptions", async () => {
        render()
        expect(await screen.findByText("Course1")).toBeInTheDocument()
        expect(await screen.findByText("CourseDescription1")).toBeInTheDocument()
        expect(await screen.findByText("Course2")).toBeInTheDocument()
        expect(await screen.findByText("CourseDescription2")).toBeInTheDocument()
    })

    test("should be link which redirect to course page",async () => {
        render()
        fireEvent.click(await screen.findByText("Course1"))
        expect(isOnUrl("/courses/1")).toBeTruthy()
    })
})