import { RestClient} from "../util/RestClient";
import {ServiceImpl, Service} from "./Service";
import {AnswerOption, Course, Lesson, Question, User} from "./Data";

const API_URL: string = "http://localhost:8080"
const serviceRestClient: RestClient = new RestClient(API_URL)


class FakeService implements Service {
    enter(login: string, password: string): Promise<User | null> {
        return Promise.resolve(new User("login", "password"));
    }

    getUser(): User | null {
        return new User("login", "password");
    }

    loadAnswerOptionsForQuestions(questions: Array<Question>): Promise<Array<AnswerOption[]>> {
        return Promise.resolve([]);
    }

    loadCourseById(courseId: number): Promise<Course> {
        return Promise.resolve(new Course(1, "course", "desc"));
    }

    loadCourses(): Promise<Array<Course>> {
        return Promise.resolve([new Course(1, "course", "desc")]);
    }

    loadLessonsForCourse(courseId: number): Promise<Array<Lesson>> {
        return Promise.resolve([new Lesson(1, "lesson")]);
    }

    loadQuestionsForLesson(lessonsId: number): Promise<Array<Question>> {
        return Promise.resolve([]);
    }

    logout(): void {
    }

    register(login: string, password: string): Promise<User | null> {
        return Promise.resolve(new User("login", "password"));
    }

    verifyAnswers(lessonId: number, answerIds: Array<number>): Promise<boolean> {
        return Promise.resolve(false);
    }

}
const fakeService : Service = new FakeService()

export const service: Service = fakeService//new ServiceImpl(serviceRestClient)
