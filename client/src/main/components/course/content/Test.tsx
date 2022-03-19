import {FormEvent, useState} from "react";
import {List} from "../../generic/List";
import "./Test.css"
import {createPopupFactory, fromBoolean, PopupType} from "../../generic/Popup";
import {AnswerOption, Question} from "../../../services/Data";
import {Setter, useArrayState, useLoader} from "../../../util/Util";
import {service} from "../../../services/Dependencies";

const NOT_ANSWERED = -1

export const Test = (props: {
    lessonId: number,
    questions: Array<Question>
}) => {

    const [answers, getAnswer, setAnswer] = useArrayState(NOT_ANSWERED, props.questions.length)

    const [answerOptionsForQuestions, setAnswerOptionsForQuestions] = useState<AnswerOption[][]>([])

    useLoader(setAnswerOptionsForQuestions,
        () => service.loadAnswerOptionsForQuestions(props.questions),
        [props.questions])

    const tasks = answerOptionsForQuestions.map((answerOptions, questionIndex) => ({
            questionText: props.questions[questionIndex].text,
            answerOptions: answerOptions,
            setAnswer: (answerOptionId: number) => setAnswer(questionIndex, answerOptionId),
            getAnswer: () => getAnswer(questionIndex)
        })
    )

    const [popupType, setPopupType] = useState(PopupType.NONE)
    const popupFactory = createPopupFactory(
        "Тест успешно пройден",
        "Есть неверные ответы, попробуйте еще раз",
        setPopupType,
        () => answers.forEach((_, index) => setAnswer(index, NOT_ANSWERED))
    )

    const onSubmit = (event: FormEvent) => {
        event.preventDefault()
        service.verifyAnswers(props.lessonId, answers).then(correct => setPopupType(fromBoolean(correct)))
    }
    return (
        <div className="Test">
            <div>Тест</div>
            <form className="TestForm" onSubmit={onSubmit}>
                <List type="u" items={tasks} element={Task}/>
                <input className="Submit" type="submit" value="Отправить ответы"/>
            </form>
            {popupFactory(popupType)}
        </div>
    )
}

const Task = (props: {
    questionText: string,
    answerOptions: Array<AnswerOption>,
    setAnswer: Setter<number>,
    getAnswer: () => number,
}) => {
    const answerProps = props.answerOptions.map(answerOption => ({
        answerOption: answerOption,
        setAnswer: props.setAnswer,
        getAnswer: props.getAnswer
    }))
    return <div className="Task">
        <div className="TaskText">{props.questionText}</div>
        <List type="o" items={answerProps} element={Answer}/>
    </div>
}


const Answer = (props: {
    answerOption: AnswerOption,
    setAnswer: Setter<number>,
    getAnswer: () => number
}) => {
    return (
        <div className="Answer">
            <input type="radio"
                   checked={props.getAnswer() === props.answerOption.id}
                   onChange={() => props.setAnswer(props.answerOption.id)}/>
            <span>{props.answerOption.text}</span>
        </div>
    )
}