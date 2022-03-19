import "./CoursePage.css"
import {useParams} from "react-router"
import {Outlet} from "react-router-dom"
import {useState} from "react"
import {List} from "../generic/List"
import {Course, Lesson} from "../../services/Data";
import {service} from "../../services/Dependencies";
import {NavigationLink} from "../generic/NavigationLink";
import {useLoader} from "../../util/Util";

export const CoursePage = () => {
    const courseId = parseInt(useParams().courseId!!)

    const [course, setCourse] = useState<Course | null>(null)
    useLoader(setCourse, () => service.loadCourseById(courseId), [courseId])

    const [lessons, setLessons] = useState<Lesson[]>([])
    useLoader(setLessons, () => service.loadLessonsForCourse(courseId), [courseId])

    return (
        <div className="CoursePage">
            <div className="LessonsBar">
                {course && <h2>{course.name}</h2>}
                <List type="o" items={lessons} element={LessonStructure}/>
            </div>
            <div className="Content">
                <Outlet/>
            </div>
        </div>
    )
}

export const LessonStructure = (lesson: Lesson) => (
    <div className="LessonStructure">
        <NavigationLink to={`lesson/${lesson.id}`}>{lesson.name} </NavigationLink>
    </div>
)
