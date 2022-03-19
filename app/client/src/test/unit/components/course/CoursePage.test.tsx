import "@testing-library/jest-dom"
import {screen, waitFor, fireEvent} from "@testing-library/react"
import {Course, Lesson} from "../../../../main/services/Data";
import {service} from "../../../../main/services/Dependencies";
import {
    createNavigationElementRender, Element, isOnUrl,
    promiseOf, renderNavigationElement,
    RenderNavigationElement
} from "../../Util";
import {CoursePage, LessonStructure} from "../../../../main/components/course/CoursePage";
import {Route} from "react-router";

describe("LessonStructure test", () => {
    let isStartUrl: () => boolean
    let onClick: jest.Mock
    beforeEach(() => {
        onClick = jest.fn();
        [, isStartUrl] = renderNavigationElement(
            <>
                <Route path="/lesson/1" element={<></>}/>
                <Route path="/" element={
                    <LessonStructure id={1} name="Lesson"/>
                }/>
            </>, "/"
        )
    })

    test("should have lesson name", () => {
        expect(screen.getByText("Lesson")).toBeInTheDocument()
    })

    test("should be link which redirect to lesson page", () => {
        expect(screen.getByText("Lesson")).toBeInTheDocument()
        fireEvent.click(screen.getByText("Lesson"))
        expect(isOnUrl("/lesson/1")).toBeTruthy()
    })
})

describe("CoursePage test", () => {

    let loadCourseById: jest.SpyInstance
    let loadLessonsForCourse: jest.SpyInstance
    let render : RenderNavigationElement

    beforeEach(() => {
        let course = new Course(1, "Course", "CourseDescription")
        loadCourseById = jest.spyOn(service, "loadCourseById").mockReturnValueOnce(promiseOf(course))

        let lessons = [new Lesson(1, "First Lesson"), new Lesson(2, "Second Lesson")]
        loadLessonsForCourse = jest.spyOn(service, "loadLessonsForCourse").mockReturnValueOnce(promiseOf(lessons))

        render = createNavigationElementRender(
            <>
                <Route path="/courses/:courseId" element={<CoursePage/>}>
                    <Route path="content" element={
                        <Element text="content"/>
                    }/>
                    <Route path="lesson/:lessonId" element={
                        <Element text="Lesson data"/>
                    }/>
                </Route>

            </>, "/courses/1/content"
        )
    })

    test("should have course name", async () => {
        render()
        expect(await screen.findByText("Course")).toBeInTheDocument()
        await waitFor(() => expect(loadCourseById).toHaveBeenCalledWith(1))
    })

    test("should have lesson bar with ordered list of lessons", async () => {
        render()
        expect(await screen.findByText("First Lesson")).toBeInTheDocument()
        expect(await screen.findByText("Second Lesson")).toBeInTheDocument()
        await waitFor(() => expect(loadLessonsForCourse).toHaveBeenCalledWith(1))
    })

    test("should redirect by click on link for lesson", async () => {
        render()
        fireEvent.click(await screen.findByText("First Lesson"))
        expect(isOnUrl("/courses/1/lesson/1")).toBeTruthy()
    })

    test("should have nested content", async () => {
        render()
        expect(await screen.findByText("content")).toBeInTheDocument()
    })

})