import {deserialize, serialize} from 'class-transformer'
import {AnswerOption, Course, Lesson, Question, User} from "./Data";
import {RestClient} from "../util/RestClient";
import {getFromLocalStorage, removeFromLocalStorage, saveToLocalStorage} from "../util/Util";


export interface Service {
    loadCourses(): Promise<Array<Course>>

    loadCourseById(courseId: number): Promise<Course>

    loadLessonsForCourse(courseId: number): Promise<Array<Lesson>>

    loadQuestionsForLesson(lessonsId: number): Promise<Array<Question>>

    loadAnswerOptionsForQuestions(questions: Array<Question>): Promise<Array<AnswerOption[]>>

    verifyAnswers(lessonId: number, answerIds: Array<number>): Promise<boolean>

    enter(login: string, password: string): Promise<User | null>

    register(login: string, password: string): Promise<User | null>

    getUser(): User | null

    logout(): void
}


export class ServiceImpl implements Service {
    readonly USER_TOKEN = "user"
    readonly restClient

    constructor(restClient: RestClient) {
        this.restClient = restClient
    }

    loadCourses() {
        return this.restClient.loadArray<Course>("/course/all", Course)
    }


    loadCourseById(courseId: number) {
        return this.restClient.load<Course>(`/course/${courseId}`, Course)
    }


    loadLessonsForCourse(courseId: number) {
        return this.restClient.loadArray<Lesson>(`/lesson/forCourse/${courseId}`, Lesson)
    }


    loadQuestionsForLesson(lessonsId: number) {
        return this.restClient.loadArray<Question>(`/question/forLesson/${lessonsId}`, Question)
    }


    loadAnswerOptionsForQuestions(questions: Array<Question>) {
        const answerOptionsForQuestions = questions.map(
            question => this.loadAnswerOptionsForQuestion(question.id)
        )
        return Promise.all(answerOptionsForQuestions)
    }

    verifyAnswers(lessonId: number, answerIds: Array<number>) {
        const json = serialize(answerIds)
        const response = this.restClient.send("POST", `/lesson/${lessonId}/verifyAnswers`, json)
        return response.then(response => response.text()).then(text => deserialize(Boolean, text).valueOf())
    }

    enter(login: string, password: string) {
        return this.processUserCredentials(login, password, "/auth/login")
    }

    register(login: string, password: string) {
        return this.processUserCredentials(login, password, "/auth/register")
    }


    getUser() {
        return getFromLocalStorage(this.USER_TOKEN, User)
    }

    logout() {
        removeFromLocalStorage(this.USER_TOKEN)
        return this.restClient.send("GET", "/auth/logout")
    }

    loadAnswerOptionsForQuestion(questionId: number) {
        return this.restClient.loadArray<AnswerOption>(`/answerOption/forQuestion/${questionId}`, AnswerOption)
    }

    private processUserCredentials(login: string, password: string, resource: string) {
        let userCredentials = {login: login, password: password}
        let json = serialize<{ login: string, password: string }>(userCredentials);
        return this.restClient.send("POST", resource, json)
            .then(response => {
                if (response.status === 200) {
                    const user = new User(login, password)
                    saveToLocalStorage(this.USER_TOKEN, user)
                    return user
                } else {
                    return null
                }
            });
    }
}

