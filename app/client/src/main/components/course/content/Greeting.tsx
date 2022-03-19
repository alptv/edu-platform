import "./Greeting.css"
import {useParams} from "react-router"
import {useState} from "react"
import {Course} from "../../../services/Data";
import {useLoader} from "../../../util/Util";
import {service} from "../../../services/Dependencies";

export const Greeting = () => {
    const courseId = parseInt(useParams().courseId!!)

    const [course, setCourse] = useState<Course | null>(null)
    useLoader(setCourse, () => service.loadCourseById(courseId), [courseId])

    return (
        <div className="Greeting">
            {course && <h1>{`Добро пожаловать на курс "${course.name}"`}</h1>}
        </div>
    )
}