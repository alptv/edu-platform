// const loadCourses = async (): Promise<Array<CourseInfo>> =>
//     fetch(`${API}/courses`, {
//         method: "GET"
//     }).then(res => Object.assign(new Array<CourseInfo>(), res.json()))
//         .catch(error => console.log(error))

export async function loadCoursesInfo(): Promise<Array<CourseInfoData>> {
    return new Promise((resolve, reject) => resolve(new Array<CourseInfoData>(new CourseInfoData(1, "Алгебра", "Изучите алгебру бла бла бла"),
        new CourseInfoData(2, "Реакт", "Напиши свою хуйню уже после первого урок"),
        new CourseInfoData(3, "Базы данных", "Очень жаль вы идете нахуй"))))
}

export async function loadCourse(courseId: number): Promise<CourseStructureData> {
    return new Promise((resolve, reject) => {
            if (courseId === 1) {
                let x = new CourseInfoData(1, "Алгебра", "Изучите алгебру бла бла бла")
                const l = []
                for (let i = 0; i < 5; i++) {
                    l.push(new LessonInfoData(i, `Основные сармовские сармы и их применнии в ежи теории`))
                }
                return resolve(new CourseStructureData(x, l))
            }
            if (courseId === 2) {
                let x = new CourseInfoData(2, "Реакт", "Напиши свою хуйню уже после первого урок")
                let l = [new LessonInfoData(2, "React lesson 1")]
                return resolve(new CourseStructureData(x, l))
            }
            let x = new CourseInfoData(3, "Базы данных", "Очень жаль вы идете нахуй")
            let l = [new LessonInfoData(1, "Bd lesson 1")]
            return resolve(new CourseStructureData(x, l))
        }
    )
}

export async function loadTasks(courseId: number, lessonNumber: number): Promise<Array<TaskData>> {
    return new Promise((resolve, reject) => {
            const tasks = []
            for (let i = 0; i < 5; i++) {
                tasks.push(new TaskData(i, `Task${i} on course${courseId} and lesson${lessonNumber}?`, ["A1", "A2", "A3", "A4"], 1))
            }
            return resolve(tasks)
        }
    )
}

export class CourseInfoData {
    constructor(readonly id: number, readonly name: string, readonly description: string) {
    }
}

export class CourseStructureData {
    constructor(readonly courseInfo: CourseInfoData, readonly lessonsStructure: Array<LessonInfoData>) {
    }
}

export class LessonInfoData {
    constructor(readonly number: number, readonly name: string) {
    }
}

export class TaskData {
    constructor(readonly number: number, readonly text: string, readonly possibleAnswers: Array<string>, readonly correctAnswer: number) {
    }
}

export class User {
    constructor(readonly name: string) {
    }
}