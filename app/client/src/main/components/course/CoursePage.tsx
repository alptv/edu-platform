import "./CoursePage.css"
import {useParams} from "react-router"
import {Outlet} from "react-router-dom"
import {loadCourse} from "../../services/CourseService"
import {LoadingComponent} from "../generic/LoadingComponent"
import {LessonStructure} from "./Lesson"
import {List} from "../generic/List"

export const CoursePage = () => {
    const courseId = parseInt(useParams().courseId!!)
    const loadCurrentCourse = async () => loadCourse(courseId)
    return (
        <div className="CoursePage">
            <LoadingComponent loader={loadCurrentCourse}>
                {course => (
                    <>
                        <div className="LessonsBar">
                            <h2>{course.courseInfo.name}</h2>
                            <List type="o" items={course.lessonsStructure} element={LessonStructure}/>
                        </div>
                        <div className="Content">
                            <Outlet/>
                        </div>
                    </>
                )}
            </LoadingComponent>
        </div>
    )
}

export const Greeting = () => {
    const courseId = parseInt(useParams().courseId!!)
    const loadCurrentCourse = async () => loadCourse(courseId)
    return (
        <div className="Greeting">
            <LoadingComponent loader={loadCurrentCourse}>
                {course => <h1>{`Добро пожаловать на курс "${course.courseInfo.name}"`}</h1>}
            </LoadingComponent>
        </div>
    )
}
