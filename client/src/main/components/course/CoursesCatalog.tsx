import "./CoursesCatalog.css"
import {useState} from "react"
import {List} from "../generic/List"
import {NavigationLink} from "../generic/NavigationLink"
import {Course} from "../../services/Data";
import {NO_DEPENDENCY, useLoader} from "../../util/Util";
import {service} from "../../services/Dependencies";


export const CoursesCatalog = () => {

    const [courses, setCourses] = useState<Course[]>([])
    useLoader(setCourses, () => service.loadCourses(), NO_DEPENDENCY)

    return (
        <div className="CoursesCatalog">
            <List type="o" items={courses} element={CourseDescription}/>
        </div>
    )
}


const CourseDescription = (course: Course) => (
    <div className={"CourseDescription"}>
        <h1>
            <NavigationLink to={`${course.id}`}>{course.name}</NavigationLink>
        </h1>
        <p>{course.description}</p>
    </div>
)