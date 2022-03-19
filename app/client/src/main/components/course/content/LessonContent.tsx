import "./LessonContent.css"
import {useParams} from "react-router"
import {useState} from "react"
import {Test} from "./Test";
import {Question} from "../../../services/Data";
import {useLoader} from "../../../util/Util";
import {service} from "../../../services/Dependencies";


export const LessonContent = () => {
    const lessonId = parseInt(useParams().lessonId!!)

    const [questions, setQuestions] = useState<Question[]>([])
    useLoader(setQuestions, () => service.loadQuestionsForLesson(lessonId), [lessonId])

    return (
        <div className="LessonContent">
            {questions.length > 0 && <Test questions={questions} lessonId={lessonId}/>}
        </div>
    )
}