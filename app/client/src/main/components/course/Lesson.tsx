import "./Lesson.css"
import {useParams} from "react-router"
import {LessonInfoData, loadTasks, TaskData} from "../../services/CourseService"
import {LoadingComponent} from "../generic/LoadingComponent"
import {NavigationLink} from "../generic/NavigationLink"
import {Test} from "./content/Test";


export const LessonStructure = (lessonData: LessonInfoData) => (
    <div className="LessonStructure">
        <NavigationLink to={`lesson/${lessonData.number}`}>{lessonData.name} </NavigationLink>
    </div>
)


export const LessonContent = () => {
    const courseId = parseInt(useParams().courseId!!)
    const lessonNumber = parseInt(useParams().number!!)
    const loadTask = async () => loadTasks(courseId, lessonNumber)
    return (
        <div className="LessonContent">
            <LoadingComponent loader={loadTask}>
                {(tasks: Array<TaskData>) => (
                    <Test tasks={tasks} courseId={courseId} lessonNumber={lessonNumber}/>
                )}
            </LoadingComponent>
        </div>
    )
}
