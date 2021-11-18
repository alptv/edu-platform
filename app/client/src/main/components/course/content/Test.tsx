import {TaskData} from "../../../services/CourseService";
import {FormEvent, useState} from "react";
import {List} from "../../generic/List";
import "./Test.css"

export const Test = (props: {
    courseId: number,
    lessonNumber: number,
    tasks: Array<TaskData>
}) => {
    const [answers, setAnswers] = useState(Array<number>(props.tasks.length).fill(-1))
    const tasksWithAnswers = props.tasks.map((taskData, index) => {

        const setAnswer = (newAnswer: number): void =>
            setAnswers([...answers.slice(0, index), newAnswer, ...answers.slice(index + 1, answers.length)])

        const answerProps = taskData.possibleAnswers.map((text, ansIndex) => ({
            number: ansIndex,
            text: text,
            taskNumber: index,
            answer: answers[index],
            setAnswer: setAnswer
        }))
        return {task: taskData, answersProps: answerProps}
    })
    const handleSubmit = (event: FormEvent) => {
        event.preventDefault()
        //postTestResult(courseId, lessonid, answers)
    }
    return (
        <div className="Test">
            <div>Тест</div>
            <form className="TestForm" onSubmit={handleSubmit}>
                <List type="u" items={tasksWithAnswers} element={Task}/>
                <input className="Submit" type="submit" value="Отправить ответы"/>
            </form>
        </div>
    )
}


const Task = (props: {
    task: TaskData
    answersProps: Array<AnswerProps>
}) => (
    <div className="Task">
        <div className="TaskText">{props.task.text}</div>
        <List type="o" items={props.answersProps} element={Answer}/>
    </div>
)


interface AnswerProps {
    number: number,
    text: string,
    taskNumber: number
    answer: number,
    setAnswer: (_: number) => void
}

const Answer = (props: AnswerProps) => {
    return (
        <div className="Answer">
            <input type="radio"
                   value={props.number}
                   name={props.taskNumber.toString()}
                   checked={props.answer == props.number}
                   onChange={() => props.setAnswer(props.number)}/>
            <span>{props.text}</span>
        </div>
    )
}
